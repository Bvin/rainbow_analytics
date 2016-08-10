package cn.rainbow.sdk.analytics.data.local;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.TextUtils;

import cn.rainbow.sdk.analytics.data.local.db.table.AppTable;
import cn.rainbow.sdk.analytics.data.local.db.table.CrashTable;
import cn.rainbow.sdk.analytics.data.local.db.DBHelper;
import cn.rainbow.sdk.analytics.data.local.db.table.EventTable;
import cn.rainbow.sdk.analytics.data.local.db.table.PageTable;
import cn.rainbow.sdk.analytics.data.local.db.table.buz.CartTable;
import cn.rainbow.sdk.analytics.data.local.db.table.buz.FavTable;
import cn.rainbow.sdk.analytics.data.local.db.table.buz.GoodsTable;
import cn.rainbow.sdk.analytics.data.local.db.table.buz.OrderTable;
import cn.rainbow.sdk.analytics.data.local.db.table.buz.THEventTable;
import cn.rainbow.sdk.analytics.data.local.db.table.buz.THPageTable;

/**
 * Created by 32967 on 2016/5/31.
 */
public class DBProvider extends ContentProvider{

    private static  final UriMatcher sUriMatcher;
    public static final String AUTHORITY = "cn.rainbow.analytics.provider";
    private static final int CODE_EVENT_TABLE = 0;
    private static final int CODE_EVENT_APP = 2;
    private static final int CODE_EVENT_PAGE = 4;
    private static final int CODE_EVENT_CRASH = 6;
    private static final int CODE_EVENT_THPAGE = 8;
    private static final int CODE_EVENT_GOODS = 10;
    private static final int CODE_EVENT_FAVS = 12;
    private static final int CODE_EVENT_ORDERS = 14;
    private static final int CODE_EVENT_CARTS = 16;
    private static final int CODE_TH_EVENT = 18;


    static {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        registerTable(EventTable.TABLE_NAME, CODE_EVENT_TABLE);
        registerTable(AppTable.TABLE_NAME,CODE_EVENT_APP);
        registerTable(PageTable.TABLE_NAME,CODE_EVENT_PAGE);
        registerTable(CrashTable.TABLE_NAME,CODE_EVENT_CRASH);
        registerTable(THPageTable.NAME,CODE_EVENT_THPAGE);
        registerTable(GoodsTable.TABLE_NAME,CODE_EVENT_GOODS);
        registerTable(FavTable.TABLE_NAME,CODE_EVENT_FAVS);
        registerTable(CartTable.TABLE_NAME,CODE_EVENT_CARTS);
        registerTable(OrderTable.TABLE_NAME,CODE_EVENT_ORDERS);
        registerTable(THEventTable.TABLE_NAME,CODE_TH_EVENT);
    }

    static void registerTable(String tableName, int code) {
        sUriMatcher.addURI(AUTHORITY, tableName, code);
        sUriMatcher.addURI(AUTHORITY, tableName + "/#", code + 1);
    }

    private DBHelper mDBHelper;

    @Override
    public boolean onCreate() {
        mDBHelper = new DBHelper(getContext());
        return true;
    }

    @Override
    public String getType(Uri uri) {
        int code = sUriMatcher.match(uri);
        String table = matchTable(code);
        if (!TextUtils.isEmpty(table)) {
            String type = code % 2 == 0 ? "dir" : "item";
            return "vnd.android.cursor." + type + "/vnd." + AUTHORITY + "." + table;
        }
        return null;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        String table = matchTable(sUriMatcher.match(uri));
        if (!TextUtils.isEmpty(table)) {
            return mDBHelper.getReadableDatabase().query(table, projection, selection, selectionArgs, null, null, sortOrder);
        }
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        int code = sUriMatcher.match(uri);
        if (code % 2 != 0) return null;//插入只能插入dir类型uri
        String table = matchTable(code);
        if (!TextUtils.isEmpty(table)) {
            long rowId = mDBHelper.getWritableDatabase().insert(table, null, contentValues);
            if (rowId > 0) {
                return ContentUris.withAppendedId(uri, rowId);
            }//插入失败
        }
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int code = sUriMatcher.match(uri);
        String table = matchTable(sUriMatcher.match(uri));
        if (code % 2 == 0) {//dir
            if (!TextUtils.isEmpty(table)) {
                return mDBHelper.getWritableDatabase().delete(table, selection, selectionArgs);
            }
        }else {//item
            long id = parseIdOfUri(uri);
            if (id >= 0)
                return mDBHelper.getWritableDatabase().delete(table, BaseColumns._ID + "=?", new String[]{String.valueOf(id)});
        }
        return -1;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        int code = sUriMatcher.match(uri);
        String table = matchTable(sUriMatcher.match(uri));
        if (code % 2 == 0) {//dir
            if (!TextUtils.isEmpty(table)) {
                return mDBHelper.getWritableDatabase().update(table, contentValues, s, strings);
            }
        } else {//item
            long id = parseIdOfUri(uri);
            if (id >= 0) {
                return mDBHelper.getWritableDatabase().update(table, contentValues, BaseColumns._ID + "=?", new String[]{String.valueOf(id)});
            }
        }
        return -1;
    }

    private long parseIdOfUri(Uri uri) {
        long id = -1;
        try {
            id = ContentUris.parseId(uri);
        } catch (UnsupportedOperationException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return id;
    }

    private String matchTable(int matchCode) {
        switch (matchCode) {
            case CODE_EVENT_TABLE:
            case CODE_EVENT_TABLE + 1:
                return EventTable.TABLE_NAME;
            case CODE_EVENT_APP:
            case CODE_EVENT_APP + 1:
                return AppTable.TABLE_NAME;
            case CODE_EVENT_PAGE:
            case CODE_EVENT_PAGE + 1:
                return PageTable.TABLE_NAME;
            case CODE_EVENT_CRASH:
            case CODE_EVENT_CRASH + 1:
                return CrashTable.TABLE_NAME;
            case CODE_EVENT_THPAGE:
            case CODE_EVENT_THPAGE + 1:
                return THPageTable.NAME;
            case CODE_EVENT_GOODS:
            case CODE_EVENT_GOODS + 1:
                return GoodsTable.TABLE_NAME;
            case CODE_EVENT_FAVS:
            case CODE_EVENT_FAVS + 1:
                return FavTable.TABLE_NAME;
            case CODE_EVENT_CARTS:
            case CODE_EVENT_CARTS + 1:
                return CartTable.TABLE_NAME;
            case CODE_EVENT_ORDERS:
            case CODE_EVENT_ORDERS + 1:
                return OrderTable.TABLE_NAME;
            case CODE_TH_EVENT:
            case CODE_TH_EVENT + 1:
                return THEventTable.TABLE_NAME;
            default:
                return null;
        }
    }
}
