package com.rxx.phonemate.lib.modules.login;

/**
 * @author 冉超群
 * @date 2017/10/26-15:33
 * @desc 登陆页面
 */
public interface ILoginView {

    String getAccountName();

    String getPassword();

    void showLogin();

    void showInput();
}
