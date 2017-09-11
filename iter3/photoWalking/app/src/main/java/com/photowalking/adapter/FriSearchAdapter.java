package com.photowalking.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.photowalking.R;
import com.photowalking.model.Friend;
import com.photowalking.model.User;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by lionel on 2017/7/29.
 */

public class FriSearchAdapter extends BaseAdapter{
    private ArrayList<User> users = new ArrayList<>();
    private LayoutInflater inflater;
    private String ip;

    public FriSearchAdapter(Context context, String ip) throws IOException {
        inflater = LayoutInflater.from(context);
        this.ip = ip;
    }

    public void addItem(User user) {
        users.add(user);
    }

    @Override
    public int getCount() {
        return users.size();
    }

    public User getItem(int position) {
        return users.get(position);
    }

    public void clear(){
        users.clear();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.friend_searchitem_layout, null);
            holder.id = (TextView) convertView.findViewById(R.id.search_item_id);
            holder.name = (TextView) convertView.findViewById(R.id.search_item_name);
            holder.imageView = (ImageView) convertView.findViewById(R.id.search_item_picture);
            convertView.setTag(holder);
        }else
            holder = (ViewHolder)convertView.getTag();
        holder.name.setText(users.get(position).getUsername());
        holder.id.setText("("+users.get(position).getId()+")");
        ImageLoader.getInstance().displayImage(ip+users.get(position).getId(), holder.imageView);
//        new SetImageTask().execute(new ImageHolder(ip+users.get(position).getId(), holder.imageView));
        return convertView;
    }

    public class ViewHolder{
        ImageView imageView;
        TextView name;
        TextView id;
    }
}
