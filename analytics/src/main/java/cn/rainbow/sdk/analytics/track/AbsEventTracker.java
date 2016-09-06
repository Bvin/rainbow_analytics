package cn.rainbow.sdk.analytics.track;

import android.content.Context;

import com.litesuits.http.exception.HttpException;
import com.litesuits.http.listener.HttpListener;
import com.litesuits.http.request.AbstractRequest;
import com.litesuits.http.response.Response;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import cn.rainbow.sdk.analytics.Config;
import cn.rainbow.sdk.analytics.THAnalytics;
import cn.rainbow.sdk.analytics.data.local.db.SQLTable;
import cn.rainbow.sdk.analytics.data.remote.Model;
import cn.rainbow.sdk.analytics.event.Event;
import cn.rainbow.sdk.analytics.utils.NetworkHelper;

/**
 * Created by bvin on 2016/5/31.
 */
public abstract class AbsEventTracker<T extends Event> {

    protected Context mContext;
    private T mEvent;
    private SQLTable mTable;

    protected long mEventId;
    protected String mEventName;

    private SimpleDateFormat mDateFormat;
    private long mStartMillis;

    private boolean mEnable = true;//默认开启
    private HttpListener<Model> mHttpListener;

    public AbsEventTracker(Context context,long eventId, String eventName) {
        mContext = context;
        mEventId = eventId;
        mEventName = eventName;
    }

    /**
     * 获取事件.
     * @return 事件, must not be null
     */
    public abstract T takeEvent();

    /**
     * 获取表.
     * @return 表, must not be null
     */
    public abstract SQLTable takeTable();

    /**
     * 事件统计开始.
     * <p>注意：
     * <ol>
     *     <li>调用此方法之前务必确保抽象方法takeEvent()中事件被创建.</li>
     *     <li>onEventStart()和onEventEnd()需成对出现，调用onEventEnd()之前务必先调用onEventStart().</li>
     * </ol>
     * @throws IllegalStateException 事件统计之前必须保证事件已被创建(@link#takeEvent())，否则会抛出此异常
     */
    public void onEventStart() throws IllegalStateException {
        if (!mEnable) return;
        if (mEvent == null) {
            mEvent = takeEvent();
        }
        if (mEvent == null)
            throw new IllegalStateException("event must be build before call onEventStart() method!");

        mEvent.setStartDate(getCurrentDate());
        mStartMillis = System.currentTimeMillis();
    }

    public void onEvent(){
        if (!mEnable) return;
    }

    /**
     * 事件统计结束（必须先调用onEventStart()开始统计.
     * @throws IllegalStateException 如果调用此方法之前没有调用onEventStart()会抛出此异常
     */
    public void onEventEnd() throws IllegalStateException {
        if (!mEnable) return;
        if (mEvent == null)
            throw new IllegalStateException("event object may be not created or be recycled!");

        //事件统计结束，保存到数据库
        mEvent.setEndDate(getCurrentDate());
        long duration = System.currentTimeMillis() - mStartMillis;
        mEvent.setDuration(duration);

        finallyExecute();
    }

    //事件结束后执行finally操作
    private void finallyExecute() {
        if (THAnalytics.getCurrentConfig().isPushRemoteEnable()) {//开启上报服务器
            if (THAnalytics.getCurrentConfig().getPushStrategy() == Config.PUSH_STRATEGY_REAL_TIME) {
                //实时推送
                if (THAnalytics.getCurrentConfig().isPushOnlyWifi()) {
                    if (new NetworkHelper(mContext).inWifiNetwork()) {
                        push();
                    } else {
                        saveIfEnable();
                    }
                } else {
                    push();
                }
            } else {
                saveIfEnable();
            }
        } else {//没开启上报服务器就先存本地
            saveIfEnable();
        }
    }

    private void saveIfEnable() {
        if (THAnalytics.getCurrentConfig().isSaveLocalEnable()) {
            save();
        }
    }

    protected void save() {
        if (mEvent != null) {
            mTable = takeTable();
            if (mTable != null) {
                mTable.save(mEvent);
            }
        }
    }

    protected void push(){
        //empty implement
    }

    /**
     * 设置是否开启统计.
     * @param enable 是否开启
     */
    public void setEnable(boolean enable) {
        mEnable = enable;
    }

    public HttpListener<Model> listener(){
        if (mHttpListener == null) {
            mHttpListener = new HttpListener<Model>() {
                @Override
                public void onSuccess(Model model, Response<Model> response) {
                    super.onSuccess(model, response);
                    //推送成功(不需要删除，因为选择推送就不会保存到本地)
                }

                @Override
                public void onFailure(HttpException e, Response<Model> response) {
                    super.onFailure(e, response);
                    //推送失败（保存到本地）
                    saveIfEnable();
                }

                @Override
                public void onStart(AbstractRequest<Model> request) {
                    super.onStart(request);
                }
            };
        }
        return mHttpListener;
    }

    private String getCurrentDate(){
        if (mDateFormat == null) {
            mDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        }
        return mDateFormat.format(new Date());
    }
}
