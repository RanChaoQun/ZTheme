package com.rxx.theme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.rxx.theme.view.IThemeView;

/**
 * @author 冉超群
 * @date 2017/10/19-20:20
 * @desc ZThemeView
 */
public abstract class ZThemeView implements IThemeView {

    private final static String TAG = "ZThemeView";

    /**
     * 主题包的Context
     */
    private Context context;

    /**
     * ITheme
     */
    private ZTheme zTheme;

    public ZThemeView(Context context, ZTheme iTheme) {
        this.context = context;
        this.zTheme = iTheme;
    }

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public ZTheme getTheme() {
        return zTheme;
    }

    @Override
    public View inflateLayoutView(int layoutId) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        return inflater.inflate(context.getResources().getLayout(layoutId), null);
    }

}
