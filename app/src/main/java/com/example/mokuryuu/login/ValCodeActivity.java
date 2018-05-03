package com.example.mokuryuu.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import Models.LoginResponseObject;
import NetAsyncTask.BaseNetAsyncOnTaskListen;
import NetAsyncTask.BaseNetAsyncTask;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ValCodeActivity extends AppCompatActivity {
    @BindView(R.id.titleText)
    TextView titleText;
    @BindView(R.id.email_text)
    EditText emailText;
    @BindView(R.id.code_text)
    EditText codeText;
    @BindView(R.id.email_btn)
    Button emailBtn;
    @BindView(R.id.register_reset_btn)
    Button registerResetBtn;

    private int emailDuration = 0;
    private TimerTask task;
    private Timer timer;
    public  ActivityLoginRegisterResetpasswdActivity.RegisterOrReset type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_valcode);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        type = (ActivityLoginRegisterResetpasswdActivity.RegisterOrReset)intent.getSerializableExtra("type");

    }

    @OnClick({R.id.email_btn, R.id.register_reset_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.email_btn:
                getEmail();
                break;
            case R.id.register_reset_btn:
                checkEmail();
                break;
        }
    }

    final int EmailHandTag = 100;
    final Handler getEmailHandle = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case  EmailHandTag:
                    if (emailDuration == 0){
                        emailBtn.setEnabled(true);
                        emailBtn.setText("重新发送");
                        timer.cancel();
                    }else {
                        emailBtn.setEnabled(false);
                        emailBtn.setText(emailDuration + "秒后再次发送");
                    }
                    break;
            }
        }
    };

    private void getEmail(){
        if (emailText.getText().toString().length() == 0){
            Toast.makeText(this, "请输入邮箱", (int)2).show();
            return;
        }

        String url = "http://120.24.48.233:5011/Login/getValidCode/";
        HashMap params = new HashMap();
        params.put("email",emailText.getText().toString());
        BaseNetAsyncTask requestTask = new BaseNetAsyncTask(this, BaseNetAsyncTask.NetType.POST, url, params, new BaseNetAsyncOnTaskListen() {
            @Override
            public void onSuccess(Context context, Object result) {
                Toast.makeText(context, "发送成功", (int)2).show();
            }

            @Override
            public void onFailure(Context context, Object msg) {
                Toast.makeText(context, (String)msg, (int)2).show();
            }
        });
        requestTask.execute();

        task = new TimerTask() {
            @Override
            public void run() {
                Message refreshEmailBtnMsg = new Message();
                refreshEmailBtnMsg.what = EmailHandTag;
                getEmailHandle.sendMessage(refreshEmailBtnMsg);
                emailDuration = emailDuration == 10 ? 0 : emailDuration+1;
            }
        };

        timer = new Timer();
        timer.schedule(task, 1000, 1000);
    }

    private  void  checkEmail(){
        if (emailText.getText().toString().length() == 0){
            Toast.makeText(this, "请输入邮箱", (int)2).show();
            return;
        }

        if (codeText.getText().toString().length() == 0){
            Toast.makeText(this, "请输入验证码", (int)2).show();
            return;
        }

        HashMap params = new HashMap();
        params.put("email", emailText.getText().toString());
        params.put("code", codeText.getText().toString());
        String url = "http://120.24.48.233:5011/Login/checkValidCode";
        BaseNetAsyncTask task = new BaseNetAsyncTask(this, BaseNetAsyncTask.NetType.POST, url, params, new BaseNetAsyncOnTaskListen() {
            @Override
            public void onSuccess(Context context, Object result) {
                Toast.makeText(context, "验证码校验通过", (int)2).show();
                LoginResponseObject resultObject = JSON.parseObject(result.toString(), LoginResponseObject.class);
                Intent intent = new Intent(context, ActivityLoginRegisterResetpasswdActivity.class);
                intent.putExtra("type", type);
                intent.putExtra("email", emailText.getText().toString());
                intent.putExtra("accesstoken", resultObject.getAccesstoken());
                startActivity(intent);
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
