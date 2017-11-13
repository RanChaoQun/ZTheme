package com.rxx.base.core;

import android.view.View;

/**
 * @author 冉超群
 * @date 2017/10/26-20:27
 * @desc
 */
public interface IView<IC extends IController> {

    void setController(IC controller);

    void deleteController();

    View getView();
}
