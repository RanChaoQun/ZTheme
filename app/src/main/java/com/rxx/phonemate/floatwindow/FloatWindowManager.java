package com.rxx.phonemate.floatwindow;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

import java.util.ArrayList;
import java.util.List;

public class FloatWindowManager {

    private static final String TAG = "FloatWindowManager";

    private static FloatWindowManager mFloatWindowManager;

    private WindowManager mWindowManager;

    private List<View> mFloatWindowLayout = new ArrayList<View>();

    private FloatWindowManager() {
    }

    public static synchronized FloatWindowManager getInstance() {
        if (mFloatWindowManager == null) {
            mFloatWindowManager = new FloatWindowManager();

        }
        return mFloatWindowManager;
    }

    /**
     * 创建悬浮窗
     * @param context
     * @param view
     * @return
     */
    public boolean createFloatWindow(Context context, View view) {
        return createFloatWindow(context, view,
                WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);

    }

    /**
     * 创建悬浮框
     *
     * @param context
     */
    public boolean createFloatWindow(Context context, View view, int layoutParamWidth, int layoutParamHeight) {
        return createFloatWindow(context, view, layoutParamWidth, layoutParamHeight, true);
    }

    public boolean createFloatWindow(Context context, View view, int layoutParamWidth, int layoutParamHeight, boolean isfocus) {
        if (mFloatWindowLayout.contains(view)) {
            Log.e(TAG, "createFloatWindow(), the view " + view + "already added!");
            return false;
        }
        mFloatWindowLayout.add(view);
        Log.i(TAG, "createFloatWindow(), mFloatWindowLayout=" + mFloatWindowLayout);
        Log.i(TAG, "add view is " + view);
        WindowManager windowManager = getWindowManager(context);
        LayoutParams mFloatWindowParams = null;
        mFloatWindowParams = new LayoutParams();

        mFloatWindowParams.format = PixelFormat.RGBA_8888;
        if (isfocus) {
            if (layoutParamHeight == LayoutParams.MATCH_PARENT) {
                mFloatWindowParams.type = LayoutParams.TYPE_SYSTEM_ERROR;
                mFloatWindowParams.flags = LayoutParams.FLAG_ALT_FOCUSABLE_IM | LayoutParams.FLAG_FULLSCREEN;
            } else {
                mFloatWindowParams.type = LayoutParams.TYPE_SYSTEM_ALERT;
                mFloatWindowParams.flags = LayoutParams.FLAG_ALT_FOCUSABLE_IM | LayoutParams.FLAG_FULLSCREEN;
            }
            view.setFocusableInTouchMode(true);
        } else {
//            mFloatWindowParams.type = LayoutParams.TYPE_SYSTEM_ALERT;//要放在最上层
            mFloatWindowParams.type = LayoutParams.TYPE_SYSTEM_OVERLAY;
            if (layoutParamHeight == LayoutParams.MATCH_PARENT) {
                mFloatWindowParams.flags = LayoutParams.FLAG_NOT_FOCUSABLE | LayoutParams.FLAG_FULLSCREEN | LayoutParams.FLAG_NOT_TOUCH_MODAL;
            } else {
                mFloatWindowParams.flags = LayoutParams.FLAG_NOT_FOCUSABLE | LayoutParams.FLAG_NOT_TOUCH_MODAL;
            }
        }

        mFloatWindowParams.gravity = Gravity.CENTER | Gravity.BOTTOM;
//      mFloatWindowParams.width = mFloatWindowLayout.width;
//      mFloatWindowParams.height = mFloatWindowLayout.height;
        mFloatWindowParams.width = layoutParamWidth;
        mFloatWindowParams.height = layoutParamHeight;
        mFloatWindowParams.x = 0;
        mFloatWindowParams.y = 0;

        windowManager.addView(view, mFloatWindowParams);
        return true;
    }

    /**
     * 移除悬浮框
     *
     * @param context
     */
    public void removeFloatWindow(Context context, View view) {
        if (mFloatWindowLayout != null) {
            Log.d(TAG, "removeFloatWindow()");
            if (mFloatWindowLayout.remove(view)) {//contains
                WindowManager windowManager = getWindowManager(context);
                windowManager.removeViewImmediate(view);
                Log.d(TAG, "removeFloatWindow finish " + view);
            }
        }
    }

    public boolean isTop(View view) {
        if (mFloatWindowLayout.size() > 0) {
            return mFloatWindowLayout.indexOf(view) == mFloatWindowLayout.size() - 1;
        }
        return false;
    }

    private WindowManager getWindowManager(Context context) {
        if (mWindowManager == null) {
            mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        }
        return mWindowManager;
    }

}
