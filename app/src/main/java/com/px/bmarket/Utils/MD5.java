package com.px.bmarket.Utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by PX on 2016/9/11.
 */
public class MD5 {
    public final static String MD5(String s) {
        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //getMD5 -STEP3
    public static String getApkMD5(Context context , String packageName){
        StringBuffer stringBuffer = new StringBuffer();
        String md5 = "";
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
            Signature signature = packageInfo.signatures[0];
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(signature.toByteArray());
            byte [] digset = messageDigest.digest();
            Log.d("----px----",digset.toString());
            md5 = byteArray2HexString(digset);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return md5;
    }
    //getMD5 -STEP2
    private static String byteArray2HexString (byte[] bytes){
        StringBuffer stringBuffer = new StringBuffer();
        int length = bytes.length;
        for (int i = 0; i <length ; i++) {
            byte2Hex(bytes[i] ,stringBuffer);
            if(i<length-1){
                stringBuffer.append(":");
            }
        }
        return stringBuffer.toString();
    }
    //getMD5 -STEP1
    private static void byte2Hex (byte b , StringBuffer sB){
        char[] hexChars = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        int high = ((b & 0xf0) >> 4);
        int low = (b & 0x0f);
        sB.append(hexChars[high]);
        sB.append(hexChars[low]);
    }

    public static String getFileMD5(File file) {
        if (!file.isFile()) {
            return null;
        }
        MessageDigest digest = null;
        FileInputStream in = null;
        byte buffer[] = new byte[1024];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        BigInteger bigInt = new BigInteger(1, digest.digest());
        Log.d("----px----" ,bigInt.toString(16));
        return bigInt.toString(16);
    }
}