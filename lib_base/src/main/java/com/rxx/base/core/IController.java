package com.rxx.base.core;

/**
 * @author 冉超群
 * @date 2017/10/26-20:28
 * @desc
 */
public interface IController<IV extends IView> {

    /**
     * 设置IView
     *
     * @param view
     */
    void setView(IV view);

    /**
     * IController 是否销毁
     *
     * @return
     */
    boolean isDestroyed();

    /**
     * 获取ViewTag
     *
     * @return
     */
    String getViewTag();
}
