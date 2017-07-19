package com.photowalking;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.photowalking.model.ShareItem;
import com.photowalking.utils.OkManager;
import com.photowalking.utils.UrlPath;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by liujinxu on 17/7/18.
 */

public class EditTextActivity extends AppCompatActivity {

    @Bind(R.id.text_share)
    EditText _text;
    @Bind(R.id.send)
    Button _send;
    boolean flag = true;
    String str = "";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.share_text);
        ButterKnife.bind(this);
        //Log.e("edit actiity :","!!!!!");
        Intent intent=getIntent();
        final String pathname = intent.getStringExtra("pathname");
        Log.e("pathname:", pathname);
        final String jsonStr = intent.getStringExtra("jsonStr");
        Log.e("jsonStr:", jsonStr);
        Gson gson = new Gson();
        final ShareItem shareItem = gson.fromJson(jsonStr, ShareItem.class);

        _send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String text = _text.getText().toString();
                if(text.length()>140 || text=="") {
                    Toast.makeText(getBaseContext(), "字数在0-140之间", Toast.LENGTH_LONG).show();
                } else {
                    new Thread(new Runnable(){
                        @Override
                        public void run() {
                            OkManager okManager = new OkManager();
                            shareItem.setPoem(text);
                            Gson g = new Gson();
                            String siJson = g.toJson(shareItem);
                            File uploadZip = new File(pathname);
                            str = okManager.sendStringByPost("", siJson);
                            okManager.uploadFile(UrlPath.uploadUrl, uploadZip);
                            flag = false;
                        }}).start();
                    if (str.equals("success")) {
                        finish();
                    }
                }
            }
        });

    }
}
