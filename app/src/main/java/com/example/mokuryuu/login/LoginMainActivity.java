package com.example.mokuryuu.login;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import NetAsyncTask.BaseNetAsyncOnTaskListen;
import NetAsyncTask.BaseNetAsyncTask;

import java.util.HashMap;

import Tool.SPTool;
import Tool.StringTool;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginMainActivity extends AppCompatActivity implements View.OnClickListener {


    @BindView(R.id.register_text)
    TextView registerText;
    @BindView(R.id.reserpasswd_text)
    TextView reserpasswdText;

    private View contentView;
    private TextView emailText;
    private TextView pwdText;
    private Button loginBtn;

    private String email;
    private String pwd;

    private static final String TAG = "LoginMainActivity";
//    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_main);
        ButterKnife.bind(this);
        contentView = findViewById(R.id.content_view);
        emailText = findViewById(R.id.email_text);
        pwdText = findViewById(R.id.pwd_text);
        loginBtn = findViewById(R.id.login_btn);
        contentView.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.content_view:
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(contentView.getWindowToken(), 0);
                break;
            case R.id.login_btn:
                loginBtnClick(loginBtn);
                break;
        }
    }

    private void loginBtnClick(Button btn) {
        email = emailText.getText().toString();
        pwd = pwdText.getText().toString();
        HashMap param = new HashMap();
        param.put("email", email);
        param.put("passwd", StringTool.md5(pwd));
        String url = "http://120.24.48.233:5011/Login/loginChecked";
        BaseNetAsyncTask task = new BaseNetAsyncTask(this, BaseNetAsyncTask.NetType.POST, url, param, new BaseNetAsyncOnTaskListen(){
            @Override
            public void onSuccess(Context context, Object result) {
                Intent intent = new Intent(context,  HomeRootActivity.class);
                HashMap resultMap = com.alibaba.fastjson.JSON.parseObject(result.toString(),HashMap.class);
                if (resultMap.containsKey("userId")) {
                    int userId = (int)resultMap.get("userId");
                    SPTool.put(context, "userId", userId);
                    intent.putExtra("userId", userId);
                }
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Context context, Object msg) {
                Log.e("test", msg.toString());
            }
        });
        task.execute();
    }




    @OnClick({R.id.register_text, R.id.reserpasswd_text})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.register_text:
                Intent registerIntenet = new Intent(this, ValCodeActivity.class);
                registerIntenet.putExtra("type", ActivityLoginRegisterResetpasswdActivity.RegisterOrReset.REGISTER);
                startActivity(registerIntenet);
                break;
            case R.id.reserpasswd_text:
                Intent resetpwdIntenet = new Intent(this, ValCodeActivity.class);
                resetpwdIntenet.putExtra("type", ActivityLoginRegisterResetpasswdActivity.RegisterOrReset.RESETPW);
                startActivity(resetpwdIntenet);
                break;
        }
    }


}
