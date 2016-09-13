package com.px.bmarket.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by PX on 2016/9/12.
 */
public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME ="AppInfo.db";
    public static final String TABLE_NAME = "AppInfo";
    private static final String CREATE_TABLE = "create table if not exists "+TABLE_NAME+"(_id integer primary key autoincrement" +
            ",apkName text,apkFileName text,apkPackageName text,apkIconUrl text,apkDownloadUrl text,apkVersion text,apkSize text" +
            ",apkType text,apkLanguage text,apkSummary text,isRecommend text,isDisplay text,apkVersionCode integer,sequence integer)";
    private static final String DROP_TABLE = "drop table if exists "+TABLE_NAME;
    private static final int VERSION = 1;
    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }
}
