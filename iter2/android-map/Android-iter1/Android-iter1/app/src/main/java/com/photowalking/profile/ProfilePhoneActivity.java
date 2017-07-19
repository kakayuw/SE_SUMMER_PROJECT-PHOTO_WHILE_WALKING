package com.photowalking.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.photowalking.R;

import com.photowalking.utils.ProfileNetUtil;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by liujinxu on 17/7/10.
 */

public class ProfilePhoneActivity extends AppCompatActivity {

    @Bind(R.id.send_code)
    Button _sendButton;
    @Bind(R.id.phone)
    EditText _phone;
    @Bind(R.id.code)
    EditText _code;
    @Bind(R.id.submit_code)
    Button _submitButton;

    private String res = "failed";
    private String phone_final = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_phone);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        final String me = intent.getStringExtra("me");

        _sendButton.setOnClickListener(new View.OnClickListener() {
            String phone = _phone.getText().toString();
            @Override
            public void onClick(View v) {
                new Thread(new Runnable(){
                    @Override
                    public void run() {
                        sendPhone(phone);
                        phone_final = phone;
                    }}).start();
                // Finish the registration screen and return to the Login activity
            }
        });

        _submitButton.setOnClickListener(new View.OnClickListener() {
            String code = _code.getText().toString();
            @Override
            public void onClick(View v) {
                new Thread(new Runnable(){
                    @Override
                    public void run() {
                        //res = sendCode(code,me,phone_final);
                        res = sendCode(me,phone_final);
                    }}).start();
                // Finish the registration screen and return to the Login activity
            }
        });
    }

    public void sendPhone(String phone) {
        ProfileNetUtil profileNetUtil = new ProfileNetUtil();
        profileNetUtil.sendPhonePost(phone);
    }

    public String sendCode(String me,String phone){
        return "";
    }
}
