package com.rxx.theme.context;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.rxx.theme.ZTheme;

import java.lang.reflect.Constructor;
import java.util.HashMap;

import dalvik.system.DexClassLoader;

/**
 * @author 冉超群
 * @date 2017/10/20-09:52
 * @desc
 */
public class ZThemeLayoutInflaterFactory implements LayoutInflater.Factory {

    private HashMap<String, Constructor<?>> createConstructor = new HashMap<>();

    private DexClassLoader mDexClassLoader;

    public ZThemeLayoutInflaterFactory(DexClassLoader mDexClassLoader) {
        this.mDexClassLoader = mDexClassLoader;
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        for (String key : ZTheme.getLoadViewClassKey()) {
            if (name.contains(key)) {
                View tView = null;
                Constructor tConstructor = createConstructor.get(name);
                if (tConstructor == null) {
                    try {
                        Class tClass = mDexClassLoader.loadClass(name);
                        tConstructor = tClass.getConstructor(Context.class, AttributeSet.class);
                        createConstructor.put(name, tConstructor);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                try {
                    tView = (View) tConstructor.newInstance(context, attrs);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return tView;
            }
        }
        return null;
    }

}
