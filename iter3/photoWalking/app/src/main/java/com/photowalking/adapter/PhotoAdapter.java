package com.photowalking.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.photowalking.R;

import java.util.ArrayList;
import java.util.List;

import com.photowalking.utils.UrlPath;

import static com.nostra13.universalimageloader.core.assist.ImageScaleType.EXACTLY;

/**
 * Created by lionel on 2017/7/14.
 */

public class PhotoAdapter extends BaseAdapter {

    private List<String> photos = new ArrayList<>();
    private LayoutInflater inflater;
    private DisplayImageOptions options;


    public PhotoAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        options = new DisplayImageOptions.Builder()
//                .showImageOnLoading(R.drawable.ic_stub)
//                .showImageForEmptyUri(R.drawable.ic_empty)
//                .showImageOnFail(R.drawable.ic_error)
                .cacheInMemory(true)
                .considerExifParams(true)
                .displayer(new FadeInBitmapDisplayer(100))
                .imageScaleType(EXACTLY)
                .build();
    }


    public void addItem(String str) {
        photos.add(str);
    }

    @Override
    public int getCount() {
        return photos.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public String getItem(int position) {
        return photos.get(position);
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.main_detail_photo, null);
            holder.img = (ImageView) convertView.findViewById(R.id.main_detail_picture);
            convertView.setTag(holder);
        }else
            holder = (ViewHolder)convertView.getTag();
        ImageLoader.getInstance().displayImage("file://"+photos.get(position), holder.img, options);

        return convertView;
    }

    static class ViewHolder{
        public ImageView img;
    }

}

