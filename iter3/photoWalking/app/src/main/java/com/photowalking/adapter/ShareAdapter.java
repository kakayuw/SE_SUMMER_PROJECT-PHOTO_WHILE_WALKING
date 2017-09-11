package com.photowalking.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.photowalking.R;
import com.photowalking.model.ShareItem;
import com.photowalking.share.AllCommentActivity;
import com.photowalking.utils.OkManager;
import com.photowalking.utils.UrlPath;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by lionel on 2017/7/7.
 */

public class ShareAdapter extends BaseAdapter {

    private ArrayList<ShareItem> shares = new ArrayList<ShareItem>();
    private LayoutInflater inflater;
    private String ip;
    private String uid;
    private String uname;
    private int layout;
    private ViewHolder holder;
    private Activity activity;
    private OkManager okManager = new OkManager();

    public ShareAdapter(Activity activity, String ip, String uid, String uname, int layout) {
        this.activity = activity;
        inflater = LayoutInflater.from(activity);
        this.ip = ip;
        this.uid = uid;
        this.uname = uname;
        this.layout = layout;

    }

    public void addItem(ShareItem shareItem){
        shares.add(shareItem);
    }

    @Override
    public int getCount() {
        return shares.size();
    }

    @Override
    public ShareItem getItem(int position) {
        return shares.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void clear(){
        shares.clear();
    }

    public void addComment(String sid, int comm){
        int size = shares.size();
        for(int i = 0; i < size; ++ i){
            if(shares.get(i).getSid().equals(sid)){
                shares.get(i).setComment(comm);
                this.notifyDataSetChanged();
            }
        }
    }

    public void delComment(String sid, int comm){
        int size = shares.size();
        for(int i = 0; i < size; ++ i){
            if(shares.get(i).getSid().equals(sid)){
                shares.get(i).setComment(comm);
                this.notifyDataSetChanged();
            }
        }
    }

    public void notifyBrowse(int position){
        int times = shares.get(position).getBrowse();
        shares.get(position).setBrowse(times+1);
        this.notifyDataSetChanged();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(layout, null);
            init(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.uname.setText(shares.get(position).getUsername());
        holder.textView.setText(shares.get(position).getTitle());
        holder.begin.setText(shares.get(position).getStarttime());
        holder.end.setText(shares.get(position).getEndtime());
        holder.miles.setText("1");//TODO : the miles of the trace has not been added into the shareItem yet
        if(shares.get(position).getPoem() !=  null ){
            holder.poem.setText(shares.get(position).getPoem());
            holder.poem.setVisibility(View.VISIBLE);
        }
        holder.photos.setText(String.valueOf(shares.get(position).getPicnum()));
        holder.comments.setText(String.valueOf(shares.get(position).getComment()));
        holder.browse.setText(String.valueOf(shares.get(position).getBrowse()));
        if(shares.get(position).getLove().equals("exist")){
            holder.love = 1;
            holder.ll_love.setBackgroundResource(R.drawable.loved);
        }
        final String sid = shares.get(position).getSid();

        holder.ll_love.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View view = v;
                if(holder.love==0){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            final String msg = okManager.sendStringByGet(UrlPath.upVoteUrl+sid+"/"+uid);
                            if(msg.equals("success") || msg.equals("duplicate")){
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        view.setBackgroundResource(R.drawable.loved);
                                        if(msg.equals("duplicate"))
                                            Toast.makeText(activity,"您已点过赞",Toast.LENGTH_SHORT).show();
                                    }
                                });
                                holder.love = 1;
                            }else{
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(activity,"点赞失败",Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    }).start();
                }else{
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String msg = okManager.sendStringByGet(UrlPath.cancelUpVoteUrl+sid+"/"+uid);
                            if(msg.equals("success")){
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        view.setBackgroundResource(R.drawable.love);
                                        holder.love = 0;
                                    }
                                });
                            }else{
                                Toast.makeText(activity,"取消点赞失败",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).start();
                }
            }
        });

        final int num = shares.get(position).getComment();
        holder.ll_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, AllCommentActivity.class);
                intent.putExtra("uid",uid);
                intent.putExtra("uname",uname);
                intent.putExtra("sid",sid);
                intent.putExtra("num",num);
                activity.startActivityForResult(intent,1);
            }
        });

        ImageLoader.getInstance().displayImage(ip+shares.get(position).getUid(), holder.imageView);
//        new SetImageTask().execute(new ImageHolder(ip+shares.get(position).getUid(), holder.imageView));
        return convertView;
    }

    private void init(View convertView){
        holder.uname = (TextView) convertView.findViewById(R.id.share_item_uname);
        holder.textView = (TextView) convertView.findViewById(R.id.share_item_text);
        holder.imageView = (ImageView) convertView.findViewById(R.id.share_item_picture);
        holder.begin = (TextView) convertView.findViewById(R.id.share_main_item_stime);
        holder.end = (TextView) convertView.findViewById(R.id.share_main_item_tottime);
        holder.miles = (TextView) convertView.findViewById(R.id.share_main_item_miles);
        holder.photos = (TextView) convertView.findViewById(R.id.share_main_item_photos);
        holder.poem = (TextView) convertView.findViewById(R.id.share_item_poem);
        holder.ll_love = (LinearLayout) convertView.findViewById(R.id.share_item_love);
        holder.comments = (TextView) convertView.findViewById(R.id.share_item_comment_num);
        holder.ll_comment = (LinearLayout) convertView.findViewById(R.id.share_item_comment);
        holder.browse = (TextView) convertView.findViewById(R.id.share_item_browse);
        holder.love = 0;
    }

    public static class ViewHolder {
        public ImageView imageView;
        public TextView uname;
        public TextView textView;
        public TextView begin;
        public TextView end;
        public TextView miles;
        public TextView photos;
        public TextView poem;
        public TextView comments;
        public TextView browse;
        public LinearLayout ll_love;
        public LinearLayout ll_comment;

        public int love;
    }
}
