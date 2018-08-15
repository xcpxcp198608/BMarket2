package com.px.bmarket.Utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.widget.Toast;

import java.io.File;

/**
 * Created by Administrator on 2016/8/20.
 */
public class ApkInstall {

    public static void installApk(Context context, String filePath , String fileName, String permission){
        if(Build.VERSION.SDK_INT > 23){
            installApkAfter7(context, filePath, fileName, permission);
        }else{
            installApkBefore7(context, filePath, fileName);
        }
    }

    /**
     * 安装apk文件
     * @param filePath 文件绝对路径
     * @param fileName 文件名称
     */
    private static void installApkBefore7 (Context context, String filePath , String fileName){
        File file = new File(filePath, fileName);
        if(!file.exists()) {
            Toast.makeText(context , "Apk file is not exists" ,Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file),"application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    /**
     * 7.0 后安装apk文件
     * @param filePath 文件绝对路径
     * @param fileName 文件名称
     * @param permission manifests 中配置的file provider的permission
     */
    private static void installApkAfter7(Context context, String filePath , String fileName, String permission){
        File file = new File(filePath, fileName);
        if(!file.exists()) {
            Toast.makeText(context , "Apk file is not exists" ,Toast.LENGTH_SHORT).show();
            return;
        }
        Uri uri ;
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            uri = FileProvider.getUriForFile(context, permission, file);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }else{
            uri = Uri.fromFile(file);
        }
        intent.setDataAndType(uri,"application/vnd.android.package-archive");
        context.startActivity(intent);
    }

}
