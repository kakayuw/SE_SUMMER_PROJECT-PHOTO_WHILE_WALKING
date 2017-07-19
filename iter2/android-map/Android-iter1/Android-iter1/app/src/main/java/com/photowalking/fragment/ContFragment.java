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

import java.util.List;

import com.photowalking.adapter.ShareAdapter;
import com.photowalking.model.ShareItem;
import com.photowalking.utils.OkManager;
import com.photowalking.utils.UrlPath;
import com.photowalking.viewUtils.XListView;

/**
 * Created by lionel on 2017/7/10.
 */

public class ContFragment extends Fragment implements XListView.XListViewListener{

    private View view;

    public static ContFragment getInstance(int res){
        ContFragment fragment = new ContFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("res", res);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        int res = getArguments().getInt("res");
        view = inflater.inflate(res, container, false);
        String[] params = new String[2];
        switch(res){
            case R.layout.share_fri:
                params[0] = UrlPath.getShareFriUrl;
                params[1] = String.valueOf(R.id.fri_share_list);
                new LoadShareTask().execute(params);
                break;
            case R.layout.share_all:
                params[0] = UrlPath.getShareAllUrl;
                params[1] = String.valueOf(R.id.all_share_list);
                new LoadShareTask().execute(params);
                break;
            case R.layout.share_mine:
                params[0] = UrlPath.getShareMineUrl;
                params[1] = String.valueOf(R.id.my_share_list);
                new LoadShareTask().execute(params);
                break;
            default: break;
        }
        return view;
    }



    public class LoadShareTask extends AsyncTask<String, Void, List<ShareItem>> {
        private int listId;

        @Override
        protected List<ShareItem> doInBackground(String... params) {
            if (params.length == 0) {
                return null;
            }
            String url = params[0];
            listId = Integer.parseInt(params[1]);
            OkManager manager = new OkManager<ShareItem>();
            List<ShareItem> shares= manager.getAll(url, ShareItem.class);
            return shares;
        }

        @Override
        protected void onPostExecute(List<ShareItem> shareItems) {
            if(shareItems != null){
                ShareAdapter shareAdapter = new ShareAdapter(ContFragment.this.getActivity(), UrlPath.getPicUrl,
                        R.layout.adapter_layout);
                for(ShareItem s : shareItems){
                    shareAdapter.addItem(s);
                }
                ListView listView = (ListView) view.findViewById(listId);
                listView.setAdapter(shareAdapter);
            }
        }
    }

    @Override
    public void onRefresh() {
        System.out.println("asfd");
    }

    @Override
    public void onLoadMore() {
        System.out.println("asfd");
    }
}
