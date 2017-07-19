package com.photowalking.friends;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.photowalking.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.photowalking.model.User;
import com.photowalking.utils.ProfileNetUtil;
import com.photowalking.utils.UrlPath;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by liujinxu on 17/7/6.
 */

public class ShowUserInfoActivity extends AppCompatActivity {
    String ip="http://192.168.1.165:8080/bzbp/rest/user/getPicture/";
    Bitmap bmp = null;
    @Bind(R.id.add)
    ImageButton _add;
    @Bind(R.id.delete)
    ImageButton _delete;
    @Bind(R.id.block)
    ImageButton _block;
    @Bind(R.id.message)
    ImageButton _message;
    @Bind(R.id.submit)
    Button _submit;
    @Bind(R.id.nickname)
    EditText _nickname;

    String delete_url="http://192.168.1.165:8080/bzbp/rest/friend/deleteFriend/";
    String nickname_url="/";
    String block_url="/";
    String TAG = "ShowUserInfoActivity";
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.friend_search_show);
        ButterKnife.bind(this);

        Log.d(TAG,"bind");
        Intent intent=getIntent();
        Gson gson = new Gson();
        String jsonstr = intent.getStringExtra("jsonstr");
        final String me = intent.getStringExtra("me");
        Log.d(TAG,"me");

        final User user = gson.fromJson(jsonstr, User.class);
        final ImageView photo = (ImageView) findViewById(R.id.photo_show);
        final TextView uid = (TextView) findViewById(R.id.uid_show);
        TextView username = (TextView) findViewById(R.id.username_show);
        TextView phone = (TextView) findViewById(R.id.phone_show);
        TextView email = (TextView) findViewById(R.id.email_show);

        photo.setImageURI(Uri.parse("/"));
        uid.setText(Integer.toString(user.getId()));
        username.setText(user.getUsername());
        phone.setText(user.getPhone());
        email.setText(user.getEmail());

        new Thread(new Runnable(){
            @Override
            public void run() {
                showPhoto(user, photo);
            }}).start();

        _add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new Thread(new Runnable(){
                    @Override
                    public void run() {
                    if(add(user,me, UrlPath.addFriUrl).equals("success")){
                       // Toast.makeText(getBaseContext(), "好友申请发送成功！", Toast.LENGTH_LONG).show();
                        _add.setEnabled(true);
                    }
                    else {
                       // Toast.makeText(getBaseContext(), "发送失败！", Toast.LENGTH_LONG).show();
                        _add.setEnabled(true);
                        finish();
                    }
                }}).start();

        }
        });

        _delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new Thread(new Runnable(){
                    @Override
                    public void run() {
                        if(delete(user,me,delete_url).equals("success")){
                            //Toast.makeText(getBaseContext(), "好友删除成功！", Toast.LENGTH_LONG).show();
                        }
                        else {
                           // Toast.makeText(getBaseContext(), "删除失败！", Toast.LENGTH_LONG).show();
                        }
                    }}).start();
            }
        });

        _block.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new Thread(new Runnable(){
                    @Override
                    public void run() {
                        if(block(user,me,block_url).equals("success")){
                            Toast.makeText(getBaseContext(), "添加成功！", Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(getBaseContext(), "添加失败！", Toast.LENGTH_LONG).show();
                        }
                    }}).start();
            }
        });

        _message.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
               // message();
            }
        });

        _submit.setOnClickListener(new View.OnClickListener() {
            String nickname = _nickname.getText().toString();
            @Override
            public void onClick(View v) {
                new Thread(new Runnable(){
                    @Override
                    public void run() {
                        if(modifyNickname(user,nickname,me,nickname_url).equals("success")){
                            Toast.makeText(getBaseContext(), "修改成功！", Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(getBaseContext(), "修改失败！", Toast.LENGTH_LONG).show();
                        }
                    }}).start();
            }
        });
    }

    public String add(User user,String me,String add_url){
        ProfileNetUtil profileNetUtil = new ProfileNetUtil();
        String res = profileNetUtil.sendFriendRequest(user,me,add_url);
        return res;
    }

    public String delete(User user,String me,String delete_url){
        ProfileNetUtil profileNetUtil = new ProfileNetUtil();
        String res = profileNetUtil.sendFriendRequest(user,me,delete_url);
        return res;
    }

    public String block(User user,String me,String block_url) {
        ProfileNetUtil profileNetUtil = new ProfileNetUtil();
        String res = profileNetUtil.sendFriendRequest(user,me,block_url);
        return res;
    }

    public String modifyNickname(User user,String nickname,String me,String nickname_url) {
        ProfileNetUtil profileNetUtil = new ProfileNetUtil();
        String res = profileNetUtil.sendNickname(user,nickname,me,nickname_url);
        return res;
    }

    public void showPhoto(User user, ImageView photo) {
        try {
            URL url = new URL(ip+user.getId());
            Log.v("userid: ", ip+user.getId());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            InputStream is = conn.getInputStream();
            bmp = BitmapFactory.decodeStream(is);
            is.close();
            photo.setImageBitmap(bmp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
