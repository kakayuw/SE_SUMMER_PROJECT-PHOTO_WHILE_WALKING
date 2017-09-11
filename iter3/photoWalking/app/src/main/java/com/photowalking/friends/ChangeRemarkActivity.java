package com.photowalking.friends;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.photowalking.R;
import com.photowalking.fragment.FriendFragment;
import com.photowalking.utils.ProfileNetUtil;
import com.photowalking.viewUtils.StatusBarUtil;

import butterknife.ButterKnife;

/**
 * Created by lionel on 17/7/10.
 */

public class ChangeRemarkActivity extends Activity {

    private String fid;
    private String nickname;
    private String me;

    private LinearLayout ll_done;
    private EditText et_remark;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.friend_change_remark);
        ll_done = (LinearLayout) findViewById(R.id.tab_fri_chg_done);
        et_remark = (EditText) findViewById(R.id.tab_fri_chg_remark);

        fid = getIntent().getStringExtra("fid");
        nickname = getIntent().getStringExtra("remark");
        me = getIntent().getStringExtra("me");

        et_remark.setHint(nickname);

        ll_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = et_remark.getText().toString();
                if(TextUtils.isEmpty(str)){
                    Toast.makeText(ChangeRemarkActivity.this,"不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                nickname = str;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ProfileNetUtil profileNetUtil = new ProfileNetUtil();
                        final String res = profileNetUtil.chgRemarkRequest(me, fid, nickname);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(res.equals("success")){
                                    Toast.makeText(ChangeRemarkActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
                                    FriendFragment.getAdapter().chgItem(Integer.parseInt(fid),nickname);
                                    FriendFragment.getAdapter().notifyDataSetChanged();
                                    ShowUserInfoActivity.chgRemark(nickname);
                                }else{
                                    Toast.makeText(ChangeRemarkActivity.this,"修改失败",Toast.LENGTH_SHORT).show();
                                }
                                finish();
                            }
                        });
                    }
                }).start();
            }
        });
    }
}
