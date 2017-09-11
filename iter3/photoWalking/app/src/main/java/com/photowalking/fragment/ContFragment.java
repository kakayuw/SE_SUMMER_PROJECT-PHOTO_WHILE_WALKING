package com.photowalking.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.photowalking.R;

import java.util.List;

import com.photowalking.adapter.ShareAdapter;
import com.photowalking.listener.ShareRefreshListener;
import com.photowalking.model.ShareItem;
import com.photowalking.share.AllCommentActivity;
import com.photowalking.share.ShareHolder;
import com.photowalking.share.ViewOthersDetailActivity;
import com.photowalking.utils.OkManager;
import com.photowalking.utils.UrlPath;
import com.photowalking.viewUtils.PullableRelativeLayout;

/**
 * Created by lionel on 2017/7/10.
 */

public class ContFragment extends Fragment {

    private View view;


    private PullableRelativeLayout prl;
    private static ListView[] listViews = new ListView[3];

    private String uid;
    private String uname;

    private boolean isFirstShow = true;
    private boolean isPrepared = false;

    public static ContFragment getInstance(int res, String uid, String uname){
        ContFragment fragment = new ContFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("res", res);
        bundle.putString("me",uid);
        bundle.putString("uname",uname);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isFirstShow && isVisibleToUser && isPrepared){
            isFirstShow = false;
            prl.autoRefresh();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        int res = getArguments().getInt("res");
        view = inflater.inflate(res, container, false);
        isPrepared = true;

        uid = getArguments().getString("me");
        uname = getArguments().getString("uname");
        switch(res){
            case R.layout.share_fri:
                ListView listFView = (ListView) view.findViewById(R.id.fri_share_list);
                listViews[0] = listFView;
                ShareHolder shareFHolder = new ShareHolder(getActivity(),listFView,UrlPath.getShareFriUrl+uid,uid,uname);
                prl = (PullableRelativeLayout)view.findViewById(R.id.fri_share_pullable_layout);
                prl.setOnRefreshListener(new ShareRefreshListener(getActivity(),shareFHolder));
                break;
            case R.layout.share_all:
                ListView listAView = (ListView) view.findViewById(R.id.all_share_list);
                listViews[1] = listAView;
                ShareHolder shareAHolder = new ShareHolder(getActivity(),listAView,UrlPath.getShareAllUrl+uid,uid,uname);
                prl = (PullableRelativeLayout)view.findViewById(R.id.all_share_pullable_layout);
                prl.setOnRefreshListener(new ShareRefreshListener(getActivity(),shareAHolder));
                break;
            case R.layout.share_mine:
                ListView listMView = (ListView) view.findViewById(R.id.my_share_list);
                listViews[2] = listMView;
                ShareHolder shareMHolder = new ShareHolder(getActivity(),listMView,UrlPath.getShareMineUrl+uid,uid,uname);
                prl = (PullableRelativeLayout)view.findViewById(R.id.my_share_pullable_layout);
                prl.setOnRefreshListener(new ShareRefreshListener(getActivity(),shareMHolder));
                break;
            default: break;
        }
        return view;
    }

    public static ListView getListView(int idx){
        return listViews[idx];
    }
}
