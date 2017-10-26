package com.rxx.phonemate.module.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.rxx.phonemate.R;
import com.rxx.phonemate.base.BaseActivity;
import com.rxx.phonemate.lib.modules.login.ILoginController;

/**
 * @author 冉超群
 * @date 2017/10/26-15:00
 * @desc
 */
public class LoginActivity extends BaseActivity implements ILoginController {

    private LinearLayout mLayoutAccount;

    private ProgressBar mLoginProgressBar;

    private EditText mEditTextAccount;

    private EditText mEditTextPassword;

    private Button mButtonLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mLayoutAccount = (LinearLayout) findViewById(R.id.mLayoutAccount);
        mLoginProgressBar = (ProgressBar) findViewById(R.id.mLoginProgressBar);
        mEditTextAccount = (EditText) findViewById(R.id.mEditTextAccount);
        mEditTextPassword = (EditText) findViewById(R.id.mEditTextPassword);
        mButtonLogin = (Button) findViewById(R.id.mButtonLogin);
    }

    @Override
    public void doLogin() {

    }
}
