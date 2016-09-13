package com.px.bmarket.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.px.bmarket.Beans.AppInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PX on 2016/9/12.
 */
public class SQLiteDao {
    private Context context;
    private SQLiteDatabase sqLiteDatabase;

    private SQLiteDao(Context context) {
        this.context = context;
        sqLiteDatabase = new SQLiteHelper(context).getWritableDatabase();
    }
    private volatile static SQLiteDao instance;
    public static synchronized SQLiteDao getInstance(Context context){
        if(instance ==null){
            synchronized (SQLiteDao.class){
                if(instance ==null){
                    instance = new SQLiteDao(context);
                }
            }
        }
        return instance;
    }

    public boolean isExists(String packageName){
        Cursor cursor = sqLiteDatabase.query(SQLiteHelper.TABLE_NAME ,null , "apkPackageName=?" ,new String [] {packageName} ,null, null ,null);
        boolean isExists = cursor.moveToNext();
        if(cursor != null){
            cursor.close();
        }
        return isExists;
    }

    public void insertData(AppInfo appInfo){
        ContentValues contentValues = new ContentValues();
        contentValues.put("apkName",appInfo.getApkName());
        contentValues.put("apkFileName",appInfo.getApkFileName());
        contentValues.put("apkPackageName",appInfo.getApkPackageName());
        contentValues.put("apkIconUrl",appInfo.getApkIconUrl());
        contentValues.put("apkDownloadUrl",appInfo.getApkDownloadUrl());
        contentValues.put("apkVersion",appInfo.getApkVersion());
        contentValues.put("apkSize",appInfo.getApkSize());
        contentValues.put("apkType",appInfo.getApkType());
        contentValues.put("apkLanguage",appInfo.getApkLanguage());
        contentValues.put("apkSummary",appInfo.getApkSummary());
        contentValues.put("isRecommend",appInfo.getIsRecommend());
        contentValues.put("isDisplay",appInfo.getIsDisplay());
        contentValues.put("apkVersionCode",appInfo.getApkVersionCode());
        contentValues.put("sequence",appInfo.getSequence());
        sqLiteDatabase.insert(SQLiteHelper.TABLE_NAME ,null ,contentValues);
        if(contentValues!=null){
            contentValues.clear();
            contentValues=null;
        }
    }

    public void updateData(AppInfo appInfo){
        ContentValues contentValues = new ContentValues();
        contentValues.put("apkName",appInfo.getApkName());
        contentValues.put("apkFileName",appInfo.getApkFileName());
        contentValues.put("apkPackageName",appInfo.getApkPackageName());
        contentValues.put("apkIconUrl",appInfo.getApkIconUrl());
        contentValues.put("apkDownloadUrl",appInfo.getApkDownloadUrl());
        contentValues.put("apkVersion",appInfo.getApkVersion());
        contentValues.put("apkSize",appInfo.getApkSize());
        contentValues.put("apkType",appInfo.getApkType());
        contentValues.put("apkLanguage",appInfo.getApkLanguage());
        contentValues.put("apkSummary",appInfo.getApkSummary());
        contentValues.put("isRecommend",appInfo.getIsRecommend());
        contentValues.put("isDisplay",appInfo.getIsDisplay());
        contentValues.put("apkVersionCode",appInfo.getApkVersionCode());
        contentValues.put("sequence",appInfo.getSequence());
        sqLiteDatabase.update(SQLiteHelper.TABLE_NAME ,contentValues ,"apkPackageName=?" ,new String []{appInfo.getApkPackageName()});
        if(contentValues!=null){
            contentValues.clear();
            contentValues=null;
        }
    }

    public void insertOrUpdateData(AppInfo appInfo){
        if(isExists(appInfo.getApkPackageName())){
            updateData(appInfo);
        }else{
            insertData(appInfo);
        }
    }

    public void deleteAll(){
        sqLiteDatabase.delete(SQLiteHelper.TABLE_NAME ,"_id>?" ,new String []{"0"});
    }

    public List<AppInfo> queryAll(){
        List<AppInfo> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(SQLiteHelper.TABLE_NAME ,null ,"_id>? and isDisplay=?" ,new String[]{"0","true"} ,null ,null ,"sequence");
        while(cursor.moveToNext()){
            AppInfo appInfo = new AppInfo();
            appInfo.setApkName(cursor.getString(cursor.getColumnIndex("apkName")));
            appInfo.setApkFileName(cursor.getString(cursor.getColumnIndex("apkFileName")));
            appInfo.setApkPackageName(cursor.getString(cursor.getColumnIndex("apkPackageName")));
            appInfo.setApkIconUrl(cursor.getString(cursor.getColumnIndex("apkIconUrl")));
            appInfo.setApkDownloadUrl(cursor.getString(cursor.getColumnIndex("apkDownloadUrl")));
            appInfo.setApkVersion(cursor.getString(cursor.getColumnIndex("apkVersion")));
            appInfo.setApkSize(cursor.getString(cursor.getColumnIndex("apkSize")));
            appInfo.setApkType(cursor.getString(cursor.getColumnIndex("apkType")));
            appInfo.setApkLanguage(cursor.getString(cursor.getColumnIndex("apkLanguage")));
            appInfo.setApkSummary(cursor.getString(cursor.getColumnIndex("apkSummary")));
            appInfo.setIsRecommend(cursor.getString(cursor.getColumnIndex("isRecommend")));
            appInfo.setIsDisplay(cursor.getString(cursor.getColumnIndex("isDisplay")));
            appInfo.setApkVersionCode(cursor.getInt(cursor.getColumnIndex("apkVersionCode")));
            appInfo.setSequence(cursor.getInt(cursor.getColumnIndex("sequence")));
            list.add(appInfo);
            if(appInfo!= null){
                appInfo =null;
            }
        }
        if(cursor!=null){
            cursor.close();
        }
        return list;
    }

    public List<AppInfo> queryRecommend(){
        List<AppInfo> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(SQLiteHelper.TABLE_NAME ,null ,"isRecommend=? and isDisplay=?" ,new String[]{"true","true"} ,null ,null ,"sequence");
        while(cursor.moveToNext()){
            AppInfo appInfo = new AppInfo();
            appInfo.setApkName(cursor.getString(cursor.getColumnIndex("apkName")));
            appInfo.setApkFileName(cursor.getString(cursor.getColumnIndex("apkFileName")));
            appInfo.setApkPackageName(cursor.getString(cursor.getColumnIndex("apkPackageName")));
            appInfo.setApkIconUrl(cursor.getString(cursor.getColumnIndex("apkIconUrl")));
            appInfo.setApkDownloadUrl(cursor.getString(cursor.getColumnIndex("apkDownloadUrl")));
            appInfo.setApkVersion(cursor.getString(cursor.getColumnIndex("apkVersion")));
            appInfo.setApkSize(cursor.getString(cursor.getColumnIndex("apkSize")));
            appInfo.setApkType(cursor.getString(cursor.getColumnIndex("apkType")));
            appInfo.setApkLanguage(cursor.getString(cursor.getColumnIndex("apkLanguage")));
            appInfo.setApkSummary(cursor.getString(cursor.getColumnIndex("apkSummary")));
            appInfo.setIsRecommend(cursor.getString(cursor.getColumnIndex("isRecommend")));
            appInfo.setIsDisplay(cursor.getString(cursor.getColumnIndex("isDisplay")));
            appInfo.setApkVersionCode(cursor.getInt(cursor.getColumnIndex("apkVersionCode")));
            appInfo.setSequence(cursor.getInt(cursor.getColumnIndex("sequence")));
            list.add(appInfo);
            if(appInfo!= null){
                appInfo =null;
            }
        }
        if(cursor!=null){
            cursor.close();
        }
        return list;
    }

    public List<AppInfo> queryByType(String type){
        List<AppInfo> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(SQLiteHelper.TABLE_NAME ,null ,"apkType=? and isDisplay=?" ,new String[]{type,"true"} ,null ,null ,"sequence");
        while(cursor.moveToNext()){
            AppInfo appInfo = new AppInfo();
            appInfo.setApkName(cursor.getString(cursor.getColumnIndex("apkName")));
            appInfo.setApkFileName(cursor.getString(cursor.getColumnIndex("apkFileName")));
            appInfo.setApkPackageName(cursor.getString(cursor.getColumnIndex("apkPackageName")));
            appInfo.setApkIconUrl(cursor.getString(cursor.getColumnIndex("apkIconUrl")));
            appInfo.setApkDownloadUrl(cursor.getString(cursor.getColumnIndex("apkDownloadUrl")));
            appInfo.setApkVersion(cursor.getString(cursor.getColumnIndex("apkVersion")));
            appInfo.setApkSize(cursor.getString(cursor.getColumnIndex("apkSize")));
            appInfo.setApkType(cursor.getString(cursor.getColumnIndex("apkType")));
            appInfo.setApkLanguage(cursor.getString(cursor.getColumnIndex("apkLanguage")));
            appInfo.setApkSummary(cursor.getString(cursor.getColumnIndex("apkSummary")));
            appInfo.setIsRecommend(cursor.getString(cursor.getColumnIndex("isRecommend")));
            appInfo.setIsDisplay(cursor.getString(cursor.getColumnIndex("isDisplay")));
            appInfo.setApkVersionCode(cursor.getInt(cursor.getColumnIndex("apkVersionCode")));
            appInfo.setSequence(cursor.getInt(cursor.getColumnIndex("sequence")));
            list.add(appInfo);
            if(appInfo!= null){
                appInfo =null;
            }
        }
        if(cursor!=null){
            cursor.close();
        }
        return list;
    }
}
