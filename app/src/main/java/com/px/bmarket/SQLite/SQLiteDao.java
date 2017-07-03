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
        Cursor cursor = sqLiteDatabase.query(SQLiteHelper.TABLE_NAME ,null , "packageName=?" ,new String [] {packageName} ,null, null ,null);
        boolean isExists = cursor.moveToNext();
        if(cursor != null){
            cursor.close();
        }
        return isExists;
    }

    public void insertData(AppInfo appInfo){
        ContentValues contentValues = new ContentValues();
        contentValues.put("sequence",appInfo.getSequence());
        contentValues.put("name",appInfo.getName());
        contentValues.put("packageName",appInfo.getPackageName());
        contentValues.put("url",appInfo.getUrl());
        contentValues.put("icon",appInfo.getIcon());
        contentValues.put("size",appInfo.getSize());
        contentValues.put("version",appInfo.getVersion());
        contentValues.put("code",appInfo.getCode());
        contentValues.put("type",appInfo.getType());
        contentValues.put("language",appInfo.getLanguage());
        contentValues.put("recommend",appInfo.getRecommend());
        contentValues.put("display",appInfo.getDisplay());
        contentValues.put("summary",appInfo.getSummary());
        sqLiteDatabase.insert(SQLiteHelper.TABLE_NAME ,null ,contentValues);
        if(contentValues!=null){
            contentValues.clear();
            contentValues=null;
        }
    }

    public void updateData(AppInfo appInfo){
        ContentValues contentValues = new ContentValues();
        contentValues.put("sequence",appInfo.getSequence());
        contentValues.put("name",appInfo.getName());
        contentValues.put("packageName",appInfo.getPackageName());
        contentValues.put("url",appInfo.getUrl());
        contentValues.put("icon",appInfo.getIcon());
        contentValues.put("size",appInfo.getSize());
        contentValues.put("version",appInfo.getVersion());
        contentValues.put("code",appInfo.getCode());
        contentValues.put("type",appInfo.getType());
        contentValues.put("language",appInfo.getLanguage());
        contentValues.put("recommend",appInfo.getRecommend());
        contentValues.put("display",appInfo.getDisplay());
        contentValues.put("summary",appInfo.getSummary());
        sqLiteDatabase.update(SQLiteHelper.TABLE_NAME ,contentValues ,"packageName=?" ,new String []{appInfo.getPackageName()});
        if(contentValues!=null){
            contentValues.clear();
            contentValues=null;
        }
    }

    public void insertOrUpdateData(AppInfo appInfo){
        if(isExists(appInfo.getPackageName())){
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
        Cursor cursor = sqLiteDatabase.query(SQLiteHelper.TABLE_NAME ,null ,"_id>? and display=?" ,new String[]{"0","true"} ,null ,null ,"name");
        while(cursor.moveToNext()){
            AppInfo appInfo = new AppInfo();
            appInfo.setId(cursor.getInt(cursor.getColumnIndex("_id")));
            appInfo.setSequence(cursor.getInt(cursor.getColumnIndex("sequence")));
            appInfo.setName(cursor.getString(cursor.getColumnIndex("name")));
            appInfo.setPackageName(cursor.getString(cursor.getColumnIndex("packageName")));
            appInfo.setUrl(cursor.getString(cursor.getColumnIndex("url")));
            appInfo.setIcon(cursor.getString(cursor.getColumnIndex("icon")));
            appInfo.setSize(cursor.getString(cursor.getColumnIndex("size")));
            appInfo.setVersion(cursor.getString(cursor.getColumnIndex("version")));
            appInfo.setCode(cursor.getInt(cursor.getColumnIndex("code")));
            appInfo.setType(cursor.getString(cursor.getColumnIndex("type")));
            appInfo.setLanguage(cursor.getString(cursor.getColumnIndex("language")));
            appInfo.setRecommend(cursor.getString(cursor.getColumnIndex("recommend")));
            appInfo.setDisplay(cursor.getString(cursor.getColumnIndex("display")));
            appInfo.setSummary(cursor.getString(cursor.getColumnIndex("summary")));
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
        Cursor cursor = sqLiteDatabase.query(SQLiteHelper.TABLE_NAME ,null ,"recommend=? and display=?" ,new String[]{"true","true"} ,null ,null ,"name");
        while(cursor.moveToNext()){
            AppInfo appInfo = new AppInfo();
            appInfo.setId(cursor.getInt(cursor.getColumnIndex("_id")));
            appInfo.setSequence(cursor.getInt(cursor.getColumnIndex("sequence")));
            appInfo.setName(cursor.getString(cursor.getColumnIndex("name")));
            appInfo.setPackageName(cursor.getString(cursor.getColumnIndex("packageName")));
            appInfo.setUrl(cursor.getString(cursor.getColumnIndex("url")));
            appInfo.setIcon(cursor.getString(cursor.getColumnIndex("icon")));
            appInfo.setSize(cursor.getString(cursor.getColumnIndex("size")));
            appInfo.setVersion(cursor.getString(cursor.getColumnIndex("version")));
            appInfo.setCode(cursor.getInt(cursor.getColumnIndex("code")));
            appInfo.setType(cursor.getString(cursor.getColumnIndex("type")));
            appInfo.setLanguage(cursor.getString(cursor.getColumnIndex("language")));
            appInfo.setRecommend(cursor.getString(cursor.getColumnIndex("recommend")));
            appInfo.setDisplay(cursor.getString(cursor.getColumnIndex("display")));
            appInfo.setSummary(cursor.getString(cursor.getColumnIndex("summary")));
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
        Cursor cursor = sqLiteDatabase.query(SQLiteHelper.TABLE_NAME ,null ,"type=? and display=?" ,new String[]{type,"true"} ,null ,null ,"name");
        while(cursor.moveToNext()){
            AppInfo appInfo = new AppInfo();
            appInfo.setId(cursor.getInt(cursor.getColumnIndex("_id")));
            appInfo.setSequence(cursor.getInt(cursor.getColumnIndex("sequence")));
            appInfo.setName(cursor.getString(cursor.getColumnIndex("name")));
            appInfo.setPackageName(cursor.getString(cursor.getColumnIndex("packageName")));
            appInfo.setUrl(cursor.getString(cursor.getColumnIndex("url")));
            appInfo.setIcon(cursor.getString(cursor.getColumnIndex("icon")));
            appInfo.setSize(cursor.getString(cursor.getColumnIndex("size")));
            appInfo.setVersion(cursor.getString(cursor.getColumnIndex("version")));
            appInfo.setCode(cursor.getInt(cursor.getColumnIndex("code")));
            appInfo.setType(cursor.getString(cursor.getColumnIndex("type")));
            appInfo.setLanguage(cursor.getString(cursor.getColumnIndex("language")));
            appInfo.setRecommend(cursor.getString(cursor.getColumnIndex("recommend")));
            appInfo.setDisplay(cursor.getString(cursor.getColumnIndex("display")));
            appInfo.setSummary(cursor.getString(cursor.getColumnIndex("summary")));
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
