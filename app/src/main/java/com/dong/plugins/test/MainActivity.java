package com.dong.plugins.test;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.dong.plugins.Constants;
import com.dong.plugins.PluginManager;
import com.dong.plugins.VLog;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    public final static String PLUGIN_NAME = "plugin.apk";
    public final static String PLUGIN_PACKAGE_NAME = "com.dong.image.loader";
    public final static String PLUGIN_CLASS_NAME = "com.dong.image.loader.MainActivity";
    PluginManager mPluginManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Utils.verifyStoragePermissions(this);

        PluginManager.init(getApplicationContext());
        mPluginManager = PluginManager.getInstance();
        String pluginApkPath = Environment.getExternalStorageDirectory()+
                File.separator+"plugins"+File.separator+PLUGIN_NAME;

        VLog.log("can read:"+Environment.getExternalStorageDirectory().canRead());
        VLog.log(pluginApkPath);
        mPluginManager.loadApk(pluginApkPath);


        findViewById(R.id.tv_content).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(Constants.PACKAGE_NAME,PLUGIN_PACKAGE_NAME);
                intent.putExtra(Constants.PLUGIN_CLASS_NAME,PLUGIN_CLASS_NAME);
                mPluginManager.startActivity(intent);
            }
        });
    }
}
