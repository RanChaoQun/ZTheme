package com.rxx.phonemate.lib.modules;

import com.rxx.base.ViewRelated;

/**
 * @author 冉超群
 * @date 2017/11/13-11:33
 * @desc
 */
public abstract class PhoneMateViewRelated implements ViewRelated {

    public static final String TAG_LOGIN = "view.tag.login";

    /**
     * 获取登陆ClassName
     *
     * @return
     */
    public abstract String getLoginViewClassName();

    @Override
    public String getClassNameByTag(String tag) {
        switch (tag) {
            case TAG_LOGIN:
                return getLoginViewClassName();
            default:
                return "";

        }
    }
}
