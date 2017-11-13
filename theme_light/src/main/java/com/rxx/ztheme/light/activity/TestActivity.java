package com.rxx.ztheme.light.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.rxx.ztheme.light.view.LoginView;

/**
 * @author 冉超群
 * @date 2017/10/29-15:55
 * @desc
 */
public class TestActivity extends Activity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new LoginView(this,null).getView());
    }
}
