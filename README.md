
![Demo演示](https://gitee.com/uploads/images/2017/1113/165926_a57bc77c_414444.gif "aaa.gif")
- ZTheme 使用示例
1.  创建ZTheme方法声明

```
    ZTheme createTheme(String apkPath, Context context);
    ZTheme createTheme(String apkPath, String libraryPath, Context context)

```
2. 使用apk创建ZTheme对象
```
     mZTheme = ZTheme.createTheme("/sdcard/theme/theme_dark.apk", this);

```
3.创建 ZThemeView  方法声明
```
    //根据反射创建ZThemeView对象，此处传入类名
    IThemeView getThemeView(String className)

```
4.创建 ZThemeView 对象
```
    IThemeView.getThemeView("com.rxx.ztheme.dark.view.LoginView");
```

5.最后就可以获取到一个view，加载到Activity。
```
  setContentView(IThemeView.getView());
```


