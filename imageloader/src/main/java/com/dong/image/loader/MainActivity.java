package com.dong.image.loader;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.dong.plugins.PluginActivity;

public class MainActivity extends PluginActivity {

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);
        setTitle("Plugin App");
        ((ImageView)findViewById(R.id.iv_logo)).setImageResource(R.drawable.android);
    }
}
