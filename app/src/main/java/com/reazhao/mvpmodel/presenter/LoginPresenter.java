package com.reazhao.mvpmodel.presenter;

import com.reazhao.mvpmodel.utils.StringUtils;
import com.reazhao.mvpmodel.view.ILoginView;

/**
 * author ReaZhao
 * Time 2017/3/29
 * E-mail 377742053@qq.com
 */

public class LoginPresenter {
    private ILoginView iLoginView;

    public LoginPresenter(ILoginView iLoginView) {
        this.iLoginView = iLoginView;
    }

    public void onLogin(String user, String password) {
        try {
            if (StringUtils.isEmpty(user)) {
                iLoginView.onEmpty("user");
                return;
            }
            if (StringUtils.isEmpty(password)) {
                iLoginView.onEmpty("password");
                return;
            }
            if (!user.equals("1") || !password.equals("1")) {
                iLoginView.onError();
                return;
            }
            if (user.equals("1") && password.equals("1")) {
                iLoginView.onSucceed(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
