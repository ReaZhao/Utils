package com.reazhao.utils.dialog.adapter;


import com.reazhao.utils.dialog.LemonHelloAction;
import com.reazhao.utils.dialog.LemonHelloInfo;
import com.reazhao.utils.dialog.LemonHelloView;
import com.reazhao.utils.dialog.interfaces.LemonHelloEventDelegate;

/**
 * LemonHello 事件代理适配器
 * Created by LiuRi on 2017/1/11.
 */

public abstract class LemonHelloEventDelegateAdapter implements LemonHelloEventDelegate {

    @Override
    public void onActionDispatch(LemonHelloView helloView, LemonHelloInfo helloInfo, LemonHelloAction helloAction) {

    }

    @Override
    public void onMaskTouch(LemonHelloView helloView, LemonHelloInfo helloInfo) {

    }
    
}
