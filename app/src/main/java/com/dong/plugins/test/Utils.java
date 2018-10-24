package com.dong.plugins.test;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

/**
 * @packInfo:com.dong.plugins.test
 * @author: yadong.qiu
 * Created by 邱亚东
 * Date: 2018/10/23
 * Time: 14:29
 */

public class Utils {

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};

    public static void verifyStoragePermissions(Activity activity){

        try {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
                if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(activity,PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
