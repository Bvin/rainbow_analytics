package cn.rainbow.sdk.analytics.track.report;

import com.litesuits.http.request.JsonAbsRequest;
import com.litesuits.http.request.param.HttpParamModel;

import cn.rainbow.sdk.analytics.data.remote.Model;

/**
 * Created by bvin on 2016/8/30.
 */
public class EventRequest extends JsonAbsRequest<Model> {

    public EventRequest(HttpParamModel model) {
        super(model);
    }
}
