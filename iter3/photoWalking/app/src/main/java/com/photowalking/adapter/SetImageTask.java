package com.photowalking.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by lionel on 2017/8/11.
 */

public class SetImageTask extends AsyncTask<ImageHolder, Void, Bitmap> {

    private ImageView imageView;

    @Override
    protected Bitmap doInBackground(ImageHolder... params) {
        if(params.length == 0 || params==null)
            return null;
        String picUrl = params[0].url;
        imageView = params[0].imageView;
        Bitmap bmp = null;
        try {
            URL url = new URL(picUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            InputStream is = conn.getInputStream();
            bmp = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmp;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }
}
