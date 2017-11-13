ZTheme是一款小巧的Android换肤框架，实现原理是动态加载APK，对基于MVP或MVC分层设计的View层进行替换，实现切换主题皮肤的功能。

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


- Demo代码示例，一个基于MVC模式的简单实现。
1.  Controller 层定义 ：ILoginController
```
public abstract class ILoginController extends BaseActivity<ILoginView,ILoginController> {

    /**
     * 登录
     */
    public abstract void doLogin();

    /**
     * 处理切换主题
     */
    public abstract void doChangeTheme();

    @Override
    public String getViewTag() {
        return PhoneMateViewRelated.TAG_LOGIN;
    }
}
```

2. Controller 实现 ： LoginActivity 
```
public class LoginActivity extends ILoginController {

    private int themeIndex = 0;

    @Override
    protected void zOnCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void doLogin() {
        Toast.makeText(this, "doLogin,name:"+mView.getAccountName()+",password:"+mView.getPassword(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void doChangeTheme() {
        if ((themeIndex % 2) == 0) {
            zGetApplication().changeTheme(((PhoneMateApplication) zGetApplication()).getLight(), null);
        } else {
            zGetApplication().changeTheme(((PhoneMateApplication) zGetApplication()).getDark(), null);
        }
        themeIndex++;
    }

}
```
3.View层定义： ILoginView 
```
public abstract class ILoginView extends BaseView<ILoginController>{

    public ILoginView(Context context, ZTheme iTheme) {
        super(context, iTheme);
    }

    public abstract String getAccountName();

    public abstract String getPassword();

    public abstract void showLogin();

    public abstract void showInput();
}

```
4.View层实现 ：

Dark主题
```
public class LoginView extends ILoginView implements View.OnClickListener {

    private LinearLayout mLayoutAccount;

    private ProgressBar mLoginProgressBar;

    private EditText mEditTextAccount;

    private EditText mEditTextPassword;

    private Button mButtonLogin;

    private Button mButtonChange;

    public LoginView(Context context, ZTheme zTheme) {
        super(context, zTheme);
        setContentView(R.layout.activity_login);
        mLayoutAccount = (LinearLayout) findViewById(R.id.mLayoutAccount);
        mLoginProgressBar = (ProgressBar) findViewById(R.id.mLoginProgressBar);
        mEditTextAccount = (EditText) findViewById(R.id.mEditTextAccount);
        mEditTextPassword = (EditText) findViewById(R.id.mEditTextPassword);
        mButtonLogin = (Button) findViewById(R.id.mButtonLogin);
        mButtonChange = (Button) findViewById(R.id.mButtonChange);

        mButtonLogin.setOnClickListener(this);
        mButtonChange.setOnClickListener(this);
    }

    @Override
    public String getAccountName() {
        return mEditTextAccount.getText().toString();
    }

    @Override
    public String getPassword() {
        return mEditTextPassword.getText().toString();
    }

    @Override
    public void showLogin() {
        mLoginProgressBar.setVisibility(View.VISIBLE);
        mLayoutAccount.setVisibility(View.GONE);
    }

    @Override
    public void showInput() {
        mLoginProgressBar.setVisibility(View.GONE);
        mLayoutAccount.setVisibility(View.VISIBLE);
    }

    @Override
    public ViewType getViewType() {
        return ViewType.VIEW_PLUG;
    }

    @Override
    public void onClick(View view) {
        if (view == mButtonLogin && mController != null && !mController.isDestroyed()) {
            mController.doLogin();
        } else if (view == mButtonChange && mController != null && !mController.isDestroyed()) {
            mController.doChangeTheme();
        }
    }
}
```

 1. light 主题

```
public class LoginView extends ILoginView implements View.OnClickListener {

    private LinearLayout mLayoutAccount;

    private ProgressBar mLoginProgressBar;

    private EditText mEditTextAccount;

    private EditText mEditTextPassword;

    private Button mButtonLogin;

    private Button mButtonChange;

    public LoginView(Context context, ZTheme zTheme) {
        super(context, zTheme);
        setContentView(R.layout.activity_login);
        mLayoutAccount = (LinearLayout) findViewById(R.id.mLayoutAccount);
        mLoginProgressBar = (ProgressBar) findViewById(R.id.mLoginProgressBar);
        mEditTextAccount = (EditText) findViewById(R.id.mEditTextAccount);
        mEditTextPassword = (EditText) findViewById(R.id.mEditTextPassword);
        mButtonLogin = (Button) findViewById(R.id.mButtonLogin);
        mButtonChange = (Button) findViewById(R.id.mButtonChange);

        mButtonLogin.setOnClickListener(this);
        mButtonChange.setOnClickListener(this);
    }

    @Override
    public String getAccountName() {
        return mEditTextAccount.getText().toString();
    }

    @Override
    public String getPassword() {
        return mEditTextPassword.getText().toString();
    }

    @Override
    public void showLogin() {
        mLoginProgressBar.setVisibility(View.VISIBLE);
        mLayoutAccount.setVisibility(View.GONE);
    }

    @Override
    public void showInput() {
        mLoginProgressBar.setVisibility(View.GONE);
        mLayoutAccount.setVisibility(View.VISIBLE);
    }

    @Override
    public ViewType getViewType() {
        return ViewType.VIEW_PLUG;
    }

    @Override
    public void onClick(View view) {
        if (view == mButtonLogin && mController != null && !mController.isDestroyed()) {
            mController.doLogin();
        } else if (view == mButtonChange && mController != null && !mController.isDestroyed()) {
            mController.doChangeTheme();
        }
    }
```
