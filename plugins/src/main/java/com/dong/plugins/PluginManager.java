package com.dong.plugins;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import dalvik.system.DexClassLoader;

/**
 * @packInfo:com.dong.plugins
 * @author: yadong.qiu
 * Created by 邱亚东
 * Date: 2018/10/23
 * Time: 9:59
 */

public class PluginManager {

    static class PluginMgrHolder{

        static PluginManager sManager = new PluginManager();

    }

    private static Context mContext;

    Map<String,PluginApk> sMap = new HashMap<>();


    public static PluginManager getInstance(){
        return PluginMgrHolder.sManager;
    }

    public PluginApk getPluginApk(String packageName){
        return sMap.get(packageName);
    }

    public static void init(Context context){
        mContext = context.getApplicationContext();
    }

    /**
     * 加载插件Apk
     * @param apkPath
     */
    public final void loadApk(String apkPath){
        PackageInfo packageInfo = queryPackageInfo(apkPath);
        if (packageInfo == null || TextUtils.isEmpty(packageInfo.packageName)){
            return;
        }
        //check cache
        PluginApk pluginApk = sMap.get(packageInfo.packageName);

        if (pluginApk == null){
            pluginApk = createApk(apkPath);
            if (pluginApk != null){
                pluginApk.packageInfo = packageInfo;
                sMap.put(packageInfo.packageName,pluginApk);
            }else {
                throw new NullPointerException("PluginApk is null");
            }
        }

    }

    /**
     * 根据插件apk的路径  获取插件的Resources 以及dexClassLoader
     * @param apkPath
     * @return
     */
    private PluginApk createApk(String apkPath){
        String addAssetPathMethod = "addAssetPath";
        PluginApk pluginApk = null;

        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPath = assetManager.getClass().getMethod(addAssetPathMethod, String.class);
            addAssetPath.invoke(assetManager,apkPath);


            Resources  pluginRes =  new Resources(assetManager,
                                        mContext.getResources().getDisplayMetrics(),
                                        mContext.getResources().getConfiguration());



            pluginApk = new PluginApk(pluginRes);
            pluginApk.classLoader = createDexClassLoader(apkPath);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return pluginApk;
    }


    /**
     * 根据路径获取包信息
     * @param apkPath
     * @return
     */
    private PackageInfo queryPackageInfo(String apkPath){
        PackageInfo packageInfo = mContext.getPackageManager().getPackageArchiveInfo(apkPath,
                PackageManager.GET_ACTIVITIES | PackageManager.GET_SERVICES);
        if (packageInfo == null){
            return null;
        }
        return packageInfo;
    }

    /**
     * 根据apk文件获取类加载器
     * @param apkPath
     * @return
     */
    private DexClassLoader createDexClassLoader (String apkPath){
        File dexOutPutDir = mContext.getDir("dex", Context.MODE_PRIVATE);
        DexClassLoader loader = new DexClassLoader(apkPath,dexOutPutDir.getAbsolutePath(),
                null,mContext.getClassLoader());
        return loader;
    }


    public void startActivity(Intent intent){
        Intent pluginIntent = new Intent(mContext,ProxyActivity.class);
        Bundle extra = intent.getExtras();
        //complicate if statement
        if (extra == null || !extra.containsKey(Constants.PLUGIN_CLASS_NAME)){
            try {
                throw new IllegalAccessException("lack class of plugin and package name");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        pluginIntent.putExtras(intent);
        pluginIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(pluginIntent);
    }



}
