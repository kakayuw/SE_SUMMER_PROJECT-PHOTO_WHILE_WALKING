package com.photowalking.profile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.photowalking.R;

import butterknife.ButterKnife;

/**
 * Created by liujinxu on 17/7/10.
 */

public class ProfileChargeActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_charge);
        ButterKnife.bind(this);
    }
}
