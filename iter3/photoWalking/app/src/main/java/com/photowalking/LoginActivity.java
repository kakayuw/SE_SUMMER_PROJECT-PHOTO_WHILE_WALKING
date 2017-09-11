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
import com.photowalking.utils.UserInfoSharedPreference;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    public boolean valid;
    private OkManager manager = new OkManager();
    private String uid;
    private String username;

    @Bind(R.id.input_username) EditText _usernameText;
    @Bind(R.id.input_password) EditText _passwordText;
    @Bind(R.id.btn_login) Button _loginButton;
    @Bind(R.id.link_find_pwd) TextView _findPwdLink;
    @Bind(R.id.tab_login_back) LinearLayout ll_back;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTransparent(this);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        
        _loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                if(imm.isActive())
                    imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(),0);
                login();
            }
        });

        _findPwdLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(LoginActivity.this, VerifyPhoneActivity.class);
                intent.putExtra("aim","findpwd");
                startActivityForResult(intent, 1);
                finish();
                overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
            }
        });

        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
                overridePendingTransition(R.anim.push_left_in,R.anim.push_right_out);
            }
        });
    }

    public void login() {

        if (!validate()) {
            _loginButton.setEnabled(true);
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("正在验证...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        username = _usernameText.getText().toString();
        String password = _passwordText.getText().toString();

        final User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        new Thread(new Runnable(){
            @Override
            public void run() {
                String result = sendHttpPost(UrlPath.loginUrl,user);
                if(result.charAt(0)=='u')
                    valid = true;
                else valid = false;
                if(valid){
                    uid = result.substring(1);
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(valid)
                            onLoginSuccess();
                        else
                            onLoginFailed();
                        progressDialog.dismiss();
                    }
                });
            }}).start();
    }

    public String sendHttpPost(String getUrl, User user) {
        Gson gson = new Gson();
        String jsonstr = gson.toJson(user);
        Log.d(TAG,jsonstr);
        String result = manager.sendStringByPost(getUrl, jsonstr);
        Log.d("GET FROM MANAGER",result);
        return result;
    }

    public void onLoginSuccess() {
        JPushInterface.setAlias(getApplicationContext(),1,uid);
        if(JPushInterface.isPushStopped(this))
            JPushInterface.resumePush(this);
        UserInfoSharedPreference.save(this, uid, username);
        Intent intent = new Intent(this, FragmentsActity.class);
        intent.putExtra("me",uid);
        intent.putExtra("uname",username);
        startActivityForResult(intent, 1);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "登录失败", Toast.LENGTH_LONG).show();
        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid_1 = true;
        String username = _usernameText.getText().toString();
        String password = _passwordText.getText().toString();

        if (username.isEmpty()) {
            _usernameText.setError("用户名不正确");
            valid_1 = false;
        } else {
            _usernameText.setError(null);
        }

        if (password.isEmpty() || password.length() < 5) {
            _passwordText.setError("密码位数必须大于6");
            valid_1 = false;
        } else {
            _passwordText.setError(null);
        }
        return valid_1;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
        overridePendingTransition(R.anim.push_left_in,R.anim.push_right_out);
    }
}
