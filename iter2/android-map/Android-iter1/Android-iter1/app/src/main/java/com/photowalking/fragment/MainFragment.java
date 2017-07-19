package com.photowalking.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nikhilpanju.recyclerviewenhanced.OnActivityTouchListener;
import com.nikhilpanju.recyclerviewenhanced.RecyclerTouchListener;
import com.photowalking.R;
import com.photowalking.main.NewTraceActivity;
import com.photowalking.main.ViewDetailActivity;
import com.photowalking.model.RowModel;
import com.photowalking.model.TraceInfo;
import com.photowalking.utils.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class MainFragment extends Fragment{
    private  View view;
    FileUtil fu = new FileUtil();
    RecyclerView mRecyclerView;
    MainAdapter mAdapter;
    private RecyclerTouchListener onTouchListener;
    private OnActivityTouchListener touchListener;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab_main, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mAdapter = new MainAdapter(getActivity(), getData());
        mRecyclerView.setAdapter(mAdapter);
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
                        startActivity(someTrace);
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
                        new AlertDialog.Builder(getActivity()).setTitle("删除轨迹数据文件")
                                .setMessage("您确认要删除这条轨迹以及数据文件吗？")
                                .setPositiveButton("确定",new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        String date = mAdapter.getItem(position).getDate();
                                        String time = mAdapter.getItem(position).getBeginTime();
                                        mAdapter.deleteRow(position);
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
        FloatingActionButton fab = (FloatingActionButton)view.findViewById(R.id.main_tab_plus);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NewTraceActivity.class);
                startActivity(intent);
            }
        });
        return view;
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
        Gson gson = new Gson();
        String path = Environment.getExternalStorageDirectory().toString() + "/"
                + "bzbp" + "/" + "data" + "/"  + "trace" ;
        File rootFolder = new File(path);
        if (!rootFolder.exists()) {
            return list;
        }
        File dateList[] = rootFolder.listFiles();
        for (int i = 0; i < dateList.length; i++) {
            File datei = dateList[i];
            File moments[] = datei.listFiles();
            for (int j = 0; j < moments.length; j++) {
                File f = moments[j];
                if(f.getName().contains("info")) {
                    String infoStr = fu.fileToJson( datei.getName() + "/" + f.getName());
                    TraceInfo ti = gson.fromJson(infoStr, TraceInfo.class);
                    RowModel rm = new RowModel();
                    rm.setMile("0");
                    rm.setBeginTime(ti.getStartTime());
                    rm.setEndTime(ti.getEndTime());
                    rm.setDate(ti.getTraceDate());
                    rm.setPhotoNumb("0");
                    rm.setTraceName(ti.getTraceName());
                    rm.setTraceId(ti.getTraceDate() + "/" + ti.getFileName());
                    list.add(rm);
                }
            }
        }
        return list;
    }

    private void deleteDataFile(String date, String time) {
        String path = Environment.getExternalStorageDirectory().toString() + "/"
                + "bzbp" + "/" + "data" + "/"  + "trace" ;
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


    private class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {
        LayoutInflater inflater;
        List<RowModel> modelList;

        public MainAdapter(Context context, List<RowModel> list) {
            inflater = LayoutInflater.from(context);
            if (list == null) modelList = null;
            else modelList = new ArrayList<>(list);
        }

        @Override
        public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.recycler_row, parent, false);
            return new MainViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MainViewHolder holder, int position) {
            holder.bindData(modelList.get(position));
        }

        @Override
        public int getItemCount() {
            if (modelList == null)
                return 0;
            return modelList.size();
        }

        public RowModel getItem(int position) {
            return modelList.get(position);
        }

        public void deleteRow(int position) {
            modelList.remove(position);
        }

        class MainViewHolder extends RecyclerView.ViewHolder {

            TextView tname, tdate, tbegin, tend, tmiles, tphotos;

            public MainViewHolder(View itemView) {
                super(itemView);
                tname = (TextView) itemView.findViewById(R.id.mainText);
                tdate = (TextView) itemView.findViewById(R.id.traceDate);
                tbegin = (TextView) itemView.findViewById(R.id.traceBeginTime);
                tend = (TextView) itemView.findViewById(R.id.traceEndTime);
                tmiles = (TextView) itemView.findViewById(R.id.traceMiles);
                tphotos = (TextView) itemView.findViewById(R.id.photoNumb);
            }

            public void bindData(RowModel rowModel) {
                tname.setText(rowModel.getTraceName());
                tdate.setText(rowModel.getDate());
                tbegin.setText(rowModel.getBeginTime());
                tend.setText(rowModel.getEndTime());
                tmiles.setText(rowModel.getMile());
                tphotos.setText(rowModel.getPhotoNumb());
            }
        }
    }

}
