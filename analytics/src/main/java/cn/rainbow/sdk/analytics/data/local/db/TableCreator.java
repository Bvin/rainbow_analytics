package cn.rainbow.sdk.analytics.data.local.db;

import android.content.ContentValues;

/**
 * Created by 32967 on 2016/5/31.
 */
public interface TableCreator {

    String tableName();

    String defineFields();

    ContentValues values();

}
