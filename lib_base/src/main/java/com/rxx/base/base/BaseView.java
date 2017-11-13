package com.rxx.base.base;

import android.content.Context;

import com.rxx.base.core.IView;
import com.rxx.theme.ZTheme;
import com.rxx.theme.ZThemeView;


/**
 * @author 冉超群
 * @date 2017/10/26-20:33
 * @desc BaseView
 */
public abstract class BaseView<IC extends BaseActivity> extends ZThemeView implements IView<IC> {

    protected IC mController;

    public BaseView(Context context, ZTheme iTheme) {
        super(context, iTheme);
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
