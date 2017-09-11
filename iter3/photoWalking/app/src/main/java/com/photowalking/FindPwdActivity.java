package com.photowalking;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

public class FindPwdActivity extends AppCompatActivity {

    @Bind(R.id.phone_input_number) EditText phone;
    @Bind(R.id.phone_get_code) LinearLayout getCode;
    @Bind(R.id.phone_input_code) EditText inputPwd;
    @Bind(R.id.findpwd_reEnterPassword) EditText renterPwd;
    @Bind(R.id.findpwd_confirm) Button btn_confirm;
    @Bind(R.id.findpwd_back) LinearLayout ll_back;

    private String phonenum = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTransparent(this);
        setContentView(R.layout.activity_findpwd);
        ButterKnife.bind(this);

        phonenum = getIntent().getStringExtra("phone");

        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FindPwdActivity.this, VerifyPhoneActivity.class));
                finish();
                overridePendingTransition(R.anim.push_left_in,R.anim.push_right_out);
            }
        });

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                if(imm.isActive())
                    imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(),0);
                resetPwd();
            }
        });
    }

    public void resetPwd() {

        if (!validate()) {
            return;
        }

        final ProgressDialog progressDialog = new ProgressDialog(FindPwdActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("正在重置...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        String password = inputPwd.getText().toString();

        new Thread(new Runnable(){
            @Override
            public void run() {
                String result = "failed";
                final boolean valid = result.equals("success");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(valid){
                            Intent intent = new Intent(FindPwdActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else{
                            Toast.makeText(getBaseContext(), "重置密码失败", Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });
            }}).start();
    }

    public boolean validate() {
        boolean valid = true;

        String password = inputPwd.getText().toString();
        String reEnterPassword = renterPwd.getText().toString();

        if (password.isEmpty() || password.length() < 5 ) {
            inputPwd.setError("密码位数必须大于6");
            valid = false;
        } else {
            inputPwd.setError(null);
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 10 || !(reEnterPassword.equals(password))) {
            renterPwd.setError("两次密码不一致");
            valid = false;
        } else {
            renterPwd.setError(null);
        }

        return valid;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(FindPwdActivity.this, VerifyPhoneActivity.class));
        finish();
        overridePendingTransition(R.anim.push_left_in,R.anim.push_right_out);
    }
}