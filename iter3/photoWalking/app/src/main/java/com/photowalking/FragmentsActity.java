package com.photowalking;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.photowalking.adapter.FragAdapter;
import com.photowalking.fragment.FriendFragment;
import com.photowalking.fragment.MainFragment;
import com.photowalking.fragment.MineFragment;
import com.photowalking.fragment.ShareFragment;
import com.photowalking.utils.UrlPath;
import com.photowalking.viewUtils.StatusBarUtil;

import java.io.File;

/**
 * Created by lionel on 2017/7/10.
 */

public class FragmentsActity extends FragmentActivity implements View.OnClickListener {

    /* Four LinearLayout at bottom */
    private LinearLayout mainTab;
    private LinearLayout friendTab;
    private LinearLayout shareTab;
    private LinearLayout mineTab;

    /* Four ImageButtons at bottom */
    private ImageView mainTabImg;
    private ImageView friendTabImg;
    private ImageView shareTabImg;
    private ImageView mineTabImg;

    /* Four Framgments at center */
    private MainFragment mainFragment;
    private FriendFragment friendFragment;
    private ShareFragment shareFragment;
    private MineFragment mineFragment;

    private ViewPager vp;
    private FragAdapter fragAdapter;

    private static String uid;
    private static String uname;
    private static boolean friFragOpend = false;

    private int idx=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        StatusBarUtil.setTransparent(this);
        setContentView(R.layout.tabs_layout);

        uid = getIntent().getStringExtra("me");
        uname = getIntent().getStringExtra("uname");

        init();
        vp = (ViewPager)findViewById(R.id.tab_content);
        fragAdapter = new FragAdapter(getSupportFragmentManager());
        fragAdapter.addItem(MainFragment.getInstance(uid, uname));
        fragAdapter.addItem(FriendFragment.getInstace(uid));
        fragAdapter.addItem(ShareFragment.getInstance(uid, uname));
        fragAdapter.addItem(MineFragment.getInstance(uid));
        vp.setAdapter(fragAdapter);
        vp.setCurrentItem(0,false);
        vp.setOffscreenPageLimit(3);
        mainTabImg.setImageResource(R.drawable.homepage2);

        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                resetImg();
                idx = position;
                switch (position){
                    case 0:
                        mainTabImg.setImageResource(R.drawable.homepage2);
                        break;
                    case 1:
                        friendTabImg.setImageResource(R.drawable.friends2);
                        break;
                    case 2:
                        shareTabImg.setImageResource(R.drawable.sharedcircle2);
                        break;
                    case 3:
                        mineTabImg.setImageResource(R.drawable.me2);
                        break;
                    default:break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });
    }

    public static boolean isfriFragOpend(){
        return friFragOpend;
    }

    private void init(){
        mainTab = (LinearLayout) findViewById(R.id.main_tab);
        friendTab = (LinearLayout) findViewById(R.id.friend_tab);
        shareTab = (LinearLayout) findViewById(R.id.share_tab);
        mineTab = (LinearLayout) findViewById(R.id.mine_tab);

        mainTabImg = (ImageView) findViewById(R.id.main_tab_img);
        friendTabImg = (ImageView)findViewById(R.id.friend_tab_img);
        shareTabImg = (ImageView) findViewById(R.id.share_tab_img);
        mineTabImg = (ImageView) findViewById(R.id.mine_tab_img);

        mainTab.setOnClickListener(this);
        friendTab.setOnClickListener(this);
        shareTab.setOnClickListener(this);
        mineTab.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.main_tab:
                idx = 0;
                vp.setCurrentItem(0,false);
//                showFragment(0);
                break;
            case R.id.friend_tab:
                idx =1;
                vp.setCurrentItem(1,false);
//                showFragment(1);
                break;
            case R.id.share_tab:
                idx =2;
                vp.setCurrentItem(2,false);
//                showFragment(2);
                break;
            case R.id.mine_tab:
                idx =3;
                vp.setCurrentItem(3,false);
//                showFragment(3);
                break;
            default: break;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("idx", idx);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        showFragment(savedInstanceState.getInt("idx"));
        vp.setCurrentItem(savedInstanceState.getInt("idx",0),false);
        super.onRestoreInstanceState(savedInstanceState);
    }

    private void showFragment(int index){
        FragmentTransaction fragmentTransaction =  getSupportFragmentManager().beginTransaction();
        hideFragment(fragmentTransaction);
        resetImg();
        idx = index;
        switch (index){
            case 0:
                if (mainFragment == null){
                    mainFragment = MainFragment.getInstance(uid, uname);
                    fragmentTransaction.add(R.id.tab_content,mainFragment);
                }else{
                    fragmentTransaction.show(mainFragment);

                }
                fragmentTransaction.commit();
                mainTabImg.setImageResource(R.drawable.homepage2);
                break;
            case 1:
                if (friendFragment==null){
                    friendFragment = FriendFragment.getInstace(uid);
                    fragmentTransaction.add(R.id.tab_content,friendFragment);
                    friFragOpend = true;
                }else{
                    fragmentTransaction.show(friendFragment);
                }

                fragmentTransaction.commit();
                friendTabImg.setImageResource(R.drawable.friends2);
                break;
            case 2:
                if (shareFragment==null){
                    shareFragment = ShareFragment.getInstance(uid, uname);
                    fragmentTransaction.add(R.id.tab_content,shareFragment);
                }else{
                    fragmentTransaction.show(shareFragment);
                }
                fragmentTransaction.commit();
                shareTabImg.setImageResource(R.drawable.sharedcircle2);
                break;
            case 3:
                if (mineFragment==null){
                    mineFragment = MineFragment.getInstance(uid);
                    fragmentTransaction.add(R.id.tab_content,mineFragment);
                }else{
                    fragmentTransaction.show(mineFragment);
                }
                fragmentTransaction.commit();
                mineTabImg.setImageResource(R.drawable.me2);
                break;
            default: break;
        }
    }

    private void hideFragment(FragmentTransaction fragmentTransaction) {
        if (mainFragment!=null){
            fragmentTransaction.hide(mainFragment);
        }
        if (friendFragment!=null){
            fragmentTransaction.hide(friendFragment);
        }
        if (shareFragment!=null){
            fragmentTransaction.hide(shareFragment);
        }
        if (mineFragment!=null){
            fragmentTransaction.hide(mineFragment);
        }
    }

    private void resetImg() {
        mainTabImg.setImageResource(R.drawable.homepage);
        friendTabImg.setImageResource(R.drawable.friends);
        shareTabImg.setImageResource(R.drawable.sharedcircle);
        mineTabImg.setImageResource(R.drawable.me);
    }

    @Override
    protected void onDestroy() {
        //TODO DELETE FOLDERS & FILES
        File parent = new File(UrlPath.downloadPath);
        if(parent.exists()){
            File[] folders = parent.listFiles();
            if(folders.length > 0){
                for(int i=0; i< folders.length; ++i){
                    File folder = folders[i];
                    File[] files = folder.listFiles();
                    if(files.length>0){
                        for(int j=0; j<files.length; ++j){
                            files[i].delete();
                    }
                    folder.delete();
                }
    //            for(File folder:folders){
    //                File[] files = folder.listFiles();
    //                if(files.length>0){
    //                    for(File file : files)
    //                        file.delete();
    //                }
    //                folder.delete();
                }
            }
        }
        super.onDestroy();
    }
}
