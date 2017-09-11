package com.photowalking.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nikhilpanju.recyclerviewenhanced.RecyclerTouchListener;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.photowalking.MainActivity;
import com.photowalking.R;

import java.io.File;

import com.photowalking.adapter.MeAdapter;
import com.photowalking.listener.MainTvClickListener;
import com.photowalking.profile.ProfileAboutActivity;
import com.photowalking.profile.ProfileAvatarActivity;
import com.photowalking.profile.ProfileChargeActivity;
import com.photowalking.profile.ProfileEditActivity;
import com.photowalking.profile.ProfilePhoneActivity;
import com.photowalking.profile.ProfilePwdActivity;
import com.photowalking.utils.BitmapUtil;
import com.photowalking.utils.OkManager;
import com.photowalking.utils.UrlPath;
import com.photowalking.utils.UserInfoSharedPreference;
import com.photowalking.viewUtils.MyItemDecoration;
import com.photowalking.viewUtils.MyRecyclerView;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by lionel on 2017/7/11.
 */

public class MineFragment extends Fragment {
    private View view;
    private static ImageView photo;
    private TextView tv;

    LinearLayout logout_layout;

    MyRecyclerView mRecyclerView;
    private RecyclerTouchListener onTouchListener;

    private String uid;

    private boolean isFirstShow = true;
    private boolean isPrepared = false;
    private boolean flag = false;

    public static MineFragment getInstance(String uid){
        MineFragment mineFragment = new MineFragment();
        Bundle bundle = new Bundle();
        bundle.putString("me",uid);
        mineFragment.setArguments(bundle);
        return mineFragment;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isFirstShow && isVisibleToUser && isPrepared){
            isFirstShow = false;
            new ShowPhotoTask().execute(uid);
            MeAdapter adapter = new MeAdapter(getActivity());
            adapter.addItem(R.drawable.edit,"编辑资料","N");
            adapter.addItem(R.drawable.pwd,"修改密码","A");
            adapter.addItem(R.drawable.phone,"修改手机号码","ï");
            adapter.addItem(R.drawable.charge,"充值","V");
            adapter.addItem(R.drawable.about,"关于","E");
            mRecyclerView.setAdapter(adapter);
            onTouchListener = new RecyclerTouchListener(getActivity(), mRecyclerView);
            onTouchListener
                    .setClickable(new RecyclerTouchListener.OnRowClickListener() {
                        @Override
                        public void onRowClicked(int position) {
                            Intent intent = null;
                            switch (position){
                                case 0:
                                    intent = new Intent(getActivity(),  ProfileEditActivity.class);
                                    break;
                                case 1:
                                    intent = new Intent(getActivity(), ProfilePwdActivity.class);
                                    break;
                                case 2:
                                    intent = new Intent(getActivity(),  ProfilePhoneActivity.class);
                                    break;
                                case 3:
                                    intent = new Intent(getActivity(), ProfileChargeActivity.class);
                                    break;
                                case 4:
                                    intent = new Intent(getActivity(), ProfileAboutActivity.class);
                                    break;
                                default:break;
                            }
                            if(intent!=null){
                                intent.putExtra("me",uid);
                                startActivityForResult(intent, 1);
                                transition();
                            }
                        }

                        @Override
                        public void onIndependentViewClicked(int independentViewID, int position) {

                        }
                    })
                    .setSwipeOptionViews(R.id.me_row_hide)
                    .setSwipeable(R.id.me_row_fg, R.id.me_row_bg, new RecyclerTouchListener.OnSwipeOptionsClickListener() {
                        @Override
                        public void onSwipeOptionClicked(int viewID, int position) {
                            switch (position){
                                case 0:
                                    tv.setTextColor(Color.BLUE);break;
                                case 1:
                                    tv.setTextColor(Color.BLACK);break;
                                case 2:
                                    tv.setTextColor(Color.WHITE);break;
                                case 3:
                                    tv.setTextColor(Color.YELLOW);break;
                                case 4:
                                    tv.setTextColor(Color.GREEN);break;
                                default:break;
                            }
                        }
                    });

            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            mRecyclerView.addOnItemTouchListener(onTouchListener);

            photo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(flag==true){
                        Intent intent = new Intent(getActivity(), ProfileAvatarActivity.class);
                        intent.putExtra("me",uid);
                        startActivityForResult(intent, 1);
                    }else{
                        photo.setImageBitmap(null);
                        new ShowPhotoTask().execute(uid);
                    }
                }
            });

            logout_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(MineFragment.this.getActivity(), R.style.AppTheme_Light_Dialog)
                            .setMessage("确定退出当前账号吗？")
                            .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    JPushInterface.deleteAlias(MineFragment.this.getActivity(),1);
                                    JPushInterface.stopPush(MineFragment.this.getActivity());
                                    UserInfoSharedPreference.delete(MineFragment.this.getActivity());
                                    Intent intent = new Intent(getActivity(), MainActivity.class);
                                    startActivityForResult(intent, 1);
                                    dialog.dismiss();
                                    MineFragment.this.getActivity().finish();
                                }
                            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
                }
            });
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab_me, container, false);
        uid = getArguments().getString("me");
        isPrepared = true;

        photo = (ImageView) view.findViewById(R.id.photo);
        logout_layout = (LinearLayout) view.findViewById(R.id.tab_me_logout);
        mRecyclerView = (MyRecyclerView)view.findViewById(R.id.tab_me_menu);
        mRecyclerView.addItemDecoration(new MyItemDecoration());
        tv = (TextView)view.findViewById(R.id.tab_me_tv);
        tv.setOnClickListener(new MainTvClickListener(getActivity()));

        return view;
    }

    private void transition(){
        MineFragment.this.getActivity().overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
    }

    public static void notifyPhotoUpdate(String uri){
        ImageLoader.getInstance().displayImage(uri, photo);
    }

    private class ShowPhotoTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {
            if(params.length == 0)
                return null;
            File parent = new File(UrlPath.APP_PATH);
            if(!parent.exists())
                parent.mkdir();
            File folder = new File(UrlPath.avatarPath);
            if(!folder.exists())
                folder.mkdir();
            String path = UrlPath.avatarPath+"/"+uid+".jpg";
            File pic = new File(path);
            if(!pic.exists()){
                try {
                    OkManager okManager = new OkManager();
                    if(!okManager.downloadFile(UrlPath.getPicUrl+params[0],path).equals("success")){
                        return null;
                    };
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return path;
        }

        @Override
        protected void onPostExecute(String uri) {
            if(uri==null){
                photo.setImageResource(R.drawable.avatar_fail);
                flag = false;
                return;
            }
            flag = true;
            Bitmap bmp = BitmapUtil.decodeSampledBitmapFromFd(uri,100,100);
//            ImageLoader.getInstance().displayImage(uri, photo);
            photo.setImageBitmap(bmp);
        }
    }
}
