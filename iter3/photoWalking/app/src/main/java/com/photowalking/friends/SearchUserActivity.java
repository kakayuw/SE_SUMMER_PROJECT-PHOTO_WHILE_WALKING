package com.photowalking.friends;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.photowalking.R;

import com.photowalking.adapter.FriSearchAdapter;
import com.photowalking.adapter.FriendAdapter;
import com.photowalking.model.Friend;
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
 * Created by liujinxu on 17/7/6.
 */



public class SearchUserActivity extends Activity {

    @Bind(R.id.tab_fri_back)
    LinearLayout back;
    @Bind(R.id.tab_fri_search_idname)
    EditText uid_name;
    @Bind(R.id.tab_fri_search_error)
    TextView errormsg;
    @Bind(R.id.tab_fri_search_result)
    ListView result;
    @Bind(R.id.tab_fri_search_loading)
    ProgressBar loading;


    private Handler handler;
    private FriSearchAdapter adapter;
    private int uid;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        StatusBarUtil.setTransparent(this);
        setContentView(R.layout.friend_search);

        uid = Integer.parseInt(getIntent().getStringExtra("me"));

        ButterKnife.bind(this);

        handler = new Handler();

        result.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final User usr = adapter.getItem(position);
                Gson gson = new Gson();
                String jsonstr = gson.toJson(usr);
                final Intent intent = new Intent(SearchUserActivity.this,ShowUserInfoActivity.class);
                intent.putExtra("jsonstr",jsonstr);
                startActivityForResult(intent, 1);
            }
        });

        uid_name.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_SEARCH){
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    if(imm.isActive())
                        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(),0);
                    String idname = uid_name.getText().toString();
                    if(idname=="" || idname.length()==0){
                        Toast.makeText(getApplicationContext(),"用户名/ID不能为空",Toast.LENGTH_SHORT).show();
                    }else{
                        new SearchUserTask().execute(idname);
                    }
                }
                return true;
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void showResult(){
        errormsg.setVisibility(View.INVISIBLE);
        result.setVisibility(View.VISIBLE);
    }

    private void showError(){
        errormsg.setVisibility(View.VISIBLE);
        result.setVisibility(View.INVISIBLE);
    }

    private void showLoad(){
        errormsg.setVisibility(View.INVISIBLE);
        result.setVisibility(View.INVISIBLE);
        loading.setVisibility(View.VISIBLE);
    }

    private class SearchUserTask extends AsyncTask<String, Void, List<User>>{

        @Override
        protected void onPreExecute() {
            showLoad();
        }

        @Override
        protected List<User> doInBackground(String... params) {
            if(params.length==0)
                return null;
            if(!OkManager.checkNetwork(getApplicationContext())){
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),"未连接到网络",Toast.LENGTH_SHORT).show();
                    }
                });
                return null;
            }
            OkManager<User> okManager = new OkManager();
            List<User> users = okManager.getAll(UrlPath.searchUserUrl+params[0], User.class);
            return users;
        }

        @Override
        protected void onPostExecute(List<User> users) {
            loading.setVisibility(View.INVISIBLE);
            if(users==null){
                showError();
                return;
            }
            showResult();
            if(adapter==null){
                try {
                    adapter = new FriSearchAdapter(getApplicationContext(),UrlPath.getPicUrl);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                result.setAdapter(adapter);
            }else{
                adapter.clear();
            }
            for(User user: users){
                if(user.getId() != uid)
                    adapter.addItem(user);
            }
            adapter.notifyDataSetChanged();
        }
    }

}
