package com.photowalking.profile;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.photowalking.FragmentsActity;
import com.photowalking.R;
import com.photowalking.viewUtils.StatusBarUtil;

import butterknife.ButterKnife;

/**
 * Created by liujinxu on 17/7/10.
 */

public class ProfileChargeActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        StatusBarUtil.setTransparent(this);
        setContentView(R.layout.profile_charge);
        ImageView img = (ImageView)findViewById(R.id.profile_charge_img);
        img.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String[] str = {"识别二维码","取消"};
                new AlertDialog.Builder(ProfileChargeActivity.this)
                        .setItems(str, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(which==0){
                                    Intent intent = new Intent();
                                    intent.setAction(Intent.ACTION_VIEW);
                                    Uri content = Uri.parse("https://QR.ALIPAY.COM/FKX01954WSM6C6Y6ZVWC58");
                                    intent.setData(content);
                                    startActivity(intent);
                                }
                                dialog.dismiss();
                            }
                        }).show();
                return true;
            }
        });

        LinearLayout ll_back = (LinearLayout) findViewById(R.id.profile_charge_back);
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        startActivityForResult(new Intent(this, FragmentsActity.class),1);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_right_out);
    }
}
