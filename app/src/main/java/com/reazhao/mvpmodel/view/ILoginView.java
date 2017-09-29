package com.reazhao.mvpmodel.view;

/**
 * author ReaZhao
 * Time 2017/3/29
 * E-mail 377742053@qq.com
 */

public interface ILoginView {
    /**
     * 登录成功
     * @param isSucceed true  登录成功 false 登录失败
     */
     void onSucceed(boolean isSucceed);

    /**
     * 用户名或错误
     */
     void onError();

    /**
     *  用户名和密码不能为空
     * @param empty  用户名或者密码
     */
    void onEmpty(String empty);


}
