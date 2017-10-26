package com.rxx.phonemate.base;

import android.content.Context;

import com.rxx.phonemate.core.IController;
import com.rxx.phonemate.core.IView;
import com.rxx.theme.ZTheme;
import com.rxx.theme.ZThemeView;

/**
 * @author 冉超群
 * @date 2017/10/26-20:33
 * @desc BaseView
 */
public abstract class BaseView<IC extends IController> extends ZThemeView implements IView<IC> {

    IC mController;

    public BaseView(Context context, ZTheme iTheme) {
        super(context, iTheme);
    }

    public BaseView(Context context) {
        super(context, null);
    }

    @Override
    public void setController(IC controller) {
        this.mController = controller;
    }

    @Override
    public void deleteController() {
        mController = null;
    }

}
