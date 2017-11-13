package com.rxx.ztheme.dark.view;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.rxx.phonemate.lib.modules.login.ILoginView;
import com.rxx.theme.ZTheme;
import com.rxx.theme.view.ViewType;
import com.rxx.ztheme.dark.R;

/**
 * @author 冉超群
 * @date 2017/10/26-21:16
 * @desc
 */
public class LoginView extends ILoginView implements View.OnClickListener {

    private LinearLayout mLayoutAccount;

    private ProgressBar mLoginProgressBar;

    private EditText mEditTextAccount;

    private EditText mEditTextPassword;

    private Button mButtonLogin;

    private Button mButtonChange;

    public LoginView(Context context, ZTheme zTheme) {
        super(context, zTheme);
        setContentView(R.layout.activity_login);
        mLayoutAccount = (LinearLayout) findViewById(R.id.mLayoutAccount);
        mLoginProgressBar = (ProgressBar) findViewById(R.id.mLoginProgressBar);
        mEditTextAccount = (EditText) findViewById(R.id.mEditTextAccount);
        mEditTextPassword = (EditText) findViewById(R.id.mEditTextPassword);
        mButtonLogin = (Button) findViewById(R.id.mButtonLogin);
        mButtonChange = (Button) findViewById(R.id.mButtonChange);

        mButtonLogin.setOnClickListener(this);
        mButtonChange.setOnClickListener(this);
    }

    @Override
    public String getAccountName() {
        return mEditTextAccount.getText().toString();
    }

    @Override
    public String getPassword() {
        return mEditTextPassword.getText().toString();
    }

    @Override
    public void showLogin() {
        mLoginProgressBar.setVisibility(View.VISIBLE);
        mLayoutAccount.setVisibility(View.GONE);
    }

    @Override
    public void showInput() {
        mLoginProgressBar.setVisibility(View.GONE);
        mLayoutAccount.setVisibility(View.VISIBLE);
    }

    @Override
    public ViewType getViewType() {
        return ViewType.VIEW_PLUG;
    }

    @Override
    public void onClick(View view) {
        if (view == mButtonLogin && mController != null && !mController.isDestroyed()) {
            mController.doLogin();
        } else if (view == mButtonChange && mController != null && !mController.isDestroyed()) {
            mController.doChangeTheme();
        }
    }

}
