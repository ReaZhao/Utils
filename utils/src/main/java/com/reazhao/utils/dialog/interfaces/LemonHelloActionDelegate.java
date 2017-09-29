package com.reazhao.utils.dialog.interfaces;


import com.reazhao.utils.dialog.LemonHelloAction;
import com.reazhao.utils.dialog.LemonHelloInfo;
import com.reazhao.utils.dialog.LemonHelloView;

/**
 * LemonHello - 事件回调代理
 * Created by LiuRi on 2017/1/11.
 */

public interface LemonHelloActionDelegate {

    void onClick(
            LemonHelloView helloView,
            LemonHelloInfo helloInfo,
            LemonHelloAction helloAction
    );

}
