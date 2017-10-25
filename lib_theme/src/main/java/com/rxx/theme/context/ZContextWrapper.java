package com.rxx.theme.context;

import android.content.Context;
import android.content.ContextWrapper;
import android.view.LayoutInflater;

import dalvik.system.DexClassLoader;

/**
 * @author 冉超群
 * @date 2017/10/19-17:42
 * @desc
 */
public class ZContextWrapper extends ContextWrapper {

    private LayoutInflater mLayoutInflater;

    private LayoutInflater.Factory mFactory;

    private DexClassLoader mDexClassLoader;

    public ZContextWrapper(Context base, DexClassLoader mDexClassLoader) {
        super(base);
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
