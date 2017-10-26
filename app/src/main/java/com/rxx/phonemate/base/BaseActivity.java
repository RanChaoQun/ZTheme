package com.rxx.phonemate.base;

import android.support.v7.app.AppCompatActivity;

import com.rxx.phonemate.core.IController;
import com.rxx.phonemate.core.IView;

/**
 * @author 冉超群
 * @date 2017/10/26-20:32
 * @desc BaseController
 */
public class BaseActivity<IV extends IView> extends AppCompatActivity implements IController<IV> {

    private IV mView;

    @Override
    public void setView(IV view) {
        mView = view;
    }

}
