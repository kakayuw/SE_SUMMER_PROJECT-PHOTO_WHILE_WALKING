package com.photowalking.listener;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.photowalking.R;
import com.photowalking.adapter.ShareAdapter;
import com.photowalking.fragment.ContFragment;
import com.photowalking.model.ShareItem;
import com.photowalking.share.ShareHolder;
import com.photowalking.share.ViewOthersDetailActivity;
import com.photowalking.utils.OkManager;
import com.photowalking.utils.UrlPath;
import com.photowalking.viewUtils.PullableRelativeLayout;

import java.util.List;

/**
 * Created by lionel on 2017/7/21.
 */

public class ShareRefreshListener implements PullableRelativeLayout.OnRefreshListener {

    private Context context;
    private ShareHolder shareHolder;
    private ShareAdapter shareAdapter;
    private Handler handler = new Handler();
    private boolean flag;

    public ShareRefreshListener(Context context, ShareHolder shareHolder){
        this.context = context;
        this.shareHolder = shareHolder;
    }

    @Override
    public void onRefresh(final PullableRelativeLayout prl)
    {
        shareHolder.pagenum=1;
        flag = true;
        new RefreshShareTask().execute(prl);
    }

    @Override
    public void onLoadMore(final PullableRelativeLayout prl)
    {
        flag = false;
        new RefreshShareTask().execute(prl);
    }

    private class RefreshShareTask extends AsyncTask<PullableRelativeLayout,Void,List<ShareItem>>{
        private PullableRelativeLayout prl;
        @Override
        protected List<ShareItem> doInBackground(PullableRelativeLayout... params) {
            String url = shareHolder.url;
            if(params.length == 0)return null;
            prl = params[0];
            if(!OkManager.checkNetwork(context)){
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context,"未连接到网络",Toast.LENGTH_SHORT).show();
                    }
                });
                return null;
            }
            OkManager manager = new OkManager<>();
            Log.e(">>>>>>>","send url: "+url+"/"+shareHolder.pagenum);
            List<ShareItem> shares= manager.getAll(url+"/"+shareHolder.pagenum, ShareItem.class);
            shareHolder.pagenum++;
            return shares;
        }

        @Override
        protected void onPostExecute(List<ShareItem> shareItems) {
            if(shareItems==null){
                if (flag) prl.refreshFinish(PullableRelativeLayout.FAIL);
                else prl.loadmoreFinish(PullableRelativeLayout.FAIL);
                return;
            }
            shareAdapter = (ShareAdapter) shareHolder.listView.getAdapter();
            if(shareAdapter == null){
                shareAdapter = new ShareAdapter(shareHolder.activity, UrlPath.getPicUrl, shareHolder.uid,
                    shareHolder.uname, R.layout.shareitem_layout);
                shareHolder.listView.setAdapter(shareAdapter);
            }
            if(flag)shareAdapter.clear();
            for(ShareItem s : shareItems){
                shareAdapter.addItem(s);
            }
            shareAdapter.notifyDataSetChanged();
            shareHolder.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    final String sid = shareAdapter.getItem(position).getSid();
                    final int picnum = shareAdapter.getItem(position).getPicnum();
                    shareAdapter.notifyBrowse(position);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            OkManager okManager = new OkManager();
                            String result = okManager.sendStringByGet(UrlPath.addBrowseUrl+sid);
//                            while(!result.equals("success")){
//                                result = okManager.sendStringByGet(UrlPath.addBrowseUrl+sid);
//                            }
                        }
                    }).start();

                    Intent intent = new Intent(shareHolder.activity, ViewOthersDetailActivity.class);
                    intent.putExtra("sid",sid);
                    intent.putExtra("me",shareHolder.uid);
                    intent.putExtra("picnum",picnum);
                    shareHolder.activity.startActivityForResult(intent,0);
                }
            });
            if (flag) prl.refreshFinish(PullableRelativeLayout.SUCCEED);
            else prl.loadmoreFinish(PullableRelativeLayout.SUCCEED);
        }
    }

}
