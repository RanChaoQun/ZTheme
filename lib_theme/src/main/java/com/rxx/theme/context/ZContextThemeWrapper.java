package com.rxx.theme.context;

import android.content.Context;
import android.support.annotation.StyleRes;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;

import dalvik.system.DexClassLoader;

/**
 * @author 冉超群
 * @date 2017/10/19-17:40
 * @desc 插件 Activity 的Context
 */
public class ZContextThemeWrapper extends ContextThemeWrapper {

    private LayoutInflater mLayoutInflater;

    private LayoutInflater.Factory mFactory;

    private DexClassLoader mDexClassLoader;

    public ZContextThemeWrapper(Context base, @StyleRes int themeResId, DexClassLoader mDexClassLoader) {
        super(base, themeResId);
        this.mDexClassLoader = mDexClassLoader;
    }

    @Override
    public Object getSystemService(String var1) {
        if (LAYOUT_INFLATER_SERVICE.equals(var1)) {
            if (this.mLayoutInflater == null) {
                LayoutInflater tLayoutInflater = (LayoutInflater) super.getSystemService(var1);
                this.mLayoutInflater = tLayoutInflater.cloneInContext(this);
                if(mFactory==null){
                    mFactory=new ZThemeLayoutInflaterFactory(mDexClassLoader);
                }
                this.mLayoutInflater.setFactory(this.mFactory);
            }
            return this.mLayoutInflater;
        } else {
            return super.getSystemService(var1);
        }
    }
}
