package com.photowalking.profile;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.utils.L;
import com.photowalking.R;
import com.photowalking.utils.UrlPath;
import com.photowalking.viewUtils.StatusBarUtil;

/**
 * Created by lionel on 17/8/11.
 */

public class AvatarShowActivity extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        StatusBarUtil.setTransparent(this);
        setContentView(R.layout.profile_avatar_show);

        LinearLayout ll_out = (LinearLayout)findViewById(R.id.avatar_show_outside);
        ll_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ImageView imageView = (ImageView)findViewById(R.id.avatar_show_img);
        String me = getIntent().getStringExtra("me");
        ImageLoader.getInstance().displayImage("file://"+UrlPath.avatarPath+"/"+me+".jpg", imageView);
    }

}
