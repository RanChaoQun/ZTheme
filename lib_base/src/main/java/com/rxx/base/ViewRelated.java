package com.rxx.base;

/**
 * @author 冉超群
 * @date 2017/10/29-19:41
 * @desc 子类需要指定特定的类名
 * com.rxx.theme.phonemate.ViewRelated
 */
public interface ViewRelated {

    String LOAD_CLASSNAME = "com.rxx.theme.phonemate.ViewRelated";

    /**
     * 根据TAG 获取View
     * @return
     */
    String getClassNameByTag(String tag);
}
