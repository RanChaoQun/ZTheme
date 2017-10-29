package com.rxx.phonemate.module.login;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.rxx.phonemate.lib.modules.login.ILoginController;

/**
 * @author 冉超群
 * @date 2017/10/26-15:00
 * @desc
 */
public class LoginActivity extends ILoginController {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mView.getView());
        mView.showInput();
    }

    @Override
    public void doLogin() {
        mView.showLogin();
    }
}
