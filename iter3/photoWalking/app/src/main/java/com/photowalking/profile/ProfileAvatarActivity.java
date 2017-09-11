package com.photowalking.profile;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.photowalking.R;
import com.photowalking.utils.FileUtil;
import com.photowalking.utils.UrlPath;
import com.photowalking.viewUtils.StatusBarUtil;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lionel on 17/8/11.
 */

public class ProfileAvatarActivity extends Activity {

    @Bind(R.id.profile_avatar_top)
    LinearLayout ll_top;
    @Bind(R.id.profile_avatar_camera)
    LinearLayout ll_camera;
    @Bind(R.id.profile_avatar_gallery)
    LinearLayout ll_gallery;
    @Bind(R.id.profile_avatar_show)
    LinearLayout ll_show;
    @Bind(R.id.profile_avatar_cancel)
    LinearLayout ll_cancel;

    private String me;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        StatusBarUtil.setTransparent(this);
        setContentView(R.layout.profile_avatar);
        ButterKnife.bind(this);

        me = getIntent().getStringExtra("me");

        ll_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ll_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, FileUtil.getUri(ProfileAvatarActivity.this,UrlPath.tmpPic));
                startActivityForResult(intent, 1);
            }
        });

        ll_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 2);
            }
        });

        ll_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileAvatarActivity.this, AvatarShowActivity.class);
                intent.putExtra("me",me);
                startActivity(intent);
                finish();
            }
        });

        ll_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_CANCELED){
            finish();
            return;
        }
        Intent intent = new Intent(this, AvatarChoseActivity.class);
        switch (requestCode){
            case 1:
                intent.putExtra("type","camera");
                intent.putExtra("me",me);
                startActivity(intent);
                finish();
                break;
            case 2:
                intent.putExtra("type","gallery");
                intent.putExtra("me",me);
                intent.setData(data.getData());
                startActivity(intent);
                finish();
                break;
            default: break;
        }
    }

}
