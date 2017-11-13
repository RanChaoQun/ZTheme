package com.rxx.phonemate.lib.modules.login;

import android.content.Context;

import com.rxx.base.base.BaseView;
import com.rxx.theme.ZTheme;

/**
 * @author 冉超群
 * @date 2017/10/26-15:33
 * @desc 登陆页面
 */
public abstract class ILoginView extends BaseView<ILoginController>{

    public ILoginView(Context context, ZTheme iTheme) {
        super(context, iTheme);
    }

    public abstract String getAccountName();

    public abstract String getPassword();

    public abstract void showLogin();

    public abstract void showInput();
}
