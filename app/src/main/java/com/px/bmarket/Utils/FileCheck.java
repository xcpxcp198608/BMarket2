package com.px.bmarket.Utils;

import com.px.bmarket.F;

import java.io.File;

/**
 * Created by PX on 2016/9/17.
 */
public class FileCheck {

    //通过file path 和 file full name 判断文件是否存在
    public static boolean isFileExists(String filePath ,String fileFullName ) {
        try {
            File file = new File(filePath + fileFullName);
            if (file.exists()) {
                Logger.d("----file is exists");
                return true;
            } else {
                Logger.d("----file is not exists");
                return false;
            }
        } catch (Exception e) {
            Logger.d("----file is not exists");
            return false;
        }
    }

    public static boolean isFileIntact (String filePath,String fileName,String md5){
        String localMD5 = MD5.getFileMD5(filePath ,fileName);
        Logger.d(localMD5);
        if(localMD5.equals(md5)){
            Logger.d("----file is intact");
            return true;
        }else{
            Logger.d("----file is not intact");
            return false;
        }
    }
}
