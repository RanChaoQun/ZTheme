package com.rxx.theme.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.util.Log;
import android.view.ContextThemeWrapper;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author 冉超群
 * @date 2017/8/10-16:03
 * @desc ZThemeResourceUtils
 */
public class ZThemeResourceUtils {

    private final static String TAG = "ZThemeResourceUtils";

    /**
     * 创建插件AssetManager
     *
     * @param apkPath
     * @return
     */
    public static AssetManager createAssetManager(String apkPath) {
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            assetManager.getClass().getMethod("addAssetPath", String.class)
                    .invoke(assetManager, apkPath);
            return assetManager;
        } catch (Exception th) {
            th.printStackTrace();
        }
        return null;
    }


    /**
     * 创建插件Resources
     *
     * @param context
     * @param dexPath
     * @return
     */
    public static Resources createResources(Context context, String dexPath) {
        try {
            Resources superRes = context.getResources();
            superRes = new Resources(createAssetManager(dexPath), superRes.getDisplayMetrics(),
                    superRes.getConfiguration());
            Resources.Theme mTheme = superRes.newTheme();
            mTheme.setTo(context.getTheme());
            return superRes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param resources
     * @return 插件theme
     */
    public static Resources.Theme createTheme(Resources resources,int themeId) {
        Resources.Theme theme = resources.newTheme();
        theme.applyStyle(themeId, true);
        return theme;
    }

    public static PackageInfo loadApk(Context context, String apkPath) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo = packageManager.getPackageArchiveInfo(apkPath, 0);
        if (packageInfo != null) {
            packageInfo.applicationInfo.sourceDir = apkPath;
            packageInfo.applicationInfo.publicSourceDir = apkPath;
        }
        return packageInfo;
    }

    /**
     * 替换ZContextWrapper#ContextImpl的Resources资源
     *
     * @param context
     * @param resources
     * @return
     */
    public static Resources replaceContextImplResources(Context context, Resources resources) {
        Resources oldResources = null;
        try {
            Class tContextImplClass = Class.forName("android.app.ContextImpl");
            Field tmResourcesField = tContextImplClass.getDeclaredField("mResources");
            tmResourcesField.setAccessible(true);
            tmResourcesField.set(context, resources);
        } catch (Exception e) {
            Log.e(TAG, "replaceContextResources err ", e);
        }
        return oldResources;
    }

    /**
     * 替换ZContextWrapper#ContextImpl的Resources资源
     *
     * @param context
     * @param resources
     * @return
     */
    public static Resources replaceContextThemeWrapperResources(Context context, Resources resources) {
        Resources oldResources = null;
        try {
            Class tContextImplClass = Class.forName("android.view.ContextThemeWrapper");
            Field tmResourcesField = tContextImplClass.getDeclaredField("mResources");
            tmResourcesField.setAccessible(true);
            tmResourcesField.set(context, resources);
        } catch (Exception e) {
            Log.e(TAG, "replaceContextResources err ", e);
        }
        return oldResources;
    }

    /**
     * 使用反射的方式，使用Bundle的Resource对象，替换Context的mResources对象
     *
     * @param context
     */
    public static Resources.Theme replaceContextImplTheme(Context context, Resources.Theme theme, int themeId) {
        Resources.Theme oldTheme = null;
        try {
            Class tContextImplClass = Class.forName("android.app.ContextImpl");

            Field tContextImplTheme = tContextImplClass.getDeclaredField("mTheme");
            tContextImplTheme.setAccessible(true);
            oldTheme = (Resources.Theme) tContextImplTheme.get(context);
            tContextImplTheme.set(context, theme);

            Field tContextThemeWrapperThemeResourceField = tContextImplClass.getDeclaredField("mThemeResource");
            tContextThemeWrapperThemeResourceField.setAccessible(true);
            tContextThemeWrapperThemeResourceField.set(context, themeId);

            Method method = tContextImplClass.getDeclaredMethod("initializeTheme");
            method.setAccessible(true);
            method.invoke(context);
        } catch (Exception e) {
            Log.e(TAG, "replaceContextTheme err ", e);
        }
        return oldTheme;
    }

    /**
     * 使用反射的方式，使用Bundle的Resource对象，替换Context的mResources对象
     *
     * @param context
     */
    public static Resources.Theme replaceContextThemeWrapper(ContextThemeWrapper context, Resources.Theme theme, int themeId) {
        Resources.Theme oldTheme = null;
        try {
            Class tContextThemeWrapperClass = Class.forName("android.view.ContextThemeWrapper");

            Field tContextThemeWrapperThemeField = tContextThemeWrapperClass.getDeclaredField("mTheme");
            tContextThemeWrapperThemeField.setAccessible(true);
            oldTheme = (Resources.Theme) tContextThemeWrapperThemeField.get(context);
            tContextThemeWrapperThemeField.set(context, theme);

            Field tContextThemeWrapperThemeResourceField = tContextThemeWrapperClass.getDeclaredField("mThemeResource");
            tContextThemeWrapperThemeResourceField.setAccessible(true);
            tContextThemeWrapperThemeResourceField.set(context, themeId);

            Method method = tContextThemeWrapperClass.getDeclaredMethod("initializeTheme");
            method.setAccessible(true);
            method.invoke(context);
        } catch (Exception e) {
            Log.e(TAG, "replaceContextTheme err ", e);
        }
        return oldTheme;
    }
}
