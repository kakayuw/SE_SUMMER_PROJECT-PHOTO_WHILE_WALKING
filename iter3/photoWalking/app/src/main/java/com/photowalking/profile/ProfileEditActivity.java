package com.photowalking.profile;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.photowalking.FragmentsActity;
import com.photowalking.R;

import com.photowalking.model.User;
import com.photowalking.utils.OkManager;
import com.photowalking.utils.ProfileNetUtil;
import com.photowalking.utils.UrlPath;
import com.photowalking.viewUtils.StatusBarUtil;

import java.io.IOException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by liujinxu on 17/7/10.
 */

public class ProfileEditActivity extends Activity {

    @Bind(R.id.profile_edit_back)
    LinearLayout ll_back;
    @Bind(R.id.profile_edit_ll)
    LinearLayout ll;
    @Bind(R.id.edit_submit)
    LinearLayout _editButton;
    @Bind(R.id.username)
    EditText _username;
    @Bind(R.id.email)
    EditText _email;
    @Bind(R.id.profile_edit_err)
    LinearLayout err;
    @Bind(R.id.profile_edit_btn)
    Button btn;
    @Bind(R.id.profile_edit_pb)
    ProgressBar loading;

    private String msg;
    private String me;

    ProfileNetUtil profileNetUtil = new ProfileNetUtil();
    private Handler handler = new Handler();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        StatusBarUtil.setTransparent(this);
        setContentView(R.layout.profile_edit);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        me = intent.getStringExtra("me");

        new GetInfoTask().execute();

        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        _editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable(){
                    @Override
                    public void run() {
                        final String username = _username.getText().toString();
                        final String email = _email.getText().toString();
                        if(TextUtils.isEmpty(username) || TextUtils.isEmpty(email)){
                            toast("用户名/邮箱不能为空");
                            return;
                        }
                        if(!OkManager.checkNetwork(getApplicationContext())) {
                            toast("未连接到网络,无法提交");
                            return;
                        }else {
                            msg = profileNetUtil.sendEditPost(me,username,email);
                        }
                        if(msg.equals("success")){
                            toast("修改成功");
                            finish();
                        }
                        else {
                            toast("修改失败");
                        }
                    }}).start();

            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GetInfoTask().execute();
            }
        });
    }

    private void toast(final String message){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), message ,Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showLoad(){
        err.setVisibility(View.INVISIBLE);
        ll.setVisibility(View.INVISIBLE);
        loading.setVisibility(View.VISIBLE);
    }

    private class GetInfoTask extends AsyncTask<Void, Void, User> {

        @Override
        protected void onPreExecute() {
            showLoad();
        }

        @Override
        protected User doInBackground(Void... params) {
            if(!OkManager.checkNetwork(getApplicationContext())){
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),"未连接到网络",Toast.LENGTH_SHORT).show();
                    }
                });
                return null;
            }
            return profileNetUtil.getUserByUid(UrlPath.getUserByUidUrl + me, me);
        }

        @Override
        protected void onPostExecute(User user) {
            loading.setVisibility(View.INVISIBLE);
            if(user==null){
                err.setVisibility(View.VISIBLE);
                ll.setVisibility(View.INVISIBLE);
                return;
            }
            err.setVisibility(View.INVISIBLE);
            ll.setVisibility(View.VISIBLE);
            _username.setText(user.getUsername());
            _email.setText(user.getEmail());
        }
    }

    @Override
    public void finish() {
        super.finish();
        startActivityForResult(new Intent(this, FragmentsActity.class),1);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_right_out);
    }

}
