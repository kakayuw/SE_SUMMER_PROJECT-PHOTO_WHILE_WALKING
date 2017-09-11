package com.photowalking.profile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.photowalking.FragmentsActity;
import com.photowalking.R;

import com.photowalking.model.User;
import com.photowalking.utils.ProfileNetUtil;
import com.photowalking.viewUtils.StatusBarUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by liujinxu on 17/7/10.
 */

public class ProfilePwdActivity extends Activity {

    @Bind(R.id.profile_pwd_back)
    LinearLayout ll_back;
    @Bind(R.id.change_submit)
    LinearLayout _changeButton;
    @Bind(R.id.pwd_old)
    EditText _old;
    @Bind(R.id.pwd_new)
    EditText _new;
    @Bind(R.id.pwd_sure)
    EditText _sure;

    private String msg;
    private boolean flag;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        StatusBarUtil.setTransparent(this);
        setContentView(R.layout.profile_pwd);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        final String me = intent.getStringExtra("me");

        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        _changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable(){
                    @Override
                    public void run() {
                        final String oldpwd = _old.getText().toString();
                        final String newpwd = _new.getText().toString();
                        final String sure = _sure.getText().toString();
                        if(TextUtils.isEmpty(oldpwd) || TextUtils.isEmpty(newpwd)
                                || TextUtils.isEmpty(sure)){
                            toast("请填写所有所需密码");
                            return;
                        }
                        if(newpwd.length() < 5){
                            toast("密码长度必须大于6");
                            return;
                        }
                        if(!newpwd.equals(sure)){
                            toast("两次输入的新密码不一致");
                            return;
                        }
                        ProfileNetUtil profileNetUtil = new ProfileNetUtil();
                        msg = profileNetUtil.sendPwdPost(me,oldpwd,newpwd);
                        if(msg.equals("success")){
                            toast("更改成功");
                            finish();
                        }
                        else {
                            toast("更改失败");
                        }
                    }}).start();


            }
        });
    }

    private void toast(final String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        startActivityForResult(new Intent(this, FragmentsActity.class),1);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_right_out);
    }
}
