package com.photowalking.fragment;

import android.app.Activity;
import android.view.View;
import android.widget.ImageButton;

import com.photowalking.R;

/**
 * Created by lionel on 2017/7/10.
 */

public class ShareOnclickListener extends Activity implements View.OnClickListener{

    private ImageButton friImgBtn;
    private ImageButton allImgBtn;
    private ImageButton myImgBtn;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fri_share_tab_img:
                resetImgBtn(R.id.fri_share_tab_img);
                break;
            case R.id.all_share_tab_img:
                resetImgBtn(R.id.all_share_tab_img);
                break;
            case R.id.my_share_tab_img:
                resetImgBtn(R.id.my_share_tab_img);
                break;
            default:
                break;
        }
    }

    private void resetImgBtn( int id) {
        friImgBtn = (ImageButton)findViewById(R.id.fri_share_tab_img);
        if(friImgBtn.getId()==id)
            friImgBtn.setImageResource(R.drawable.friends2);
        else
            friImgBtn.setImageResource(R.drawable.friends);
        allImgBtn = (ImageButton)findViewById(R.id.all_share_tab_img);
        if(allImgBtn.getId()==id)
            allImgBtn.setImageResource(R.drawable.allpeople2);
        else
            allImgBtn.setImageResource(R.drawable.allpeople);
        myImgBtn = (ImageButton)findViewById(R.id.my_share_tab_img);
        if(myImgBtn.getId()==id)
            myImgBtn.setImageResource(R.drawable.my2);
        else
            myImgBtn.setImageResource(R.drawable.my);

    }

}
