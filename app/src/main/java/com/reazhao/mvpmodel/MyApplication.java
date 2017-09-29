package com.reazhao.mvpmodel;

import android.app.Application;

import com.reazhao.utils.Utils;


/**
 * author ReaZhao
 * Time 2017/3/30
 * E-mail 377742053@qq.com
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
    }
}
