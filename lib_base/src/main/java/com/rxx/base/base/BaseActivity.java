package com.rxx.base.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.rxx.base.ZApplication;
import com.rxx.base.core.IController;
import com.rxx.base.theme.ThemeChange;
import com.rxx.theme.ZTheme;

/**
 * @author 冉超群
 * @date 2017/10/26-20:32
 * @desc BaseController
 */
public class BaseActivity<IV extends BaseView<IC>, IC extends BaseActivity> extends AppCompatActivity implements IController<IV>, ThemeChange {

    protected IV mView;

    @Override
    protected final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        zGetApplication().addThemeChange(this);
        deView();
    }

    /**
     * 替代 onCreate
     *
     * @param savedInstanceState
     */
    protected void zOnCreate(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void setView(IV view) {
        mView = view;
    }

    @Override
    public String getViewTag() {
        throw new RuntimeException("getViewTag must do");
    }

    @Override
    public final void onThemeChange(ZTheme zTheme) {
        deView();
        zOnThemeChange(zTheme);
    }

    private void deView() {
        setView(createThemeView(zGetApplication().getViewRelated().getClassNameByTag(getViewTag())));
        mView.setController((IC) this);
        setContentView(mView.getView());
    }

    /**
     * 替代 zOnThemeChange
     *
     * @param zTheme
     */
    public void zOnThemeChange(ZTheme zTheme) {

    }

    /**
     * 获取ThemeView
     *
     * @param
     */
    private final IV createThemeView(String className) {
        return (IV) zGetApplication().getZTheme().getThemeView(className);
    }

    /**
     * 获取Application
     *
     * @return
     */
    public ZApplication zGetApplication() {
        return (ZApplication) getApplication();
    }


    @Override
    protected final void onDestroy() {
        super.onDestroy();
        zGetApplication().remove(this);
        mView.deleteController();
        zOnDestroy();
    }

    /**
     * 提到onDestroy
     */
    protected void zOnDestroy() {

    }
}
