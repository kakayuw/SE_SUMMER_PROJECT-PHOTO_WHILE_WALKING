package com.photowalking;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import com.photowalking.model.User;
import com.photowalking.utils.OkManager;
import com.photowalking.utils.UrlPath;
import com.photowalking.viewUtils.StatusBarUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";
    public static User newuser;
    private OkManager manager;
    private boolean valid;

    private String mobile;

    @Bind(R.id.input_name) EditText _nameText;
    @Bind(R.id.input_email) EditText _emailText;
    @Bind(R.id.input_password) EditText _passwordText;
    @Bind(R.id.input_reEnterPassword) EditText _reEnterPasswordText;
    @Bind(R.id.btn_signup) Button _signupButton;
    @Bind(R.id.tab_signup_back) LinearLayout ll_back;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTransparent(this);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        mobile = getIntent().getStringExtra("phone");

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                if(imm.isActive())
                    imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(),0);
                signup();
            }
        });

        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, VerifyPhoneActivity.class));
                finish();
                overridePendingTransition(R.anim.push_left_in,R.anim.push_right_out);
            }
        });
    }

    public void signup() {
        Log.d(TAG, "Sign up");

        if (!validate()) {
            _signupButton.setEnabled(true);
            return;
        }

        _signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("正在创建账户...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        newuser = new User();
        newuser.setUsername(name);
        newuser.setEmail(email);
        newuser.setPhone(mobile);
        newuser.setPassword(password);

        new Thread(new Runnable(){
            @Override
            public void run() {
                String result = sendHttpPost(UrlPath.signupUrl, newuser);
                valid = result.equals("success");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(valid)
                            onSignupSuccess();
                        else
                            onSignupFailed();
                        progressDialog.dismiss();
                    }
                });
            }}).start();
    }

    public String sendHttpPost(String getUrl, User user) {
        Gson gson = new Gson();
        String jsonstr = gson.toJson(user);
        Log.d(TAG,jsonstr);
        manager = new OkManager();
        String result = manager.sendStringByPost(getUrl, jsonstr);
        Log.d("GET FROM MANAGER",result);
        return result;

    }

    public void onSignupSuccess() {
        _signupButton.setEnabled(false);
        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "注册失败", Toast.LENGTH_LONG).show();
        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        String reEnterPassword = _reEnterPasswordText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("至少要有三个字符");
            valid = false;
        } else {
            _nameText.setError(null);
        }


        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("邮箱地址不正确");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 5) {
            _passwordText.setError("密码位数必须大于6");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 5 || !(reEnterPassword.equals(password))) {
            _reEnterPasswordText.setError("两次密码不一致");
            valid = false;
        } else {
            _reEnterPasswordText.setError(null);
        }

        return valid;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(SignupActivity.this, VerifyPhoneActivity.class));
        finish();
        overridePendingTransition(R.anim.push_left_in,R.anim.push_right_out);
    }
}