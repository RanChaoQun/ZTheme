package com.rxx.phonemate.module.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.rxx.phonemate.PhoneMateApplication;
import com.rxx.phonemate.lib.modules.login.ILoginController;

/**
 * @author 冉超群
 * @date 2017/10/26-15:00
 * @desc
 */
public class LoginActivity extends ILoginController {

    private int themeIndex = 0;

    @Override
    protected void zOnCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void doLogin() {
        Toast.makeText(this, "doLogin,name:"+mView.getAccountName()+",password:"+mView.getPassword(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void doChangeTheme() {
        if ((themeIndex % 2) == 0) {
            zGetApplication().changeTheme(((PhoneMateApplication) zGetApplication()).getLight(), null);
        } else {
            zGetApplication().changeTheme(((PhoneMateApplication) zGetApplication()).getDark(), null);
        }
        themeIndex++;
    }

}
