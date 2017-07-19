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

import com.photowalking.R;
import com.photowalking.model.ShareItem;

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
    private int layout;
    private ViewHolder holder;

    public ShareAdapter(Context context, String ip, int layout) {
        inflater = LayoutInflater.from(context);
        this.ip = ip;
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
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(layout, null);
            holder.textView = (TextView) convertView.findViewById(R.id.share_item_text);
            holder.imageView = (ImageView) convertView.findViewById(R.id.share_item_picture);
            holder.begin = (TextView) convertView.findViewById(R.id.share_main_item_stime);
            holder.end = (TextView) convertView.findViewById(R.id.share_main_item_tottime);
            holder.miles = (TextView) convertView.findViewById(R.id.share_main_item_miles);
            holder.photos = (TextView) convertView.findViewById(R.id.share_main_item_photos);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textView.setText(shares.get(position).getTitle());
        holder.picUrl = ip+shares.get(position).getUid();
        holder.textView.setText(shares.get(position).getStarttime());
        holder.begin.setText(shares.get(position).getEndtime());
        holder.miles.setText(shares.get(position).getUpvote());//TODO : the miles of the trace has not been added into the shareItem yet
        holder.photos.setText(shares.get(position).getPicnum());

        new setImageTask().execute(holder);
        return convertView;
    }

    public static class ViewHolder {
        public ImageView imageView;
        public TextView textView;
        public String picUrl;
        public TextView begin;
        public TextView end;
        public TextView miles;
        public TextView photos;
    }

    public class setImageTask extends AsyncTask<ViewHolder, Void, Bitmap> {

        private ShareAdapter.ViewHolder vholder;
        @Override
        protected Bitmap doInBackground(ShareAdapter.ViewHolder... params) {
            if(params.length == 0 || params==null)
                return null;
            vholder = params[0];
            String picUrl = params[0].picUrl;
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
            vholder.imageView.setImageBitmap(bitmap);
        }
    }
}
