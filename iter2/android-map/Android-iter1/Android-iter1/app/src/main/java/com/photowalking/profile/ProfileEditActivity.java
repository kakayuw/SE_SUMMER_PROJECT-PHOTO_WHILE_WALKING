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
import com.photowalking.utils.UrlPath;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by liujinxu on 17/7/10.
 */

public class ProfileEditActivity extends AppCompatActivity {

    @Bind(R.id.edit_submit)
    Button _editButton;
    @Bind(R.id.username)
    EditText _username;
    @Bind(R.id.email)
    EditText _email;
//    @Bind(R.id.phone)
//    EditText _phone;
    User user = new User();
    ProfileNetUtil profileNetUtil = new ProfileNetUtil();

    boolean getUserFlag = true;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_edit);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        final String me = intent.getStringExtra("me");
        new Thread(new Runnable(){
            @Override
            public void run() {
                user = profileNetUtil.getUserByUid(UrlPath.getUserByUidUrl+me,me);
                getUserFlag = false;
            }}).start();
        while(getUserFlag);
        _username.setText(user.getUsername());
        _email.setText(user.getEmail());

        _editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable(){
                    @Override
                    public void run() {
                        if(edit_info(me) == "false"){
                            finish();
                        }
                        else {
                            finish();
                        }
                    }}).start();

                // Finish the registration screen and return to the Login activity

            }
        });
    }

    public String edit_info(String me){
        final String username = _username.getText().toString();
        final String email = _email.getText().toString();
  //      final String phone = _phone.getText().toString();
        User user = new User();
        user.setEmail(email);
        user.setUsername(username);
        user.setId(Integer.parseInt(me));
  //      user.setPhone(phone);
        String res = profileNetUtil.sendEditPost(user);
        return res;
    }
}
