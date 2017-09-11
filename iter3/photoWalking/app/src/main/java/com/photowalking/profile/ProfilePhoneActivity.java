package com.photowalking.profile;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.Text;
import com.mob.MobSDK;
import com.photowalking.FragmentsActity;
import com.photowalking.R;

import com.photowalking.utils.ProfileNetUtil;
import com.photowalking.viewUtils.StatusBarUtil;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by liujinxu on 17/7/10.
 */

public class ProfilePhoneActivity extends Activity {

    @Bind(R.id.profile_phone_back)
    LinearLayout ll_back;
    @Bind(R.id.profile_phone_input_number)
    EditText phone;
    @Bind(R.id.profile_phone_get_code)
    LinearLayout getCode;
    @Bind(R.id.profile_phone_tv)
    TextView textView;
    @Bind(R.id.profile_phone_input_code)
    EditText inputCode;
    @Bind(R.id.profile_phone_submit)
    LinearLayout submit;

    private String msg = "failed";
    private String phonenum = "";
    private String me;

    private TimerTask tt;
    private Timer tm;
    private int time = 60;
    private static int repeat = 1;
    private boolean flag = true;

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
                    ProfileNetUtil profileNetUtil = new ProfileNetUtil();
                    msg = profileNetUtil.sendPhonePost(me,phonenum);
                    if(msg.equals("success")){
                        toast("修改成功");
                        finish();
                    }else{
                        toast("修改失败");
                    }
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
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        StatusBarUtil.setTransparent(this);
        setContentView(R.layout.profile_phone);
        ButterKnife.bind(this);
        me = getIntent().getStringExtra("me");

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
        submit.setEnabled(false);
    }

    public void enableBtn(){
        textView.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.primary));
        getCode.setEnabled(true);
        submit.setEnabled(true);
    }

    public void init(){
        MobSDK.init(this);
        SMSSDK.registerEventHandler(eh);
        disableBtn();

        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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

        submit.setOnClickListener(new View.OnClickListener() {
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
    public void finish() {
        super.finish();
        startActivityForResult(new Intent(this, FragmentsActity.class),1);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_right_out);
    }
}
