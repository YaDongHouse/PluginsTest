package com.dong.plugins;

import android.content.pm.PackageInfo;
import android.content.res.Resources;

import dalvik.system.DexClassLoader;

/**
 * @packInfo:com.dong.plugins
 * @author: yadong.qiu
 * Created by 邱亚东
 * Date: 2018/10/22
 * Time: 18:03
 */

public class PluginApk {
    public PackageInfo packageInfo;
    public DexClassLoader classLoader;
    public Resources pluginRes;
    public PluginApk(Resources pluginRes){
        this.pluginRes = pluginRes;
    }
}
