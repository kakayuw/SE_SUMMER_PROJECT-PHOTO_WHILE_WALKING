package com.photowalking.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.photowalking.R;
import com.photowalking.adapter.FragAdapter;

/**
 * Created by lionel on 2017/7/11.
 */

public class ShareFragment extends Fragment implements View.OnClickListener {

    /* Three LinearLayouts at top */
    private LinearLayout friTab;
    private LinearLayout allTab;
    private LinearLayout myTab;

    /* Three TextViews at top */

    private TextView friText;
    private TextView allText;
    private TextView myText;

    private static View shareView;
    public static ListView listView;

    private ViewPager vp;
    private LinearLayout indicator;
    private RelativeLayout.LayoutParams params;

    private String uid;
    private String uname;

    private int idx = 0;
    private boolean isFirstShow = true;
    private boolean isPrepared = false;

    public static ShareFragment getInstance(String uid, String uname){
        ShareFragment shareFragment = new ShareFragment();
        Bundle bundle = new Bundle();
        bundle.putString("me",uid);
        bundle.putString("uname",uname);
        shareFragment.setArguments(bundle);
        return  shareFragment;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isFirstShow && isVisibleToUser && isPrepared){
            isFirstShow = false;
            FragAdapter fragAdapter = new FragAdapter(getChildFragmentManager());
            ContFragment friShare = ContFragment.getInstance(R.layout.share_fri, uid, uname);
            fragAdapter.addItem(friShare);
            fragAdapter.addItem(ContFragment.getInstance(R.layout.share_all, uid, uname));
            fragAdapter.addItem(ContFragment.getInstance(R.layout.share_mine, uid, uname));

            vp.setAdapter(fragAdapter);
            friShare.setUserVisibleHint(true);
            vp.setCurrentItem(0, false);

            vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    int left = position*params.width + positionOffsetPixels / 3;
                    params.leftMargin = left;
                    indicator.setLayoutParams(params);
                }

                @Override
                public void onPageSelected(int position) {
                    resetImgBtn();
                    idx = position;
                    switch(position){
                        case 0:
                            friText.setTextColor(0xFFE43F3F);
                            listView = ContFragment.getListView(0);
                            break;
                        case 1:
                            allText.setTextColor(0xFFE43F3F);
                            listView = ContFragment.getListView(1);
                            break;
                        case 2:
                            myText.setTextColor(0xFFE43F3F);
                            listView = ContFragment.getListView(2);
                            break;
                        default:
                            break;
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {}
            });

            vp.setOffscreenPageLimit(2); //pervent page from being destroyed

            listView = ContFragment.getListView(0);
        }
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        shareView = inflater.inflate(R.layout.tab_share, container, false);
        isPrepared = true;
        init();

        uid = getArguments().getString("me");
        uname = getArguments().getString("uname");
        initIndicator();
        friText.setTextColor(0xFFE43F3F);

        return shareView;
    }


    private void init(){
        vp = (ViewPager)shareView.findViewById(R.id.share_list_view);

        friText = (TextView) shareView.findViewById(R.id.fri_share_tab_tv);
        allText = (TextView)shareView.findViewById(R.id.all_share_tab_tv);
        myText = (TextView)shareView.findViewById(R.id.my_share_tab_tv);


        friTab = (LinearLayout)shareView.findViewById(R.id.fri_share_tab);
        allTab = (LinearLayout)shareView.findViewById(R.id.all_share_tab);
        myTab = (LinearLayout)shareView.findViewById(R.id.my_share_tab);

        friTab.setOnClickListener(this);
        allTab.setOnClickListener(this);
        myTab.setOnClickListener(this);

    }

    public void initIndicator() {
        indicator = (LinearLayout) shareView.findViewById(R.id.share_indicator);
        DisplayMetrics dm = new DisplayMetrics();//初始化DisplayMetrics对象
        this.getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);//将当前窗口信息放入DisplayMetrics类中
        int s_width = dm.widthPixels;// 获取分辨率宽度
        params = (RelativeLayout.LayoutParams)indicator.getLayoutParams();
        params.width = s_width/3;
        indicator.setLayoutParams(params);
    }

    @Override
    public void onClick(View v) {
        resetImgBtn();
        switch (v.getId()) {
            case R.id.fri_share_tab:
                idx = 0;
                vp.setCurrentItem(0, false);
                friText.setTextColor(0xFFE43F3F);
                break;
            case R.id.all_share_tab:
                idx = 1;
                vp.setCurrentItem(1, false);
                allText.setTextColor(0xFFE43F3F);
                break;
            case R.id.my_share_tab:
                idx = 2;
                vp.setCurrentItem(2, false);
                myText.setTextColor(0xFFE43F3F);
                break;
            default:
                break;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("idx", idx);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        int index = 0;
        if(savedInstanceState!=null)
            index = savedInstanceState.getInt("idx",0);
        vp.setCurrentItem(index,false);
        super.onViewStateRestored(savedInstanceState);
    }


    private void resetImgBtn() {
        friText.setTextColor(0xFF888888);
        allText.setTextColor(0xFF888888);
        myText.setTextColor(0xFF888888);
    }


}
