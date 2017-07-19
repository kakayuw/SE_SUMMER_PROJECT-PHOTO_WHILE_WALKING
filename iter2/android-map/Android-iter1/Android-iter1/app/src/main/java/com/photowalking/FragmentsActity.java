package com.photowalking;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

import com.photowalking.fragment.FriendFragment;
import com.photowalking.fragment.MainFragment;
import com.photowalking.fragment.MineFragment;
import com.photowalking.fragment.ShareFragment;

/**
 * Created by lionel on 2017/7/10.
 */

public class FragmentsActity extends Activity implements View.OnClickListener {

    /* Four ImageButtons at bottom */
    private ImageButton mainTabImgBtn;
    private ImageButton friendTabImgBtn;
    private ImageButton shareTabImgBtn;
    private ImageButton mineTabImgBtn;

    /* Four Framgments at center */
    private MainFragment mainFragment;
    private FriendFragment friendFragment;
    private ShareFragment shareFragment;
    private MineFragment mineFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.tabs_layout);
        init();
        setSelect(0);
    }

    private void init(){
        mainTabImgBtn = (ImageButton) findViewById(R.id.main_tab_img);
        friendTabImgBtn = (ImageButton) findViewById(R.id.friend_tab_img);
        shareTabImgBtn = (ImageButton) findViewById(R.id.share_tab_img);
        mineTabImgBtn = (ImageButton) findViewById(R.id.mine_tab_img);

        mainTabImgBtn.setOnClickListener(this);
        friendTabImgBtn.setOnClickListener(this);
        shareTabImgBtn.setOnClickListener(this);
        mineTabImgBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.main_tab_img:
                setSelect(0);
                break;
            case R.id.friend_tab_img:
                setSelect(1);
                break;
            case R.id.share_tab_img:
                setSelect(2);
                break;
            case R.id.mine_tab_img:
                setSelect(3);
                break;
            default: break;
        }
    }

    private void setSelect(int idx){
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();

        resetImgBtn();
        hideFragment(fragmentTransaction);
        switch (idx){
            case 0:
                if (mainFragment == null){
                    mainFragment = new MainFragment();
                    fragmentTransaction.add(R.id.tab_content,mainFragment);
                }else{
                    fragmentTransaction.show(mainFragment);

                }
                fragmentTransaction.commit();
                mainTabImgBtn.setImageResource(R.drawable.homepage2);

                break;
            case 1:
                if (friendFragment==null){
                    friendFragment=new FriendFragment();
                    fragmentTransaction.add(R.id.tab_content,friendFragment);
                }else{
                    fragmentTransaction.show(friendFragment);
                }

                fragmentTransaction.commit();
                friendTabImgBtn.setImageResource(R.drawable.friends2);


                break;
            case 2:
                if (shareFragment==null){
                    shareFragment=new ShareFragment();
                    fragmentTransaction.add(R.id.tab_content,shareFragment);
                }else{
                    fragmentTransaction.show(shareFragment);
                }
                fragmentTransaction.commit();
                shareTabImgBtn.setImageResource(R.drawable.sharedcircle2);
                break;
            case 3:
                if (mineFragment==null){
                    mineFragment=new MineFragment();
                    fragmentTransaction.add(R.id.tab_content,mineFragment);
                }else{
                    fragmentTransaction.show(mineFragment);
                }
                fragmentTransaction.commit();
                mineTabImgBtn.setImageResource(R.drawable.me2);
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

    private void resetImgBtn() {
        mainTabImgBtn.setImageResource(R.drawable.homepage);
        friendTabImgBtn.setImageResource(R.drawable.friends);
        shareTabImgBtn.setImageResource(R.drawable.sharedcircle);
        mineTabImgBtn.setImageResource(R.drawable.me);
    }

}
