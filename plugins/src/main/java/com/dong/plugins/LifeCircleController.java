package com.dong.plugins;

import android.app.Activity;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;

import java.lang.reflect.Constructor;

/**
 * @packInfo:com.dong.plugins
 * @author: yadong.qiu
 * Created by 邱亚东
 * Date: 2018/10/23
 * Time: 9:56
 */

public class LifeCircleController implements Pluginable {

    /**
     * 被代理的Activity
     */
    Activity mProxy;

    PluginActivity mPlugin;
    Resources mResources;
    Resources.Theme mTheme;
    PluginApk mPluginApk;
    String mPluginClass;

    public LifeCircleController(Activity mProxy) {
        this.mProxy = mProxy;
    }

    @Override
    public void onCreate(Bundle bundle) {
        /**
         * 插件的启动的类全名
         */
        mPluginClass = bundle.getString(Constants.PLUGIN_CLASS_NAME);
        /**
         * 插件的包名
         */
        String packageName = bundle.getString(Constants.PACKAGE_NAME);
        /**
         * 目标类的Resources，PackageInfo以及以及apk的dexClassLoader
         */
        mPluginApk = PluginManager.getInstance().getPluginApk(packageName);
        try {
            /**
             * 构建目标类的对象
             */
            mPlugin = (PluginActivity) loadPluginable(mPluginApk.classLoader,mPluginClass);
            mPlugin.attach(mProxy,mPluginApk);
            mResources = mPluginApk.pluginRes;
            mPlugin.onCreate(bundle);
        } catch (Exception e) {
            VLog.log("Fail in LifeCircleController onCreate");
            VLog.log(e.getMessage());
            e.printStackTrace();

        }

    }

    /**
     * 通过dexClassLoader构建目标Activity
     * @param classLoader
     * @param pluginActivityClass
     * @return
     * @throws Exception
     */
    private Object loadPluginable(ClassLoader classLoader,String pluginActivityClass) throws Exception{
        Class<?> pluginClz = classLoader.loadClass(pluginActivityClass);
        Constructor<?> constructor = pluginClz.getConstructor(new Class[] {});
        constructor.setAccessible(true);
        return constructor.newInstance(new Object[] {});
    }





    @Override
    public void onStart() {
        if (mPlugin != null){
            mPlugin.onStart();
        }

    }

    @Override
    public void onResume() {
        if (mPlugin != null){
            mPlugin.onResume();
        }
    }

    @Override
    public void onStop() {
        if (mPlugin != null){
            mPlugin.onStop();
        }
    }

    @Override
    public void onPause() {
        if (mPlugin != null){
            mPlugin.onPause();
        }
    }

    @Override
    public void onDestroy() {
        if (mPlugin != null){
            mPlugin.onDestroy();
        }
    }

    public Resources.Theme getTheme(){
        return mTheme;
    }

    public Resources getResources(){
        return mResources;
    }

    public AssetManager getAssets(){
        return mResources.getAssets();
    }
}
