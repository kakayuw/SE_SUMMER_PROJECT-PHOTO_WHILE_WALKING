package com.photowalking.listener;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.photowalking.profile.BonusActivity;

/**
 * Created by lionel on 2017/8/24.
 */

public class MainTvClickListener implements View.OnClickListener{

    private int count = 0;
    private long start;
    private long end;
    private Activity activity;

    public MainTvClickListener(Activity activity){
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        count++;
        if(count == 1){
            start = System.currentTimeMillis();
            Toast.makeText(activity,"请连续点击5下~(￣∀￣)~",Toast.LENGTH_SHORT).show();
        }
        if (count == 5) {
            end = System.currentTimeMillis();
        }
        if (count >= 5) {
            if ((end - start) < 2000) {
                activity.startActivityForResult(new Intent(activity, BonusActivity.class),1);
            }
            count = 0;
        }
        if ((System.currentTimeMillis() - start) > 1000) {
            count = 0;
            start = System.currentTimeMillis();
        }

    }
}
