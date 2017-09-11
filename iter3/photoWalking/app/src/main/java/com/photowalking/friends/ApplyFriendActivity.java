package com.photowalking.friends;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.photowalking.R;
import com.photowalking.model.ApplyInfo;
import com.photowalking.utils.OkManager;
import com.photowalking.utils.UrlPath;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lionel on 2017/7/29.
 */

public class ApplyFriendActivity extends Activity {

    @Bind(R.id.tab_fri_add_info)
    EditText et_info;
    @Bind(R.id.tab_fri_add_remark)
    EditText et_remark;
    @Bind(R.id.tab_fri_add_send)
    LinearLayout send;

    private String fid;
    private String nickname;
    private String me;
    private String uname;

    private String msg;
    private String remark;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.friend_apply);
        ButterKnife.bind(this);

        fid = getIntent().getStringExtra("fid");
        nickname = getIntent().getStringExtra("remark");
        me = getIntent().getStringExtra("me");
        uname = getIntent().getStringExtra("uname");

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String info = et_info.getText().toString();
                remark = et_remark.getText().toString();
                if(TextUtils.isEmpty(remark))
                    remark = nickname;
                final ApplyInfo applyInfo = new ApplyInfo(Integer.parseInt(me),Integer.parseInt(fid),uname,remark,info);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        OkManager okManager = new OkManager();
                        Gson gson = new Gson();
                        String content = gson.toJson(applyInfo);
                        String res = okManager.sendStringByPost(UrlPath.applyFriUrl,content);
                        if(res.equals("success")){
                            msg = "发送成功";
                        }else{
                            msg = "发送失败";
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getBaseContext(),msg,Toast.LENGTH_SHORT).show();
                            }
                        });
                        finish();
                    }
                }).start();
            }
        });

    }

}
