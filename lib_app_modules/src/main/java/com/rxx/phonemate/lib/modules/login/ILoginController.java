package com.rxx.phonemate.lib.modules.login;

import com.rxx.base.base.BaseActivity;

/**
 * @author 冉超群
 * @date 2017/10/26-15:37
 * @desc
 */
public abstract class ILoginController extends BaseActivity<ILoginView> {
    public abstract void doLogin();
}
