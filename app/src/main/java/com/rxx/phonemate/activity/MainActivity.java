package com.rxx.phonemate.activity;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.rxx.phonemate.R;
import com.rxx.phonemate.config.ThemeConfig;
import com.rxx.theme.ZTheme;

public class MainActivity extends Activity implements View.OnClickListener {

    FrameLayout mLayoutRoot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(View.inflate(this, R.layout.activity_main, null));
        mLayoutRoot = (FrameLayout) findViewById(R.id.mLayoutRoot);
    }

    @Override
    public void onClick(View view) {
        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_CONTACT = 101;
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            //验证是否许可权限
            for (String str : permissions) {
                if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //申请权限
                    this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                    return;
                }
            }
        }

        long time = System.currentTimeMillis();
        Log.i("MainActivity", "load start:" + time);
        ZTheme zTheme = ZTheme.createTheme(ThemeConfig.APK_PATH, getApplicationContext());
        if (view.getId() == R.id.mButton1) {
            mLayoutRoot.addView(zTheme.getThemeView("com.rxx.phonemate.theme.dark.view.LauncherZThemeView").getView(), mLayoutRoot.getChildCount() - 1);
        } else if (view.getId() == R.id.mButton2) {
            mLayoutRoot.addView(zTheme.getThemeView("com.rxx.phonemate.theme.dark.view.DarkZThemeView1").getView(), mLayoutRoot.getChildCount() - 1);

        }
        Log.i("MainActivity", "load end:" + (System.currentTimeMillis() - time));
    }
}