package com.rxx.phonemate.theme.dark.view;

import android.content.Context;
import android.view.View;

import com.rxx.phonemate.theme.dark.R;
import com.rxx.theme.ZTheme;
import com.rxx.theme.ZThemeView;


/**
 * @author 冉超群
 * @date 2017/10/22-15:32
 * @desc
 */
public class DarkZThemeView2 extends ZThemeView {

    public DarkZThemeView2(Context context, ZTheme iTheme) {
        super(context, iTheme);
    }

    @Override
    public View getView() {
        return inflateLayoutView(R.layout.activity_main);
    }
}
