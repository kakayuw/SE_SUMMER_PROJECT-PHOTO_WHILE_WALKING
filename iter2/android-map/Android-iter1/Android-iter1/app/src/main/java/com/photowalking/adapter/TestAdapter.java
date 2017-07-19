package com.photowalking.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.photowalking.R;

import java.io.IOException;
import java.util.ArrayList;

import com.photowalking.model.SendBytes;

/**
 * Created by lionel on 2017/7/12.
 */

public class TestAdapter extends BaseAdapter{

    private ArrayList<SendBytes> friends = new ArrayList<SendBytes>();
    private LayoutInflater inflater;
    private String ip;

    public TestAdapter(Context context) throws IOException {
        inflater = LayoutInflater.from(context);
    }

    public void addItem(SendBytes sendBytes) {
        friends.add(sendBytes);
    }

    @Override
    public int getCount() {
        return friends.size();
    }

    @Override
    public Object getItem(int position) {
        return friends.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.adapter_layout, null);
            holder.textView = (TextView) convertView.findViewById(R.id.share_item_text);
            holder.imageView = (ImageView) convertView.findViewById(R.id.share_item_picture);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textView.setText(friends.get(position).getPicname());

        byte[] bytes = friends.get(position).getPic();
        try {
           Log.v("get bytes : ", new String(bytes));

            Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            holder.imageView.setImageBitmap(bmp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }

    public static class ViewHolder {
        public ImageView imageView;
        public TextView textView;
    }



}
