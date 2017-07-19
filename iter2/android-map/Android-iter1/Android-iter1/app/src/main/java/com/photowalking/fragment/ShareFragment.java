package com.photowalking.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.photowalking.R;

/**
 * Created by lionel on 2017/7/11.
 */

public class ShareFragment extends Fragment implements View.OnClickListener {

    /* Three ImageButtons at top */
    private ImageButton friImgBtn;
    private ImageButton allImgBtn;
    private ImageButton myImgBtn;

    /* Three Framgments at center */
    private ContFragment friFragment;
    private ContFragment allFragment;
    private ContFragment myFragment;


    private static View shareView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        shareView = inflater.inflate(R.layout.tab_share, container, false);
        init();
        setSelect(0);
        return shareView;
    }

    private void init(){
        friImgBtn = (ImageButton)shareView.findViewById(R.id.fri_share_tab_img);
        allImgBtn = (ImageButton)shareView.findViewById(R.id.all_share_tab_img);
        myImgBtn = (ImageButton)shareView.findViewById(R.id.my_share_tab_img);

        friImgBtn.setOnClickListener(this);
        allImgBtn.setOnClickListener(this);
        myImgBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        resetImgBtn();
        switch (v.getId()) {
            case R.id.fri_share_tab_img:
                setSelect(0);
                break;
            case R.id.all_share_tab_img:
                setSelect(1);
                break;
            case R.id.my_share_tab_img:
                setSelect(2);
                break;
            default:
                break;
        }
    }

    private void setSelect(int idx){
        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        resetImgBtn();
        hideFragment(fragmentTransaction);
        switch (idx){
            case 0:
                friImgBtn.setImageResource(R.drawable.friends2);
                Toast.makeText(getActivity(), "you click0", Toast.LENGTH_SHORT).show();
                if (friFragment == null){
                    friFragment = ContFragment.getInstance(R.layout.share_fri);
                    fragmentTransaction.add(R.id.share_list_view,friFragment);
                }else {
                    fragmentTransaction.show(friFragment);
                }
                fragmentTransaction.commit();
                break;
            case 1:
                allImgBtn.setImageResource(R.drawable.allpeople2);
                Toast.makeText(getActivity(), "you click1", Toast.LENGTH_SHORT).show();
                if (allFragment == null){
                    allFragment = ContFragment.getInstance(R.layout.share_all);
                    fragmentTransaction.add(R.id.share_list_view,allFragment);
                }else{
                    fragmentTransaction.show(allFragment);
                }
                fragmentTransaction.commit();
                break;
            case 2:
                myImgBtn.setImageResource(R.drawable.my2);
                Toast.makeText(getActivity(), "you click2", Toast.LENGTH_SHORT).show();
                if (myFragment == null){
                    myFragment = ContFragment.getInstance(R.layout.share_mine);
                    fragmentTransaction.add(R.id.share_list_view,myFragment);
                }else{
//                    fragmentTransaction.remove(myFragment);
//                    myFragment = ContFragment.getInstance(R.layout.share_mine);
//                    fragmentTransaction.add(R.id.share_list_view,myFragment);
                    fragmentTransaction.show(myFragment);
                }
                fragmentTransaction.commit();
                break;
            default: break;
        }
    }

    private void hideFragment(FragmentTransaction fragmentTransaction) {
        if (friFragment!=null){
            fragmentTransaction.hide(friFragment);
        }
        if (allFragment!=null){
            fragmentTransaction.hide(allFragment);
        }
        if (myFragment!=null){
            fragmentTransaction.hide(myFragment);
        }
    }

    private void resetImgBtn() {
        friImgBtn.setImageResource(R.drawable.friends);
        allImgBtn.setImageResource(R.drawable.allpeople);
        myImgBtn.setImageResource(R.drawable.my);

    }



}
