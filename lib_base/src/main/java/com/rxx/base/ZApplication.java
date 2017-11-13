package com.rxx.base;

import android.app.Application;
import android.text.TextUtils;

import com.rxx.base.theme.ThemeChange;
import com.rxx.theme.ZTheme;

import java.lang.reflect.Constructor;
import java.util.HashSet;
import java.util.Set;

/**
 * @author 冉超群
 * @date 2017/10/27-10:55
 * @desc
 */
public class ZApplication<VR extends ViewRelated> extends Application {

    protected ZTheme mZTheme;

    private final static Set<ThemeChange> mThemeChanges = new HashSet<>();

    public ZTheme getZTheme() {
        return mZTheme;
    }

    /**
     * add  ThemeChange
     *
     * @param tag
     */
    public void addThemeChange(ThemeChange tag) {
        mThemeChanges.add(tag);
    }

    /**
     * 移除 ThemeChange
     *
     * @param tag
     */
    public void remove(ThemeChange tag) {
        mThemeChanges.remove(tag);
    }

    /**
     * 切换主题
     *
     * @param apkPath
     * @param libraryPath
     */
    public void changeTheme(String apkPath, String libraryPath) {
        if (TextUtils.equals(mZTheme.getThemePath(), apkPath) && TextUtils.equals(mZTheme.getLibraryPath(), libraryPath)) {
            return;
        }
        mZTheme = ZTheme.createTheme(apkPath, libraryPath, this);
        for (ThemeChange themeChange : mThemeChanges) {
            themeChange.onThemeChange(mZTheme);
        }
    }

    public VR getViewRelated() {
        VR phoneMateViewRelated = null;
        try {
            Class<?> tClass = getZTheme().getDexClassLoader().loadClass(ViewRelated.LOAD_CLASSNAME);
            Constructor<?> tConstructor = tClass.getConstructor();
            tConstructor.setAccessible(true);
            phoneMateViewRelated = (VR) tConstructor.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return phoneMateViewRelated;
    }

}
