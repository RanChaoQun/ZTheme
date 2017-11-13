package com.rxx.phonemate;

import android.content.res.AssetManager;
import android.util.Log;

import com.rxx.base.ZApplication;
import com.rxx.phonemate.lib.modules.PhoneMateViewRelated;
import com.rxx.theme.ZTheme;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * @author 冉超群
 * @date 2017/10/22-11:38
 * @desc
 */
public class PhoneMateApplication extends ZApplication<PhoneMateViewRelated> {

    private final static String TAG = "PhoneMateApplication";

    public String[] themePaths;

    @Override
    public void onCreate() {
        super.onCreate();
        long tStatrtCopyTheme=System.currentTimeMillis();
        themePaths = new String[]{getFilesDir().getAbsolutePath() + File.separator + "theme_dark.apk",
                getFilesDir().getAbsolutePath() + File.separator + "theme_light.apk"};
        copyTheme();
        Log.d(TAG,"rady Theme Time:"+(System.currentTimeMillis()-tStatrtCopyTheme));
    }

    /**
     * 拷贝Theme
     */
    private void copyTheme() {
        long tCopyTheme=System.currentTimeMillis();
        copyAssetsFile(getAssets(), "theme", getFilesDir().getAbsolutePath());
        Log.d(TAG,"tCopyTheme:"+(System.currentTimeMillis()-tCopyTheme));
        initTheme();
        Log.d(TAG,"initTheme:"+(System.currentTimeMillis()-tCopyTheme));
    }

    /**
     * 加载Theme
     */
    private void initTheme() {
        mZTheme = ZTheme.createTheme(themePaths[0], this);
        ZTheme.createTheme(themePaths[1], this);
    }

    /**
     * 复制AssetsFile 到指定位置
     *
     * @param assetManager
     * @param assetsFileName
     * @param path           复制路径
     */
    private static void copyAssetsFile(AssetManager assetManager, String assetsFileName, String path) {
        try {
            String fileNames[] = assetManager.list(assetsFileName);// 获取assets目录下的所有文件及目录名
            if (fileNames.length > 0) {// 如果是目录
                File file = new File(path);
                file.mkdirs();// 如果文件夹不存在，则递归
                for (String fileName : fileNames) {
                    copyAssetsFile(assetManager, assetsFileName + "/" + fileName, path + "/" + fileName);
                }
            } else {// 如果是文件
                InputStream is = assetManager.open(assetsFileName);
                FileOutputStream fos = new FileOutputStream(new File(path));
                byte[] buffer = new byte[1024];
                int byteCount;
                while ((byteCount = is.read(buffer)) != -1) {// 循环从输入流读取
                    // buffer字节
                    fos.write(buffer, 0, byteCount);// 将读取的输入流写入到输出流
                }
                fos.flush();// 刷新缓冲区
                is.close();
                fos.close();
            }
        } catch (Exception e) {
            Log.e(TAG, "catch err:", e);
        }
    }

    public String getLight() {
        return themePaths[1];
    }

    public String getDark() {
        return themePaths[0];
    }

}
