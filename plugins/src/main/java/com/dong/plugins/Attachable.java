package com.dong.plugins;

/**
 * @packInfo:com.dong.plugins
 * @author: yadong.qiu
 * Created by 邱亚东
 * Date: 2018/10/22
 * Time: 18:02
 */

public interface Attachable<T> {

    void attach(T proxy,PluginApk apk);
}
