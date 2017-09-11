package com.photowalking.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.nikhilpanju.recyclerviewenhanced.RecyclerTouchListener;
import com.photowalking.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.photowalking.adapter.MainAdapter;
import com.photowalking.listener.MainTvClickListener;
import com.photowalking.main.NewTraceActivity;
import com.photowalking.main.ViewDetailActivity;
import com.photowalking.model.Photo;
import com.photowalking.model.RowModel;
import com.photowalking.model.Trace;
import com.photowalking.model.TraceInfo;
import com.photowalking.utils.FileUtil;
import com.photowalking.utils.UrlPath;
import com.photowalking.viewUtils.MyRecyclerView;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;

/**
 * Created by lionel on 2017/7/10.
 */

public class MainFragment extends Fragment {

    private  View view;
    private FileUtil fu = new FileUtil();
    private MyRecyclerView mRecyclerView;
    public static MainAdapter mAdapter;
    private RecyclerTouchListener onTouchListener;

    private String uid;
    private String uname;
    private int reqcode;

    public static MainFragment getInstance(String uid, String uname){
        MainFragment mainFragment = new MainFragment();
        Bundle bundle = new Bundle();
        bundle.putString("me",uid);
        bundle.putString("uname", uname);
        mainFragment.setArguments(bundle);
        return mainFragment;
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab_main, container, false);

        mRecyclerView = (MyRecyclerView) view.findViewById(R.id.recyclerView);
        uid = getArguments().getString("me");
        uname  = getArguments().getString("uname");
        ImageView plus = (ImageView) view.findViewById(R.id.main_tab_plus);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NewTraceActivity.class);
                intent.putExtra("me",uid);
                intent.putExtra("uname",uname);
                startActivityForResult(intent, 1);
            }
        });
        checkPerssion();
        return view;
    }

    private void checkPerssion(){
        String[] permission = new String[2];
        reqcode = 0;
        int READ = ContextCompat.checkSelfPermission(this.getActivity(), READ_EXTERNAL_STORAGE);
        int WRITE = ContextCompat.checkSelfPermission(this.getActivity(), WRITE_EXTERNAL_STORAGE);
        if(READ != PERMISSION_GRANTED){
            reqcode |= 1;
            permission[0]=READ_EXTERNAL_STORAGE;
        }
        if(WRITE != PERMISSION_GRANTED){
            reqcode |= 2;
            permission[1]=WRITE_EXTERNAL_STORAGE;
        }
        if(reqcode>0)
            ActivityCompat.requestPermissions(this.getActivity(),permission,reqcode);
        else
            init();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==reqcode){
            if(grantResults[0]!=PERMISSION_GRANTED || grantResults[1]!=PERMISSION_GRANTED) {
                Toast.makeText(this.getActivity(),"无法访问文件及照片",Toast.LENGTH_SHORT).show();
            }else{
                init();
            }
        }
    }

    private void init(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                createDir();
                mAdapter = new MainAdapter(getActivity(), getData());
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mRecyclerView.setAdapter(mAdapter);
                    }
                });
            }
        }).start();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        onTouchListener = new RecyclerTouchListener(getActivity(), mRecyclerView);
        onTouchListener
                .setIndependentViews(R.id.rowButton)
                .setViewsToFade(R.id.rowButton)
                .setClickable(new RecyclerTouchListener.OnRowClickListener() {
                    @Override
                    public void onRowClicked(int position) {
                        Intent someTrace = new Intent(getActivity(), ViewDetailActivity.class);
                        someTrace.putExtra("date", mAdapter.getItem(position).getDate());
                        someTrace.putExtra("time", mAdapter.getItem(position).getBeginTime());
                        someTrace.putExtra("me",uid);
                        someTrace.putExtra("uname",uname);
                        someTrace.putExtra("traceid", mAdapter.getItem(position).getTraceId());
                        someTrace.putExtra("miles" , mAdapter.getItem(position).getMile());
                        startActivityForResult(someTrace, 1);
                    }

                    @Override
                    public void onIndependentViewClicked(int independentViewID, int position) {
                        Toast.makeText(getActivity(), "Row " + (position + 1) + " clicked!", Toast.LENGTH_SHORT).show();
                    }
                })
                .setLongClickable(true, new RecyclerTouchListener.OnRowLongClickListener() {
                    @Override
                    public void onRowLongClicked(int position) {
                        //ToastUtil.makeToast(getApplicationContext(), "Row " + (position + 1) + " long clicked!");
                    }
                })
                .setSwipeOptionViews(R.id.change)
                .setSwipeable(R.id.rowFG, R.id.rowBG, new RecyclerTouchListener.OnSwipeOptionsClickListener() {
                    @Override
                    public void onSwipeOptionClicked(int viewID, final int position) {
                        new AlertDialog.Builder(getActivity(),R.style.AppTheme_Light_Dialog).setTitle("删除轨迹数据文件")
                                .setMessage("您确认要删除这条轨迹以及数据文件吗？")
                                .setPositiveButton("确定",new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        String date = mAdapter.getItem(position).getDate();
                                        String time = mAdapter.getItem(position).getBeginTime();
                                        String traceid = mAdapter.getItem(position).getTraceId();
                                        new Delete().from(Photo.class).where("traceid = ?", traceid).execute();
                                        new Delete().from(Trace.class).where("traceid = ?", traceid).execute();
                                        mAdapter.deleteRow(position);
                                        mAdapter.notifyDataSetChanged();
                                        deleteDataFile(date, time);
                                        Toast.makeText(getActivity(), "轨迹已删除！", Toast.LENGTH_SHORT).show();
                                    }
                                }).setNegativeButton("取消",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).show();
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        mRecyclerView.addOnItemTouchListener(onTouchListener); }

    @Override
    public void onPause() {
        super.onPause();
        mRecyclerView.removeOnItemTouchListener(onTouchListener);
    }

    private List<RowModel> getData() {
        List<RowModel> list = new ArrayList();
//        Gson gson = new Gson();
//        String path = UrlPath.dataPath+"/trace" ;
//        File rootFolder = new File(path);
//        if (!rootFolder.exists()) {
//            return list;
//        }
//        File dateList[] = rootFolder.listFiles();
//        for (int i = 0; i < dateList.length; i++) {
//            File datei = dateList[i];
//            File moments[] = datei.listFiles();
//            for (int j = 0; j < moments.length; j++) {
//                File f = moments[j];
//                if(f.getName().contains("info")) {
//                    String infoStr = fu.fileToJson( datei.getName() + "/" + f.getName());
//                    TraceInfo ti = gson.fromJson(infoStr, TraceInfo.class);
//                    RowModel rm = new RowModel();
//                    rm.setMile("0");
//                    rm.setBeginTime(ti.getStartTime());
//                    rm.setEndTime(ti.getEndTime());
//                    rm.setDate(ti.getTraceDate());
//                    rm.setPhotoNumb("0");
//                    rm.setTraceName(ti.getTraceName());
//                    rm.setTraceId(ti.getTraceDate() + "/" + ti.getFileName());
//                    list.add(rm);
//                }
//            }
//        }
        List<Trace> tracelist =
                new Select()
                        .from(Trace.class)
                        .orderBy("traceid ASC")
                        .execute();
        for (int i = 0; i < tracelist.size(); i++) {
            RowModel rm = new RowModel();
            Trace tmp = tracelist.get(i);
            rm.setMile(Double.toString(tmp.getMiles()));
            rm.setBeginTime(tmp.getStarttime());
            rm.setEndTime(tmp.getEndtime());
            rm.setDate(tmp.getTracedate());
            rm.setPhotoNumb(Integer.toString(tmp.getPhotonumb()));
            rm.setTraceName(tmp.getTracename());
            rm.setTraceId(tmp.getTraceid());
            list.add(rm);

        }
        return list;
    }

    private void createDir(){
        File appFolder = new File(UrlPath.APP_PATH);
        if(!appFolder.exists())
            appFolder.mkdir();
        File dataFolder = new File(UrlPath.dataPath);
        if(!dataFolder.exists())
            dataFolder.mkdir();
        File avatarFolder = new File(UrlPath.avatarPath);
        if(!avatarFolder.exists())
            avatarFolder.mkdir();
    }

    private void deleteDataFile(String date, String time) {
        String path = UrlPath.dataPath+ "/trace" ;
        File line = new File(path + "/" + date + "/" + time);
        if (line.exists() && line.isFile())
            line.delete();
        File data = new File(path + "/" + date + "/info_" + time);
        if (data.exists() && data.isFile())
            data.delete();
        File folder = new File(path + "/" + date);
        if (folder.listFiles().length == 0)
            folder.delete();
    }

}
