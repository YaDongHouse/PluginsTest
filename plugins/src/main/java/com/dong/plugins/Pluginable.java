package com.dong.plugins;

import android.os.Bundle;

/**
 * @packInfo:com.dong.plugins
 * @author: yadong.qiu
 * Created by 邱亚东
 * Date: 2018/10/22
 * Time: 18:10
 */

public interface Pluginable {

    public void onCreate(Bundle bundle);

    public void onStart();

    public void onResume();

    public void onStop();

    public void onPause();

    public void onDestroy();

}
