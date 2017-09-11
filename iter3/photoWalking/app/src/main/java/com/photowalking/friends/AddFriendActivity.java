package com.photowalking.friends;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.photowalking.R;
import com.photowalking.adapter.ImageHolder;
import com.photowalking.adapter.SetImageTask;
import com.photowalking.fragment.FriendFragment;
import com.photowalking.model.ApplyInfo;
import com.photowalking.model.Friend;
import com.photowalking.model.User;
import com.photowalking.utils.ProfileNetUtil;
import com.photowalking.utils.UrlPath;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lionel on 2017/8/17.
 */

public class AddFriendActivity extends Activity{
    @Bind(R.id.tab_fri_apply_agree)
    LinearLayout ll_agree;
    @Bind(R.id.tab_fri_apply_user)
    LinearLayout ll_user;
    @Bind(R.id.tab_fri_apply_img)
    ImageView img;
    @Bind(R.id.tab_fri_apply_name)
    TextView tv_name;
    @Bind(R.id.tab_fri_apply_info)
    TextView tv_info;
    @Bind(R.id.tab_fri_apply_remark)
    EditText et_remark;

    private String msg;
    private int uida;
    private int uidb;
    private String remarka;
    private String remarkb;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.friend_add);
        ButterKnife.bind(this);

        String s = getIntent().getStringExtra("applyInfo");
        Gson gson = new Gson();
        ApplyInfo applyInfo = gson.fromJson(s,ApplyInfo.class);
        uida = applyInfo.getUida();
        uidb = applyInfo.getUidb();
        remarka = applyInfo.getRemarka();
        final String unamea = applyInfo.getUnamea();
        String info = applyInfo.getInfo();

        tv_name.setText(unamea);
        tv_info.setText(info);

        ll_agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remarkb = et_remark.getText().toString();
                if(TextUtils.isEmpty(remarkb))
                    remarkb = unamea;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ProfileNetUtil profileNetUtil = new ProfileNetUtil();
                        String res = profileNetUtil.addFriendRequest(uida, uidb, remarka, remarkb);
                        if(res.equals("success")){
                            FriendFragment.getAdapter().addItem(new Friend(uida,remarkb));
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    FriendFragment.getAdapter().notifyDataSetChanged();
                                }
                            });
                            msg = "添加成功";
                        }else{
                            msg = "添加失败";
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getBaseContext(),msg, Toast.LENGTH_SHORT).show();
                            }
                        });
                        finish();
                    }
                }).start();
            }
        });

        ll_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ProfileNetUtil profileNetUtil = new ProfileNetUtil();
                        User u = profileNetUtil.getUserByUid(UrlPath.getUserByUidUrl + uida, String.valueOf(uida));
                        if(u!=null){
                            Gson g = new Gson();
                            String jsonstr = g.toJson(u);
                            final Intent intent = new Intent(AddFriendActivity.this,ShowUserInfoActivity.class);
                            intent.putExtra("jsonstr",jsonstr);
                            startActivity(intent);
                        }
                    }
                }).start();
            }
        });

        new SetImageTask().execute(new ImageHolder(UrlPath.getPicUrl+uida, img));

    }

}
