package com.photowalking.main;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.utils.L;
import com.photowalking.R;
import com.photowalking.adapter.FriendAdapter;
import com.photowalking.fragment.FriendFragment;
import com.photowalking.model.Friend;
import com.photowalking.model.Photo;
import com.photowalking.model.SendPicture;
import com.photowalking.model.ShareItem;
import com.photowalking.model.Trace;
import com.photowalking.model.TraceInfo;
import com.photowalking.model.UploadInfoStr;
import com.photowalking.model.UploadPhoto;
import com.photowalking.utils.BitmapUtil;
import com.photowalking.utils.FileUtil;
import com.photowalking.utils.OkManager;
import com.photowalking.utils.UrlPath;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by liujinxu on 17/7/18.
 */

public class EditTextActivity extends Activity implements View.OnClickListener{

    String TAG = "EditTextActivity";

    @Bind(R.id.main_share_poem)
    EditText poem;
    @Bind(R.id.main_share_send)
    Button btn_send;
    @Bind(R.id.main_share_all)
    LinearLayout ll_all;
    @Bind(R.id.main_share_all_tv)
    TextView tv_all;
    @Bind(R.id.main_share_fri)
    LinearLayout ll_fri;
    @Bind(R.id.main_share_fri_tv)
    TextView tv_fri;
    @Bind(R.id.main_share_one)
    LinearLayout ll_one;
    @Bind(R.id.main_share_one_name)
    TextView tv_one;

    private TraceInfo ti;
    private String sid;
    private String uid;
    private String uname;
    private String date;
    private String time;
    private int photoNumb;
    private double miles;
    private String polylines;

    private ShareItem si = new ShareItem();

    private String text;
    private String str;
    private String fileUploaded;
    private int type = -3;
    private int choseId=0;
    private Handler handler = new Handler();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main_share_text);
        ButterKnife.bind(this);

        Intent intent=getIntent();
        uid = intent.getStringExtra("me");
        uname = intent.getStringExtra("uname");
        String  traceInfo = intent.getStringExtra("ti");
        date = intent.getStringExtra("date");
        time = intent.getStringExtra("time");
        miles = intent.getDoubleExtra("miles", 0);

        Gson gson = new Gson();
        ti = gson.fromJson(traceInfo, TraceInfo.class);
        sid = ti.getTraceId();

        si.setSid(ti.getTraceId());
        si.setTitle(ti.getTraceName());
        String stime = ti.getTraceDate() + " " + ti.getStartTime();
        String etime = ti.getTraceDate() + " " + ti.getEndTime();
        si.setStarttime(stime);
        si.setEndtime(etime);
        si.setUid(Integer.parseInt(uid));
        si.setUsername(uname);
        si.setUpvote(0); //share to all
        init();



    }

    private void init(){

        ll_all.setOnClickListener(this);
        ll_fri.setOnClickListener(this);
        ll_one.setOnClickListener(this);

        poem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int length = poem.getText().toString().length();
                if(length>=140)
                    Toast.makeText(getApplicationContext(),"字数必须不大于140字",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type == -3){
                    Toast.makeText(EditTextActivity.this,"请选择分享对象",Toast.LENGTH_SHORT).show();
                    return;
                }
                text = poem.getText().toString();
                final ProgressDialog progressDialog = new ProgressDialog(EditTextActivity.this, R.style.AppTheme_Dark_Dialog);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("正在上传...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                new Thread(new Runnable(){
                    @Override
                    public void run() {
                        OkManager okManager = new OkManager();
                        si.setPoem(text);
                        List<Photo> photoList = new Select().from(Photo.class).where("traceid = ?", sid).execute();
                        photoNumb = photoList.size();
                        si.setPicnum(photoNumb);
                        si.setType(type);
                        Gson g = new Gson();
                        String siStr = g.toJson(si);
                        String polylines = ((Trace) new Select().from(Trace.class).where("traceid = ?", sid).execute().get(0)).getPolylines();
                        str = okManager.sendStringByPost(UrlPath.uploadSitemUrl,siStr);
                        if(str.equals("success")){
                            Log.e(">>>>>>>>>>","upload started.....");
                            List<UploadPhoto> photos = new ArrayList<>();
                            for(Photo p: photoList)
                                photos.add(new UploadPhoto(p));
                            UploadInfoStr uis = new UploadInfoStr(
                                    sid,
                                    Integer.parseInt(ti.getUserid()),
                                    uname,
                                    ti.getTraceName(),
                                    ti.getStartTime(),
                                    ti.getEndTime(),
                                    ti.getTraceDate(),
                                    miles,
                                    photoList.size(),
                                    polylines,
                                    photos
                            );
                            String classInfoStr = new Gson().toJson(uis);
                            okManager.sendStringByPost(UrlPath.uploadUrl, classInfoStr);
                            Log.e("<<<<<<<<<<","upload ended.....");
                            fileUploaded = uploadPicture(photos);
                            if(fileUploaded.equals("success")){
                                str = "上传成功";
                            }else{
//                              okManager.deleteStringByGet(UrlPath.deleteSitemUrl);
                            }
                            FileUtil.deleteImage(EditTextActivity.this,UrlPath.tmpPic);
                        }else if(str.equals("failed")){
                            str = "已上传过";
                        }else if(str.equals("wechat")){
                            str = "上传成功";
                        }else
                            str = "上传失败";
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(),str,Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
                    }
                }).start();
            }
        });
    }


    @Override
    public void onClick(View v) {
        resetColor();
        type = -3;
        switch(v.getId()){
            case R.id.main_share_fri:
                ll_fri.setBackgroundResource(R.drawable.friendshare2);
                tv_fri.setTextColor(0xffd81e06);
                type = -1;
                break;
            case R.id.main_share_all:
                ll_all.setBackgroundResource(R.drawable.allpeople2);
                tv_all.setTextColor(0xffd81e06);
                type = -2;
                break;
            case R.id.main_share_one:
                tv_one.setText("");
                choseId = -3;
                new choseNameTask().execute();
            default: break;
        }
    }

    private void resetColor(){
        ll_fri.setBackgroundResource(R.drawable.friendshare);
        tv_fri.setTextColor(0xff8a8a8a);
        ll_all.setBackgroundResource(R.drawable.allpeople);
        tv_all.setTextColor(0xff8a8a8a);
        ll_one.setBackgroundResource(R.drawable.at);
        tv_one.setVisibility(View.GONE);
    }

    public class choseNameTask extends AsyncTask<Void, Void, List<Friend>> {

        @Override
        protected List<Friend> doInBackground(Void... params) {
            if(!OkManager.checkNetwork(EditTextActivity.this)){
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(EditTextActivity.this,"未连接到网络",Toast.LENGTH_SHORT).show();
                    }
                });
                return null;
            }
            OkManager manager = new OkManager<>();
            List<Friend> friends = manager.getAll(UrlPath.getFriUrl+uid, Friend.class);
            return friends;
        }

        @Override
        protected void onPostExecute(List<Friend> friends) {
            if (friends == null){
                return;
            }
            tv_one.setVisibility(View.VISIBLE);
            try {
                final FriendAdapter adapter = new FriendAdapter(EditTextActivity.this, UrlPath.getPicUrl,FriendAdapter.TMP);
                for (Friend f : friends)
                    adapter.addItem(f);
                new AlertDialog.Builder(EditTextActivity.this)
                        .setTitle("请选择")
                        .setAdapter(adapter, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                choseId = adapter.getItem(which).getUid();
                                type = choseId;
                                String name = adapter.getItem(which).getNickname();
                                ll_one.setBackgroundResource(R.drawable.at2);
                                tv_one.setText(name);
                                dialog.dismiss();
                            }
                        }).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String uploadPicture(List<UploadPhoto> photos){
        OkManager okManager = new OkManager();
        try {
            for (int i = 0; i < photoNumb; i++) {
                Log.e(">>>>","upload "+i);
                File file = new File(UrlPath.tmpPic);
                FileOutputStream outputStream = new FileOutputStream(file);
                Log.e(">>>",photos.get(i).getFilename());
                Bitmap bitmap = BitmapUtil.decodeSampledBitmapFromFd(photos.get(i).getFilename(),170,190);
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);

                okManager.uploadFile(UrlPath.uploadPicUrl+sid+"/"+i,file);
                Log.e(">>>>","complete "+i);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return "success";
    }
}