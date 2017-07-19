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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import com.photowalking.model.Friend;

/**
 * Created by liujinxu on 17/7/3.
 */


public class FriendAdapter extends BaseAdapter {
    private ArrayList<Friend> friends = new ArrayList<Friend>();
    private LayoutInflater inflater;
    private String ip;

    public FriendAdapter(Context context, String ip) throws IOException {
        inflater = LayoutInflater.from(context);
        this.ip = ip;
    }

    public void addItem(Friend friend) {
        friends.add(friend);
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
        holder.textView.setText(friends.get(position).getNickname());
        holder.picUrl = ip+friends.get(position).getUid();
        new setImageTask().execute(holder);
        return convertView;
    }

    public static byte[] getBytes(InputStream is) throws IOException {
        ByteArrayOutputStream outstream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;
        while ((len = is.read(buffer)) != -1) {
            outstream.write(buffer, 0, len);
        }
        outstream.close();
        return outstream.toByteArray();
    }

    public static class ViewHolder {
        public ImageView imageView;
        public TextView textView;
        public String picUrl;
    }

    public class setImageTask extends AsyncTask<ViewHolder, Void, Bitmap>{

        private ViewHolder vholder;
        @Override
        protected Bitmap doInBackground(ViewHolder... params) {
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
