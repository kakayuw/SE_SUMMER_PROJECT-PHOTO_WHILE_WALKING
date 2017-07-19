package com.photowalking.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.photowalking.LoginActivity;
import com.photowalking.R;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.photowalking.profile.ProfileAboutActivity;
import com.photowalking.profile.ProfileChargeActivity;
import com.photowalking.profile.ProfileEditActivity;
import com.photowalking.profile.ProfilePwdActivity;
import com.photowalking.utils.UrlPath;

/**
 * Created by lionel on 2017/7/11.
 */

public class MineFragment extends Fragment{
    private View view;
    private ImageView photo;

    LinearLayout about_layout;
    LinearLayout edit_layout;
    LinearLayout charge_layout;
    LinearLayout logout_layout;
    LinearLayout pwd_layout;

//    ViewGroup about_layout;
//    ViewGroup edit_layout;
//    ViewGroup charge_layout;
//    ViewGroup logout_layout;
//    ViewGroup pwd_layout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab_me, container, false);
        doThings();
        return view;
    }

    public void doThings() {
        Intent intent = getActivity().getIntent();
        final String me = intent.getStringExtra("me"); //"1";
        photo = (ImageView) view.findViewById(R.id.photo);
        new ShowPhotoTask().execute(me);

        about_layout = (LinearLayout) view.findViewById(R.id.tab_me_about_bzbp);
        edit_layout = (LinearLayout) view.findViewById(R.id.tab_me_edit_info);
        charge_layout = (LinearLayout) view.findViewById(R.id.tab_me_charge);
        logout_layout = (LinearLayout) view.findViewById(R.id.tab_me_logout);
        pwd_layout = (LinearLayout) view.findViewById(R.id.tab_me_modify_pwd);

//        about_layout.setFocusable(true);
//        about_layout.setFocusableInTouchMode(true);
        about_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getContext(),"about", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getActivity(), ProfileAboutActivity.class);
                intent.putExtra("me",me);
                startActivityForResult(intent, 1);
            }
        });

//        charge_layout.setFocusable(true);
//        charge_layout.setFocusableInTouchMode(true);
        charge_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProfileChargeActivity.class);
                intent.putExtra("me",me);
                startActivityForResult(intent, 1);
            }
        });

//        pwd_layout.setFocusable(true);
//        pwd_layout.setFocusableInTouchMode(true);
        pwd_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProfilePwdActivity.class);
                intent.putExtra("me",me);
                startActivityForResult(intent, 1);
            }
        });

//        edit_layout.setFocusable(true);
//        edit_layout.setFocusableInTouchMode(true);
        edit_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),  ProfileEditActivity.class);
                intent.putExtra("me",me);
                startActivityForResult(intent, 1);

            }
        });

//        logout_layout.setFocusable(true);
//        logout_layout.setFocusableInTouchMode(true);
        logout_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivityForResult(intent, 1);
            }
        });
    }

    private class ShowPhotoTask extends AsyncTask<String, Void, Bitmap>{

        @Override
        protected Bitmap doInBackground(String... params) {
            if(params.length == 0)
                return null;
            Bitmap bmp = null;
            try {
                URL url = new URL(UrlPath.getPicUrl+params[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                InputStream is = conn.getInputStream();
                bmp = BitmapFactory.decodeStream(is);
                is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bmp;
        }

        @Override
        protected void onPostExecute(Bitmap bmp) {
            photo.setImageBitmap(bmp);
        }
    }
}
