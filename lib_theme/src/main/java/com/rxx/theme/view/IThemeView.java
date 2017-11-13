package com.rxx.theme.view;

import android.content.Context;
import android.view.View;

import com.rxx.theme.ZTheme;

/**
 * @author 冉超群
 * @date 2017/10/19-20:28
 * @desc
 */
public interface IThemeView {

    /**
     * 获取主题的Context
     *
     * @return
     */
    Context getContext();

    /**
     * 获取主题View
     *
     * @return
     */
    View getView();

    /**
     * 获取IZTheme
     *
     * @return
     */
    ZTheme getTheme();

    /**
     * 加载布局文件
     *
     * @return
     */
    View inflateLayoutView(int layoutId);

    void setContentView(View view);

    void setContentView(int layoutResID);

    /**
     * View 类型
     *
     * @return
     */
    ViewType getViewType();

    /**
     * 加载控件
     *
     * @param viewId
     * @return
     */
    View findViewById(int viewId);
}
