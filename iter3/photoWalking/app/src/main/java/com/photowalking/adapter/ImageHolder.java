package com.photowalking.adapter;

import android.widget.ImageView;

/**
 * Created by lionel on 2017/8/11.
 */

public class ImageHolder {
    public String url;
    public ImageView imageView;

    public ImageHolder(String url, ImageView imageView){
        this.url = url;
        this.imageView = imageView;
    }
}
