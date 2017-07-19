package com.photowalking.friends;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.gson.Gson;
import com.photowalking.R;

import com.photowalking.model.User;
import com.photowalking.utils.ProfileNetUtil;
import com.photowalking.utils.UrlPath;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by liujinxu on 17/7/6.
 */



public class AddFriendActivity extends AppCompatActivity {

    @Bind(R.id.tab_fri_add_uid)
    EditText _add_friend;
    @Bind(R.id.search_by_name)
    ImageButton _searchByName;
    @Bind(R.id.search_by_uid)
    ImageButton _searchByUid;
    @Bind(R.id.tab_fri_add_uname)
    EditText _uid;

    User user = new User();
    boolean flag = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.friend_add);
        Intent intent=getIntent();
        Gson gson = new Gson();
        final String me = intent.getStringExtra("me");

        ButterKnife.bind(this);

        _searchByName.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String username = _add_friend.getText().toString();
                searchByName(username);
                while(flag == false);
                if(user == null)
                {Toast.makeText(getBaseContext(), "Search failed", Toast.LENGTH_LONG).show();}
                else{
                    Gson gson = new Gson();
                    String jsonstr = gson.toJson(user);
                    Intent intent = new Intent(AddFriendActivity.this,ShowUserInfoActivity.class);
                    intent.putExtra("jsonstr",jsonstr);
                    intent.putExtra("me",me);
                    startActivityForResult(intent, 1);
                }

            }
        });

        _searchByUid.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String uid = _uid.getText().toString();
                searchByUid(uid);
                while(flag == false);
                if(user == null)
                { Toast.makeText(getBaseContext(), "Search failed", Toast.LENGTH_LONG).show();}
                else {
                    Gson gson = new Gson();
                    String jsonstr = gson.toJson(user);
                    Intent intent = new Intent(AddFriendActivity.this,ShowUserInfoActivity.class);
                    intent.putExtra("jsonstr",jsonstr);
                    intent.putExtra("me",me);
                    startActivityForResult(intent, 1);
                }

            }
        });

    }

    public void searchByName(final String username) {
        new Thread(new Runnable(){
            @Override
            public void run() {
                sendHttpPostByName(UrlPath.getUserByNameUrl+username,username);
            }}).start();
    }

    public void searchByUid(final String uid) {
        new Thread(new Runnable(){
            @Override
            public void run() {
                sendHttpPostByUid(UrlPath.getUserByUidUrl+uid,uid);
            }}).start();
    }

    public void sendHttpPostByName(String url,String username) {
        ProfileNetUtil profileNetUtil = new ProfileNetUtil();
        user = profileNetUtil.getUserByName(url,username);
        flag = true;
    }

    public void sendHttpPostByUid(String url,String uid) {
        ProfileNetUtil profileNetUtil = new ProfileNetUtil();
        user = profileNetUtil.getUserByUid(url,uid);
        flag = true;
    }

}
