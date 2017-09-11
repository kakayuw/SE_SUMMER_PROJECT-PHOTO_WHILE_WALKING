package com.photowalking.profile;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.icu.util.Output;
import android.icu.util.ULocale;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.isseiaoki.simplecropview.CropImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.utils.L;
import com.photowalking.R;
import com.photowalking.fragment.MineFragment;
import com.photowalking.utils.FileUtil;
import com.photowalking.utils.OkManager;
import com.photowalking.utils.UrlPath;
import com.photowalking.viewUtils.StatusBarUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Created by lionel on 17/8/11.
 */

public class AvatarChoseActivity extends Activity {

    private String type;
    private String me;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.profile_avatar_chose);
        final CropImageView imageView = (CropImageView)findViewById(R.id.avatar_chose_img);

        me = getIntent().getStringExtra("me");
        type = getIntent().getStringExtra("type");

        LinearLayout ll_back = (LinearLayout)findViewById(R.id.avatar_chose_back);
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        LinearLayout ll_send = (LinearLayout)findViewById(R.id.avatar_chose_send);
        ll_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Bitmap bmp = imageView.getCroppedBitmap();
                try {
                    OutputStream outputStream = new FileOutputStream(UrlPath.tmpPic);
                    bmp.compress(Bitmap.CompressFormat.JPEG, 90, outputStream);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            OkManager okManager = new OkManager();
                            File file = new File(UrlPath.tmpPic);
                            String res = okManager.uploadFile(UrlPath.chgUserPicUrl+me,file);
                            if(res.equals("success")){
                                OutputStream output = null;
                                final String pic = UrlPath.avatarPath+"/"+me+".jpg";
                                try {
                                    output = new FileOutputStream(pic);
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                                bmp.compress(Bitmap.CompressFormat.JPEG, 90, output);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        FileUtil.deleteImage(AvatarChoseActivity.this, UrlPath.tmpPic);
                                        MineFragment.notifyPhotoUpdate("file://"+pic);
                                    }
                                });
                            }else{
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        FileUtil.deleteImage(AvatarChoseActivity.this, UrlPath.tmpPic);
                                        Toast.makeText(AvatarChoseActivity.this,"更换头像失败",Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    }).start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                finish();
            }
        });

        String uri = "file://";
        if(type.equals("camera")){
            uri += UrlPath.tmpPic;
        }
        if(type.equals("gallery")){
            uri += FileUtil.getRealPathFromUri( this, getIntent().getData());
        }
        Log.e("Chose File>>>",uri);
        ImageLoader.getInstance().displayImage(uri,imageView);
    }
}
