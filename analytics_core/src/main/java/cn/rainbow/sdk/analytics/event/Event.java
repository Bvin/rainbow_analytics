package cn.rainbow.sdk.analytics.event;

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import cn.rainbow.sdk.analytics.persistence.Persistable;

/**
 * Created by bvin on 2016/9/14.
 */
public class Event implements Persistable{

    private String mName;

    protected int mChannelId;

    public Event(String name) {
        mName = name;
    }

    @Override
    public String toPersistableString() {
        return toString();
    }

    public String getName() {
        return mName;
    }

    public void setChannelId(int channelId) {
        mChannelId = channelId;
    }

    public void putValue(StringBuilder sb, String key, String value, boolean urlEncode) {
        if (!TextUtils.isEmpty(value)) {
            sb.append("&").append(key).append("=").append(urlEncode ? urlEncode(value) : value);
        }
    }

    public void putValue(StringBuilder sb, String key, String value) {
        putValue(sb, key, value, false);
    }

    /**
     * URL编码.
     * @param content 内容
     * @return 成功返回URL编码后的内容，否则返元原来内容
     */
    protected String urlEncode(String content){
        if (TextUtils.isEmpty(content)) return content;
        try {
            content = URLEncoder.encode(content, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return content;
    }
}
