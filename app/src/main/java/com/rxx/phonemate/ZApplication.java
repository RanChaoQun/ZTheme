package com.rxx.phonemate;

import android.app.Application;

import com.rxx.theme.ZTheme;

/**
 * @author 冉超群
 * @date 2017/10/22-11:38
 * @desc
 */
public class ZApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ZTheme.addViewClassKey(new String[]{"com.rxx.phonemate.theme.dark.view"});
    }
}
