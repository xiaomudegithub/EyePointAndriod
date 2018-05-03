package com.example.mokuryuu.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import NetAsyncTask.BaseNetAsyncOnTaskListen;
import NetAsyncTask.BaseNetAsyncTask;
import Tool.StringTool;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ActivityLoginRegisterResetpasswdActivity extends Activity {



    public enum RegisterOrReset {
        REGISTER,
        RESETPW,
    }

    @BindView(R.id.titleText)
    TextView titleText;
    @BindView(R.id.register_reset_btn)
    Button registerResetBtn;
    @BindView(R.id.repasswd_text)
    EditText repasswdText;
    @BindView(R.id.passwd_text)
    EditText passwdText;

    public RegisterOrReset type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_login_reset_register);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        this.setType((RegisterOrReset) intent.getSerializableExtra("type"));
    }

    public void setType(RegisterOrReset type) {
        this.type = type;
        String text = type == RegisterOrReset.REGISTER ? "注册" : "重置密码";
        titleText.setText(text);
        registerResetBtn.setText(text);
    }

    @OnClick(R.id.register_reset_btn)
    public void onViewClicked() {
        if (passwdText.getText().length() == 0){
            Toast.makeText(this, "请输入密码", (int)2).show();
            return;
        }

        if (repasswdText.getText().length() == 0){
            Toast.makeText(this, "请输入确认密码", (int)2).show();
            return;
        }

        if (!passwdText.getText().toString().equals(repasswdText.getText().toString())){
            Toast.makeText(this, "两次输入密码不一样", (int)2).show();
            return;
        }
        registerOrResetPW();
    }

    private void registerOrResetPW() {

        Intent intent = getIntent();
        String url = "http://120.24.48.233:5011/Login/registerOrUpdatepwd";
        HashMap params = new HashMap();
        params.put("email", intent.getStringExtra("email"));
        params.put("passwd", StringTool.md5(passwdText.getText().toString()));
        params.put("accesstoken", intent.getStringExtra("accesstoken"));
        BaseNetAsyncTask task = new BaseNetAsyncTask(this, BaseNetAsyncTask.NetType.POST, url, params, new BaseNetAsyncOnTaskListen() {
            @Override
            public void onSuccess(Context context, Object result) {
                String text = type == RegisterOrReset.REGISTER ? "注册成功" : "重置密码成功";
                Toast.makeText(context, text, (int)2).show();
                finish();
            }

            @Override
            public void onFailure(Context context, Object msg) {
                Toast.makeText(context, (String)msg, (int)2).show();
            }
        });
        task.execute();
    }


}
