package com.photowalking.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.photowalking.R;

import java.io.IOException;
import java.util.List;

import com.photowalking.adapter.FriendAdapter;
import com.photowalking.friends.SearchUserActivity;
import com.photowalking.friends.ShowUserInfoActivity;
import com.photowalking.model.ApplyInfo;
import com.photowalking.model.Friend;
import com.photowalking.model.User;
import com.photowalking.utils.LocalBroadcastManager;
import com.photowalking.utils.OkManager;
import com.photowalking.utils.ProfileNetUtil;
import com.photowalking.utils.UrlPath;
import com.photowalking.viewUtils.PullableRelativeLayout;

/**
 * Created by lionel on 2017/7/11.
 */

public class FriendFragment extends Fragment {

    private View view;
    private PullableRelativeLayout prl;
    private ListView flist;
    ImageView plus;

    private String uid;
    static FriendAdapter adapter;

    private Handler handler = new Handler();
    private boolean isFirstShow = true;
    private boolean isPrepared = false;

    public static FriendFragment getInstace(String uid){
        FriendFragment friendFragment = new FriendFragment();
        Bundle bundle = new Bundle();
        bundle.putString("me",uid);
        friendFragment.setArguments(bundle);
        return friendFragment;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isFirstShow && isVisibleToUser && isPrepared){
            isFirstShow = false;
            prl.setOnRefreshListener(new PullableRelativeLayout.OnRefreshListener() {
                @Override
                public void onRefresh(PullableRelativeLayout pullToRefreshLayout) {
                    new LoadFriendTask().execute();
                }

                @Override
                public void onLoadMore(PullableRelativeLayout pullToRefreshLayout) {
                }
            });
            prl.autoRefresh();
            flist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if(!OkManager.checkNetwork(FriendFragment.this.getActivity())){
                        Toast.makeText(FriendFragment.this.getActivity(),"未连接到网络,无法获取信息",Toast.LENGTH_SHORT).show();
                    }else {
                        final int fid = adapter.getItem(position).getUid();
                        final String remark = adapter.getItem(position).getNickname();
                        final Intent intent = new Intent(FriendFragment.this.getActivity(),ShowUserInfoActivity.class);
                        intent.putExtra("me",uid);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                ProfileNetUtil profileNetUtil = new ProfileNetUtil();
                                User u = profileNetUtil.getUserByUid(
                                        UrlPath.getUserByUidUrl + fid, Integer.toString(fid));
                                Gson gson = new Gson();
                                String jsonstr = gson.toJson(u);
                                intent.putExtra("jsonstr",jsonstr);
                                intent.putExtra("remark",remark);
                                startActivityForResult(intent,1);
                            }
                        }).start();

                    }

                }
            });
            plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), SearchUserActivity.class);
                    intent.putExtra("me",uid);
                    startActivityForResult(intent, 1);
                }
            });
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab_friend, container, false);
        isPrepared = true;

        uid = getArguments().getString("me");
        prl = (PullableRelativeLayout) view.findViewById(R.id.friend_tab_pullable_layout);
        plus = (ImageView) view.findViewById(R.id.friend_tab_plus);
        flist = (ListView) view.findViewById(R.id.friend_tab_list);
        return view;
    }

    public static FriendAdapter getAdapter(){
        return adapter;
    }


    public class LoadFriendTask extends AsyncTask<Void, Void, List<Friend>>{

        @Override
        protected List<Friend> doInBackground(Void... params) {
            if(!OkManager.checkNetwork(getActivity().getApplicationContext())){
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity().getApplicationContext(),"未连接到网络",Toast.LENGTH_SHORT).show();
                    }
                });
                return null;
            }
            OkManager manager = new OkManager<>();
            List<Friend> friends = manager.getAll(UrlPath.getFriUrl+uid, Friend.class);
            return friends;
        }

        @Override
        protected void onPostExecute(List<Friend> friends) {
            if (friends == null){
                prl.refreshFinish(PullableRelativeLayout.FAIL);
                return;
            }
            if(adapter==null){
                try {
                    adapter = new FriendAdapter(FriendFragment.this.getActivity(), UrlPath.getPicUrl, FriendAdapter.LOCAL);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                flist.setAdapter(adapter);
            }else{
                adapter.clear();
            }
            for (Friend f : friends) {
                adapter.addItem(f);
            }
            adapter.notifyDataSetChanged();
            prl.refreshFinish(PullableRelativeLayout.SUCCEED);
        }
    }

}
