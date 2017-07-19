package com.photowalking.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.photowalking.R;

import com.photowalking.model.User;
import com.photowalking.utils.ProfileNetUtil;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by liujinxu on 17/7/10.
 */

public class ProfilePwdActivity extends AppCompatActivity {

    @Bind(R.id.change_submit)
    Button _changeButton;
    @Bind(R.id.pwd_old)
    EditText _old;
    @Bind(R.id.pwd_new)
    EditText _new;
    @Bind(R.id.pwd_sure)
    EditText _sure;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_pwd);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        final String me = intent.getStringExtra("me");

        _changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable(){
                    @Override
                    public void run() {
                        if(change_pwd(me) == "failed"){
                            finish();
                        }
                        else {

                        }
                    }}).start();

                // Finish the registration screen and return to the Login activity

            }
        });
    }

    public String change_pwd(String me) {
        final String oldpwd = _old.getText().toString();
        final String newpwd = _new.getText().toString();
        final String sure = _sure.getText().toString();
        User user = new User();
        if(!newpwd.equals(sure)){return "failed";}
        user.setPassword(oldpwd);
        user.setId(Integer.parseInt(me));
        ProfileNetUtil profileNetUtil = new ProfileNetUtil();
        String res = profileNetUtil.sendPwdPost(user,newpwd);
        return res;
    }
}
