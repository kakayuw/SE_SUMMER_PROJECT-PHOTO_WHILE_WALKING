package com.photowalking.fragment;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.photowalking.R;

import java.io.IOException;
import java.util.List;

import com.photowalking.adapter.FriendAdapter;
import com.photowalking.model.Friend;
import com.photowalking.utils.OkManager;
import com.photowalking.utils.UrlPath;

/**
 * Created by lionel on 2017/7/11.
 */

public class FriendFragment extends Fragment{

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab_friend, container, false);
        new LoadFriendTask().execute(UrlPath.getFriUrl);
        return view;
    }

    public class LoadFriendTask extends AsyncTask<String, Void, List<Friend>>{

        @Override
        protected List<Friend> doInBackground(String... params) {
            if (params.length == 0) {
                return null;
            }
            String friUrl = params[0];
            OkManager manager = new OkManager<Friend>();
            List<Friend> friends= manager.getAll(friUrl, Friend.class);
            return friends;
        }

        @Override
        protected void onPostExecute(List<Friend> friends) {
            if (friends != null) {
                FriendAdapter adapter = null;
                try {
                    adapter = new FriendAdapter(FriendFragment.this.getActivity(), UrlPath.getPicUrl);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                for (Friend f : friends) {
                    adapter.addItem(f);
                }
                ListView flist = (ListView) view.findViewById(R.id.friend_tab_list);
                flist.setAdapter(adapter);
            }
        }
    }

}
