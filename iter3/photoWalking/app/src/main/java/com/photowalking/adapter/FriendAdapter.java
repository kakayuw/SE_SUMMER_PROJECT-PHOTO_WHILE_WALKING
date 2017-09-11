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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.photowalking.model.Friend;
import com.photowalking.utils.OkManager;
import com.photowalking.utils.UrlPath;

/**
 * Created by liujinxu on 17/7/3.
 */


public class FriendAdapter extends BaseAdapter{
    public static int LOCAL = 1;
    public static int TMP = 2;

    private ArrayList<Friend> friends = new ArrayList<Friend>();
    private static ArrayList<ViewHolder> holders = new ArrayList<>();
    private LayoutInflater inflater;
    private String ip;
    private int type;

    public FriendAdapter(Context context, String ip, int type) throws IOException {
        inflater = LayoutInflater.from(context);
        this.ip = ip;
        this.type = type;
    }

    public void addItem(Friend friend) {
        friends.add(friend);
    }

    @Override
    public int getCount() {
        return friends.size();
    }

    public Friend getItem(int position) {
        return friends.get(position);
    }

    public void clear(){
        friends.clear();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public int getPosition(int uid){
        for(int i=0; i<friends.size(); ++i){
            if(friends.get(i).getUid()==uid)
                return i;
        }
        return -1;
    }

    public void deleteItem(int position){
        friends.remove(position);
    }

    public void chgItem(int uid, String remark){
        for(int i=0; i<friends.size(); ++i){
            if(friends.get(i).getUid()==uid)
                friends.get(i).setNickname(remark);
        }
    }

    public void notifyAvatarChange(int uid){
        for(int i=0; i<friends.size(); ++i){
            if(friends.get(i).getUid()==uid){
                new setImageTask().execute(holders.get(i));
            }

        }
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.frienditem_layout, null);
            holder.textView = (TextView) convertView.findViewById(R.id.friend_item_name);
            holder.imageView = (ImageView) convertView.findViewById(R.id.friend_item_picture);
            convertView.setTag(holder);
            if(type==LOCAL)
                holders.add(position, holder);
        }else
        holder = (ViewHolder)convertView.getTag();
        holder.textView.setText(friends.get(position).getNickname());
        holder.uid = friends.get(position).getUid();

        new setImageTask().execute(holder);
        return convertView;
    }

    public class ViewHolder{
        ImageView imageView;
        TextView textView;
        int uid;
    }

    public class setImageTask extends AsyncTask<ViewHolder, Void, String>{

        private ViewHolder vholder;
        @Override
        protected String doInBackground(ViewHolder... params) {
            if(params.length == 0 || params==null)
                return null;
            vholder = params[0];
            File folder = new File(UrlPath.avatarPath);
            if(!folder.exists())
                folder.mkdir();
            String path = UrlPath.avatarPath+"/"+vholder.uid+".jpg";
            File pic = new File(path);
            if(!pic.exists()){
                try {
                    OkManager okManager = new OkManager();
                    if(!okManager.downloadFile(ip+vholder.uid, path).equals("success")){
                        return null;
                    };
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return "file://"+path;
        }

        @Override
        protected void onPostExecute(String uri) {
            if(uri==null)
                return;
            ImageLoader.getInstance().displayImage(uri, vholder.imageView);
        }
    }

}
