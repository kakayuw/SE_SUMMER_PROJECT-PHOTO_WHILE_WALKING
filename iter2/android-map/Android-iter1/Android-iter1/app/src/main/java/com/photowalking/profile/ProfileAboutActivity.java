package com.photowalking.profile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.photowalking.R;

/**
 * Created by liujinxu on 17/7/10.
 */

public class ProfileAboutActivity extends AppCompatActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.profile_about);
    }

}
