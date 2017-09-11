package com.photowalking;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.photowalking.utils.LocalBroadcastManager;
import com.photowalking.receiver.MyReceiver;
import com.photowalking.utils.OkManager;
import com.photowalking.viewUtils.StatusBarUtil;

import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        StatusBarUtil.setTransparent(this);
        setContentView(R.layout.activity_main);
        setNotificationStyle();

        Button login = (Button) findViewById(R.id.main_btn_login);
        Button register = (Button) findViewById(R.id.main_btn_register);

        if(!OkManager.checkNetwork(getApplicationContext())){
            new AlertDialog.Builder(this,R.style.AppTheme_Light_Dialog)
                    .setMessage("未连接到网络或网络不可用,\n为使用全部功能请打开网络")
                    .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_SETTINGS);
                            startActivity(intent);
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).show();
        };

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_right_in,R.anim.push_left_out);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, VerifyPhoneActivity.class);
                intent.putExtra("aim","signup");
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_right_in,R.anim.push_left_out);
            }
        });

        checkPerssion();

    }

    private void checkPerssion(){
        String[] permission = new String[2];
        int reqcode = 0;
        int READ = ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE);
        int WRITE = ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE);
        if(READ != PERMISSION_GRANTED){
            reqcode |= 1;
            permission[0]=READ_EXTERNAL_STORAGE;
        }
        if(WRITE != PERMISSION_GRANTED){
            reqcode |= 2;
            permission[1]=WRITE_EXTERNAL_STORAGE;
        }
        if(reqcode>0)
            ActivityCompat.requestPermissions(this,permission,reqcode);
    }

    public void setNotificationStyle(){
        BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(this);
        builder.statusBarDrawable = R.drawable.ic_white_launcher;
        builder.notificationFlags = Notification.FLAG_SHOW_LIGHTS
                | Notification.FLAG_AUTO_CANCEL
                | Notification.FLAG_ONGOING_EVENT;  //设置呼吸灯闪烁
        builder.notificationDefaults = Notification.DEFAULT_SOUND
                | Notification.DEFAULT_LIGHTS;  // 设置为铃声、呼吸灯闪烁都要
        JPushInterface.setDefaultPushNotificationBuilder(builder);
    }
}
