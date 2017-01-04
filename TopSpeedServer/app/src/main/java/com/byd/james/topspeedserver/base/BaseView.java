package com.byd.james.topspeedserver.base;

/**
 * Created by james on 2016/12/20.
 */

public interface BaseView<T> {
    //设置数据接口的方法
    void setPresenter(T presenter);
    //展示错误信息
    void showError(String error);
}
