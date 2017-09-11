package com.photowalking;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.photowalking.receiver.MyReceiver;
import com.photowalking.utils.LocalBroadcastManager;
import com.photowalking.utils.UserInfoSharedPreference;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by liujinxu on 17/7/5.
 */

public class WelcomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            Thread.sleep(600);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        registerMessageReceiver();

        String uid = UserInfoSharedPreference.getUid(this);
        String uname = UserInfoSharedPreference.getUname(this);
        if(uid == null || uname == null){
            startActivity(new Intent(this,MainActivity.class));
            finish();
            overridePendingTransition(0,R.anim.bottom_out);
        }else{
            Intent intent = new Intent(this, FragmentsActity.class);
            intent.putExtra("me",uid);
            intent.putExtra("uname",uname);
            startActivity(intent);
            finish();
            overridePendingTransition(0,R.anim.bottom_out);
        }
    }

    private MyReceiver mReceiver;

    public void registerMessageReceiver() {
        mReceiver = new MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(JPushInterface.ACTION_MESSAGE_RECEIVED);
        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, filter);
    }

}