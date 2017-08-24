package cn.rainbow.sdk.analytics.persistence;


import java.io.Serializable;

/**
 * Created by bvin on 2017/8/24.
 */

public class Record implements Serializable {

    private int mRowId;
    private String mBody;

    public Record(int rowId, String body) {
        mRowId = rowId;
        mBody = body;
    }

    public int getRowId() {
        return mRowId;
    }

    public String getBody() {
        return mBody;
    }
}
