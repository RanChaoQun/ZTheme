package com.rxx.theme;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.res.Resources;
import android.text.TextUtils;
import android.view.Display;

import com.rxx.theme.context.ZContextThemeWrapper;
import com.rxx.theme.context.ZContextWrapper;
import com.rxx.theme.utils.ZThemeResourceUtils;
import com.rxx.theme.view.IThemeView;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import dalvik.system.DexClassLoader;

/**
 * @author 冉超群
 * @date 2017/10/19-17:39
 * @desc
 */
public class ZTheme {
    /**
     * 存放 theme，key:主题包路径 value:view
     */
    private static Map<String, ZTheme> cacheThemes = new HashMap<>();

    /**
     * ZThemeLayoutInflaterFactory 加载自定义View关键字
     */
    private static HashSet<String> loadViewClassKey = new HashSet<>();

    /**
     * 主题包原始Context
     */
    private Context baseContext;

    /**
     * themeName,使用appName
     */
    private String themeName;

    /**
     * themePath 主题包地址
     */
    private String themePath;

    /**
     * themePath 主题包so地址
     */
    private String themeLibraryPath;

    /**
     * DexClassLoader
     */
    private DexClassLoader themeClassLoader;

    /**
     * key:view 对象的className
     * value：ZThemeView
     */
    private Map<String, IThemeView> themeViews = new HashMap<>();

    /**
     * 自定义加载ThemeView
     */
    private Factory factory;

    public ZTheme(Context baseContext, String path, DexClassLoader themeClassLoader) {
        this(baseContext, path, null, themeClassLoader);
    }

    public ZTheme(Context baseContext, String path, String libraryPath, DexClassLoader themeClassLoader) {
        this.baseContext = baseContext;
        this.themePath = path;
        this.themeLibraryPath = libraryPath;
        this.themeClassLoader = themeClassLoader;
    }

    /**
     * 指定主题是否缓存
     *
     * @param path 主题包路径
     * @return 指定主题是否缓存
     */
    public static boolean themeIsCached(String path) {
        return cacheThemes.containsKey(path);
    }

    /**
     * @param apkPath
     */
    public static ZTheme createTheme(String apkPath, Context context) {
        return createTheme(apkPath, null, context);
    }

    /**
     * @param apkPath
     * @param libraryPath
     */
    public static ZTheme createTheme(String apkPath, String libraryPath, Context context) {
        ZTheme tITheme = null;
        if (TextUtils.isEmpty(apkPath)) {
            throw new RuntimeException("apkPath是空");
        }
        if (cacheThemes.containsKey(apkPath)) {
            tITheme = cacheThemes.get(apkPath);
        } else {
            PackageInfo packageInfo = ZThemeResourceUtils.loadApk(context, apkPath);
            DexClassLoader tDexClassLoader = new DexClassLoader(apkPath,
                    context.getFilesDir().getAbsolutePath(),
                    libraryPath,
                    context.getClassLoader());
            if (context instanceof Activity) {
                Context baseContext = createActivityContext((Activity) context);

                Resources tResources = ZThemeResourceUtils.createResources(baseContext, apkPath);
                android.content.res.Resources.Theme tTheme = ZThemeResourceUtils.createTheme(tResources, packageInfo.applicationInfo.theme);

                ZThemeResourceUtils.replaceContextImplResources(baseContext, tResources);
                ZThemeResourceUtils.replaceContextImplTheme(baseContext, tTheme, packageInfo.applicationInfo.theme);

                ZContextThemeWrapper tZContextThemeWrapper = new ZContextThemeWrapper(baseContext, packageInfo.applicationInfo.theme, tDexClassLoader);

                ZThemeResourceUtils.replaceContextThemeWrapperResources(tZContextThemeWrapper, tResources);
                ZThemeResourceUtils.replaceContextThemeWrapper(tZContextThemeWrapper, tTheme, packageInfo.applicationInfo.theme);

                tITheme = new ZTheme(tZContextThemeWrapper, apkPath, libraryPath, tDexClassLoader);
            } else if (context instanceof Application) {
                Context baseContext = createAppContext((Application) context);

                Resources tResources = ZThemeResourceUtils.createResources(baseContext, apkPath);
                ZThemeResourceUtils.replaceContextImplResources(baseContext, tResources);

                android.content.res.Resources.Theme tTheme = ZThemeResourceUtils.createTheme(tResources, packageInfo.applicationInfo.theme);
                ZThemeResourceUtils.replaceContextImplTheme(baseContext, tTheme, packageInfo.applicationInfo.theme);

                tITheme = new ZTheme(new ZContextWrapper(baseContext, tDexClassLoader), apkPath, libraryPath, tDexClassLoader);
            }
            if (tITheme != null) {
                cacheThemes.put(apkPath, tITheme);
            }
        }
        return tITheme;
    }

    /**
     * 配置自定义View加载关键字
     *
     * @param loadViewClassKeys
     */
    public static void addViewClassKey(String[] loadViewClassKeys) {
        loadViewClassKey.addAll(Arrays.asList(loadViewClassKeys));
    }

    public static HashSet<String> getLoadViewClassKey() {
        return loadViewClassKey;
    }

    private static Context createAppContext(Application application) {
        Context context = null;
        Object contextImpl = application.getBaseContext();
        try {
            Class tContextImplClass = Class.forName("android.app.ContextImpl");

            Field tLoadedApkField = tContextImplClass.getDeclaredField("mPackageInfo");
            tLoadedApkField.setAccessible(true);
            Object tLoadedApk = tLoadedApkField.get(contextImpl);

            Field tActivityThreadField = tContextImplClass.getDeclaredField("mMainThread");
            tActivityThreadField.setAccessible(true);
            Object tActivityThread = tActivityThreadField.get(contextImpl);

            Method[] methods = tContextImplClass.getDeclaredMethods();
            for (Method method : methods) {
                if ("createAppContext".equals(method.getName())) {
                    method.setAccessible(true);
                    context = (Context) method.invoke(contextImpl, tActivityThread, tLoadedApk);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return context;
    }

    private static Context createActivityContext(Activity activity) {
        Context context = null;
        Object contextImpl = activity.getBaseContext();
        try {
            Class tContextImplClass = Class.forName("android.app.ContextImpl");

            Field tLoadedApkField = tContextImplClass.getDeclaredField("mPackageInfo");
            tLoadedApkField.setAccessible(true);
            Object tLoadedApk = tLoadedApkField.get(contextImpl);

            Field tActivityThreadField = tContextImplClass.getDeclaredField("mMainThread");
            tActivityThreadField.setAccessible(true);
            Object tActivityThread = tActivityThreadField.get(contextImpl);

            Field tActivityTokenField = tContextImplClass.getDeclaredField("mActivityToken");
            tActivityTokenField.setAccessible(true);
            Object tActivityToken = tActivityTokenField.get(contextImpl);

            Field tDisplayField = tContextImplClass.getDeclaredField("mDisplay");
            tDisplayField.setAccessible(true);
            Display tDisplay = (Display) tDisplayField.get(contextImpl);

            Class tActivityClass = Class.forName("android.app.Activity");
            Field tConfigurationField = tActivityClass.getDeclaredField("mCurrentConfig");
            tConfigurationField.setAccessible(true);
            Object tConfiguration = tConfigurationField.get(activity);

            Method[] methods = tContextImplClass.getDeclaredMethods();
            for (Method method : methods) {
                if ("createActivityContext".equals(method.getName())) {
                    method.setAccessible(true);
                    /**
                     * createActivityContext(ActivityThread mainThread,
                     LoadedApk packageInfo, IBinder activityToken, int displayId,
                     Configuration overrideConfiguration)
                     */
                    context = (Context) method.invoke(contextImpl, tActivityThread, tLoadedApk, tActivityToken, tDisplay.getDisplayId(), tConfiguration);
                    break;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return context;
    }

    public boolean checkSecure() {
        return true;
    }

    public IThemeView getThemeView(String className) {
        if (viewIsCached(className)) {
            return themeViews.get(className);
        }
        IThemeView tIThemeView = null;
        if (factory != null) {
            tIThemeView = factory.getThemeView(className);
        }
        if (tIThemeView == null) {
            try {
                Class<?> tClass = getDexClassLoader().loadClass(className);
                Constructor<?> tConstructor = tClass.getConstructor(Context.class, ZTheme.class);
                tConstructor.setAccessible(true);
                tIThemeView = (IThemeView) tConstructor.newInstance(baseContext, this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (tIThemeView != null) {
            themeViews.put(className, tIThemeView);
            return tIThemeView;
        }
        return null;
    }

    public DexClassLoader getDexClassLoader() {
        return themeClassLoader;
    }

    public String getThemePath() {
        return themePath;
    }

    public String getLibraryPath() {
        return themeLibraryPath;
    }

    public boolean viewIsCached(String className) {
        return themeViews.containsKey(className);
    }

    public void setFactory(Factory factory) {
        this.factory = factory;
    }

    /**
     * 创建View对外暴露接口，可以自己实现
     */
    public interface Factory {
        IThemeView getThemeView(String className);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ZTheme)) {
            return false;
        }

        ZTheme zTheme = (ZTheme) o;

        if (getThemePath() != null ? !getThemePath().equals(zTheme.getThemePath()) : zTheme.getThemePath() != null) {
            return false;
        }
        return themeLibraryPath != null ? themeLibraryPath.equals(zTheme.themeLibraryPath) : zTheme.themeLibraryPath == null;
    }

}
