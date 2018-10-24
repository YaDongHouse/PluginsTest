package com.dong.plugins;

import android.util.Log;

/**
 * @packInfo:com.dong.plugins
 * @author: yadong.qiu
 * Created by 邱亚东
 * Date: 2018/10/22
 * Time: 18:09
 */

public class VLog {
    private final static String TAG = "vimerzhao_plugin";
    private final static boolean IS_DEBUG = true;

    public static void log(String info) {
        if (IS_DEBUG) Log.e(TAG,info);
    }
}
