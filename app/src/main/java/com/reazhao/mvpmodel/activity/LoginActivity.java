package com.reazhao.mvpmodel.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.reazhao.mvpmodel.R;
import com.reazhao.mvpmodel.presenter.LoginPresenter;
import com.reazhao.mvpmodel.view.ILoginView;
import com.reazhao.utils.LogUtils;
import com.reazhao.utils.OkHttpUtils;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends Activity implements ILoginView, View.OnClickListener {
    private LoginPresenter loginPresenter;
    private EditText etUser;
    private EditText etPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        loginPresenter = new LoginPresenter(this);
        etUser = (EditText) findViewById(R.id.et_user);
        etPassword = (EditText) findViewById(R.id.et_password);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
//                loginPresenter.onLogin(etUser.getText().toString(), etPassword.getText().toString());
//               LemonHello.getInformationHello("提示", "是否切换用户").addAction(new LemonHelloAction("确定", new LemonHelloActionDelegate() {
//                   @Override
//                   public void onClick(LemonHelloView helloView, LemonHelloInfo helloInfo, LemonHelloAction helloAction) {
//                       LogUtils.i(helloAction.getTitle());
//                       helloView.hide();
//                   }
//               })).addAction(new LemonHelloAction("取消", new LemonHelloActionDelegate() {
//                   @Override
//                   public void onClick(LemonHelloView helloView, LemonHelloInfo helloInfo, LemonHelloAction helloAction) {
//                       LogUtils.i(helloAction.getTitle());
//                       helloView.hide();
//                   }
//               })).show(this);
                Map<String ,String> map=new HashMap<>();
                String[] keys = { "key","apktype","ver" };
                String[] values = { "loGinApK4Es","1","" };
                map.put("key","loGinApK4Es");
                map.put("apktype","3");
                map.put("ver","");
                OkHttpUtils.doPostAsyn("http://dev.x9water.com/mgr/service/nfive/verApp.htm",map,new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        LogUtils.i(msg.obj.toString());
                    }
                });
                break;
            default:
                break;
        }
    }

    @Override
    public void onSucceed(boolean isSucceed) {
        if (isSucceed) {
            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onError() {
        Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEmpty(String empty) {
        if (empty.equals("user")) {
            Toast.makeText(LoginActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
        }
        if (empty.equals("password")) {
            Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        LogUtils.i("sssssssss",event.getKeyCode());
        if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_CENTER) {

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
