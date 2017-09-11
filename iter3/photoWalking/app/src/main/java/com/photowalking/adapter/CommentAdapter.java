package com.photowalking.adapter;

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

import com.google.gson.Gson;
import com.nostra13.universalimageloader.utils.L;
import com.photowalking.R;
import com.photowalking.friends.AddFriendActivity;
import com.photowalking.friends.ShowUserInfoActivity;
import com.photowalking.model.Comment;
import com.photowalking.model.Friend;
import com.photowalking.model.User;
import com.photowalking.utils.ProfileNetUtil;
import com.photowalking.utils.UrlPath;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by liujinxu on 17/8/11.
 */

public class CommentAdapter extends BaseAdapter {
    private ArrayList<Comment> comm = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;

    public CommentAdapter(Context context) throws IOException {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void addItem(Comment comment) {
        comm.add(comment);
    }

    @Override
    public int getCount() {
        return comm.size();
    }

    public Comment getItem(int position) {
        return comm.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void delItem(int position){
        comm.remove(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        com.photowalking.adapter.CommentAdapter.ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.share_commentitem_layout, null);
            holder.tv_id1 = (TextView) convertView.findViewById(R.id.comment_item_id1);
            holder.ll_reply = (LinearLayout) convertView.findViewById(R.id.comment_item_reply);
            holder.tv_id2 = (TextView) convertView.findViewById(R.id.comment_item_id2);
            holder.tv_words = (TextView) convertView.findViewById(R.id.comment_item_words);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();
        holder.tv_id1.setText(comm.get(position).getUnamea());
        int pre_uid2 = 0;
        if(comm.get(position).getUnameb() == null) {
            holder.ll_reply.setVisibility(View.GONE);
        } else {
            holder.ll_reply.setVisibility(View.VISIBLE);
            holder.tv_id2.setText(comm.get(position).getUnameb());
            pre_uid2 = comm.get(position).getUidb();
        }
        holder.tv_words.setText(comm.get(position).getComment());

        final int uid1 = comm.get(position).getUida();
        final int uid2 = pre_uid2;

        holder.tv_id1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUserInfo(uid1);}
        });

        holder.tv_id2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUserInfo(uid2);
            }
        });

        return convertView;
    }

    public class ViewHolder {
        TextView tv_id1;
        LinearLayout ll_reply;
        TextView tv_id2;
        TextView tv_words;
    }

    private void showUserInfo(final int uid){
        new Thread(new Runnable() {
            @Override
            public void run() {
                ProfileNetUtil profileNetUtil = new ProfileNetUtil();
                User u = profileNetUtil.getUserByUid(UrlPath.getUserByUidUrl + uid, String.valueOf(uid));
                if(u!=null){
                    Gson g = new Gson();
                    String jsonstr = g.toJson(u);
                    final Intent intent = new Intent(context,ShowUserInfoActivity.class);
                    intent.putExtra("jsonstr",jsonstr);
                    context.startActivity(intent);
                }
            }
        }).start();
    }

}
