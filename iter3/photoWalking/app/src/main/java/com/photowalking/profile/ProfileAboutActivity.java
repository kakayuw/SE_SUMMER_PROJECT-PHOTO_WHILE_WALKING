package com.photowalking.profile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import com.photowalking.FragmentsActity;
import com.photowalking.R;
import com.photowalking.viewUtils.StatusBarUtil;

/**
 * Created by liujinxu on 17/7/10.
 */

public class ProfileAboutActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        StatusBarUtil.setTransparent(this);
        setContentView(R.layout.profile_about);

        LinearLayout ll_back = (LinearLayout) findViewById(R.id.profile_about_back);
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
