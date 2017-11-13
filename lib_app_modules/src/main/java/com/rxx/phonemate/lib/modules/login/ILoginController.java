package com.rxx.phonemate.lib.modules.login;

import com.rxx.base.base.BaseActivity;
import com.rxx.phonemate.lib.modules.PhoneMateViewRelated;

/**
 * @author 冉超群
 * @date 2017/10/26-15:37
 * @desc
 */
public abstract class ILoginController extends BaseActivity<ILoginView,ILoginController> {

    /**
     * 登录
     */
    public abstract void doLogin();

    /**
     * 处理切换主题
     */
    public abstract void doChangeTheme();

    @Override
    public String getViewTag() {
        return PhoneMateViewRelated.TAG_LOGIN;
    }
}
