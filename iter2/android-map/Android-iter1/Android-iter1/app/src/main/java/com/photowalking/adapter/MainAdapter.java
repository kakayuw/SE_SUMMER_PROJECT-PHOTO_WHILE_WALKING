package com.photowalking.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.photowalking.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.photowalking.model.MainItem;

/**
 * Created by lionel on 2017/7/13.
 */

public class MainAdapter extends BaseAdapter{

    private List<MainItem> items = new ArrayList<>();
    private LayoutInflater inflater;

    public MainAdapter(Context context) throws IOException {
        inflater = LayoutInflater.from(context);
    }

    public void addItem(MainItem item) {
        items.add(item);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
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
            convertView = inflater.inflate(R.layout.main_list_item, null);
            holder.title = (TextView) convertView.findViewById(R.id.main_item_title);
            holder.stime = (TextView) convertView.findViewById(R.id.main_item_stime);
            holder.tottime = (TextView) convertView.findViewById(R.id.main_item_tottime);
            holder.miles = (TextView) convertView.findViewById(R.id.main_item_miles);
            holder.photos = (TextView) convertView.findViewById(R.id.main_item_photos);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.title.setText(items.get(position).getTitle());
        holder.stime.setText(items.get(position).getStime());
        holder.tottime.setText(items.get(position).getTotime());
        holder.miles.setText(items.get(position).getMiles());
        holder.photos.setText(items.get(position).getPhotos());
        return convertView;
    }

    public static class ViewHolder{
        private TextView title;
        private TextView stime;
        private TextView tottime;
        private TextView miles;
        private TextView photos;
    }
}
