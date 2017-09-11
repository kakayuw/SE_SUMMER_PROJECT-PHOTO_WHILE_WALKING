package com.photowalking;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mob.MobSDK;
import com.photowalking.model.User;
import com.photowalking.utils.OkManager;
import com.photowalking.utils.ProfileNetUtil;
import com.photowalking.utils.UrlPath;
import com.photowalking.viewUtils.StatusBarUtil;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class VerifyPhoneActivity extends AppCompatActivity {
    private OkManager manager;
    private boolean valid;

    @Bind(R.id.phone_input_number) EditText phone;
    @Bind(R.id.phone_get_code) LinearLayout getCode;
    @Bind(R.id.phone_tv) TextView textView;
    @Bind(R.id.phone_input_code) EditText inputCode;
    @Bind(R.id.phone_confirm) Button btn_confirm;
    @Bind(R.id.phone_back) LinearLayout ll_back;

    private String phonenum = "";

    private TimerTask tt;
    private Timer tm;
    private int time = 60;
    private static int repeat = 1;
    private boolean flag = true;
    private String aim;

    Handler hd =  new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == repeat) {
                flag = true;
                getCode.setEnabled(true);
                tm.cancel();
                tt.cancel();
                time = 60;//时间重置
                textView.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.primary));
                textView.setText("重新发送");
            }else {
                flag = false;
                getCode.setEnabled(false);
                textView.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.base));
                textView.setText("重新发送("+time+"s)");
            }
        }
    };

    EventHandler eh=new EventHandler(){
        @Override
        public void afterEvent(int event, int result, Object data) {
            if (result == SMSSDK.RESULT_COMPLETE) {
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    Intent intent = null;
                    if(aim.equals("signup")){
                        intent = new Intent(VerifyPhoneActivity.this, SignupActivity.class);
                    }else{
                        intent = new Intent(VerifyPhoneActivity.this, FindPwdActivity.class);
                    }
                    intent.putExtra("phone",phonenum);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.push_right_in,R.anim.push_left_out);
                }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                    toast("发送成功");
                }
            }else{//错误等在这里（包括验证失败）
                ((Throwable)data).printStackTrace();
                String str = ((Throwable) data).getMessage();
                toast(str);
            }
        }
    };

    private void toast(final String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTransparent(this);
        setContentView(R.layout.activity_phone);
        ButterKnife.bind(this);

        aim = getIntent().getStringExtra("aim");

        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VerifyPhoneActivity.this, MainActivity.class));
                finish();
                overridePendingTransition(R.anim.push_left_in,R.anim.push_right_out);
            }
        });

        if (Build.VERSION.SDK_INT >= 23) {
            int readPhone = checkSelfPermission(Manifest.permission.READ_PHONE_STATE);
            int receiveSms = checkSelfPermission(Manifest.permission.RECEIVE_SMS);
            int readSms = checkSelfPermission(Manifest.permission.READ_SMS);
            int readSdcard = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);

            int requestCode = 0;
            ArrayList<String> permissions = new ArrayList<>();
            if (readPhone != PackageManager.PERMISSION_GRANTED) {
                requestCode |= 1 << 0;
                permissions.add(Manifest.permission.READ_PHONE_STATE);
            }
            if (receiveSms != PackageManager.PERMISSION_GRANTED) {
                requestCode |= 1 << 1;
                permissions.add(Manifest.permission.RECEIVE_SMS);
            }
            if (readSms != PackageManager.PERMISSION_GRANTED) {
                requestCode |= 1 << 2;
                permissions.add(Manifest.permission.READ_SMS);
            }
            if (readSdcard != PackageManager.PERMISSION_GRANTED) {
                requestCode |= 1 << 3;
                permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
            if (requestCode > 0) {
                String[] permission = new String[permissions.size()];
                this.requestPermissions(permissions.toArray(permission), requestCode);
                return;
            }
        }
        init();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        init();
    }

    public void disableBtn(){
        textView.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.base));
        getCode.setEnabled(false);
        btn_confirm.setEnabled(false);
    }

    public void enableBtn(){
        textView.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.primary));
        getCode.setEnabled(true);
        btn_confirm.setEnabled(true);
    }

    public void init(){
        MobSDK.init(this);
        SMSSDK.registerEventHandler(eh);
        disableBtn();

        phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str= phone.getText().toString();
                if(!TextUtils.isEmpty(str) && flag)
                    enableBtn();
                else
                    disableBtn();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        getCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phonenum = phone.getText().toString();
                if(TextUtils.isEmpty(phonenum) || phonenum.equals("") ||phonenum.length()<11){
                    toast("号码填写错误");
                }else{
                    SMSSDK.getVerificationCode("86", phonenum);
                    tm = new Timer();
                    tt = new TimerTask() {
                        @Override
                        public void run() {
                            hd.sendEmptyMessage(time--);
                        }
                    };
                    tm.schedule(tt,0,1000);
                }
            }
        });

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = inputCode.getText().toString();
                if(!TextUtils.isEmpty(code)){
                    SMSSDK.submitVerificationCode("86",phonenum,code);
                }else{
                    toast("请输入验证码");
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eh);
    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(VerifyPhoneActivity.this, MainActivity.class));
        finish();
        overridePendingTransition(R.anim.push_left_in,R.anim.push_right_out);
    }
}