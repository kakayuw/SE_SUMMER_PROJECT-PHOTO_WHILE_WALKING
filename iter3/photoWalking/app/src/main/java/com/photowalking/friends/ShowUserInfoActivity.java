package com.photowalking.friends;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.photowalking.R;

import com.photowalking.adapter.ImageHolder;
import com.photowalking.adapter.SetImageTask;
import com.photowalking.fragment.FriendFragment;
import com.photowalking.model.User;
import com.photowalking.utils.ProfileNetUtil;
import com.photowalking.utils.UrlPath;
import com.photowalking.viewUtils.StatusBarUtil;
import com.photowalking.utils.UserInfoSharedPreference;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by liujinxu on 17/7/6.
 */

public class ShowUserInfoActivity extends Activity {

    @Bind(R.id.tab_fri_show_img)
    ImageView img;
    @Bind(R.id.tab_fri_show_back)
    LinearLayout ll_back;
    @Bind(R.id.tab_fri_show_more)
    TextView tv_more;
    @Bind(R.id.tab_fri_show_uid)
    TextView tv_uid;
    @Bind(R.id.tab_fri_show_name)
    TextView tv_name;
    @Bind(R.id.tab_fri_show_email)
    TextView tv_email;
    @Bind(R.id.tab_fri_show_add)
    LinearLayout add;

    @Bind(R.id.tab_fri_show_divider)
    LinearLayout ll_divider;
    @Bind(R.id.tab_fri_show_ll_remrk)
    LinearLayout ll_remark;

    static TextView tv_remark;

    @Bind(R.id.tab_fri_show_fri)
    LinearLayout fri;
    @Bind(R.id.tab_fri_show_delete)
    LinearLayout delete;
    @Bind(R.id.tab_fri_show_chat)
    LinearLayout chat;

    private String me;
    private String uname;
    private User user;
    private String msg;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        StatusBarUtil.setTransparent(this);
        setContentView(R.layout.friend_show);
        ButterKnife.bind(this);
        tv_remark = (TextView)findViewById(R.id.tab_fri_show_remark);

        Intent intent = getIntent();
        Gson gson = new Gson();
        me = UserInfoSharedPreference.getUid(this);
        uname = UserInfoSharedPreference.getUname(this);
        String jsonstr = intent.getStringExtra("jsonstr");
        user = gson.fromJson(jsonstr, User.class);

        checkfri();

        new SetImageTask().execute(new ImageHolder(UrlPath.getPicUrl+user.getId(), img));

        tv_uid.setText(Integer.toString(user.getId()));
        tv_name.setText(user.getUsername());
        tv_email.setText(user.getEmail());

        tv_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ShowUserInfoActivity.this, "under building", Toast.LENGTH_SHORT).show();
            }
        });

        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowUserInfoActivity.this, ApplyFriendActivity.class);
                intent.putExtra("me", me);
                intent.putExtra("uname",uname);
                intent.putExtra("fid", Integer.toString(user.getId()));
                intent.putExtra("remark",user.getUsername());
                startActivityForResult(intent, 1);
                finish();
            }
        });

        ll_remark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowUserInfoActivity.this, ChangeRemarkActivity.class);
                intent.putExtra("me", me);
                intent.putExtra("fid", Integer.toString(user.getId()));
                intent.putExtra("remark",getIntent().getStringExtra("remark"));
                startActivityForResult(intent, 1);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (delete(Integer.toString(user.getId()), me).equals("success")) {
                            msg = "删除成功！";
                            int pos = FriendFragment.getAdapter().getPosition(user.getId());
                            if(pos!=-1){
                                FriendFragment.getAdapter().deleteItem(pos);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        FriendFragment.getAdapter().notifyDataSetChanged();
                                    }
                                });
                            }
                        } else
                            msg = "删除失败！";
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
                            }
                        });
                        finish();
                    }
                }).start();
            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"under building",Toast.LENGTH_SHORT).show();
                //TODO START ACTIVITY CHAT WITH FRIEND
//                Intent intent = new Intent(ShowUserInfoActivity.this,ChatActivity.class);
//                startActivity(intent);
//                finish();
            }
        });

    }

    public void checkfri(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                ProfileNetUtil profileNetUtil = new ProfileNetUtil();
                String flag = profileNetUtil.sendFriendRequest(
                        Integer.toString(user.getId()),me,UrlPath.checkFriUrl);
                if (flag.equals("true")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            add.setVisibility(View.GONE);
                            fri.setVisibility(View.VISIBLE);
                            ll_divider.setVisibility(View.VISIBLE);
                            ll_remark.setVisibility(View.VISIBLE);
                            tv_remark.setText(getIntent().getStringExtra("remark"));
                        }
                    });
                }
            }
        }).start();
    }

    public String delete(String fid, String me){
        ProfileNetUtil profileNetUtil = new ProfileNetUtil();
        String res = profileNetUtil.sendFriendRequest(fid,me,UrlPath.deleteFriUrl);
        return res;
    }

    public static void chgRemark(String remark){
        tv_remark.setText(remark);
    }
}
