package com.photowalking.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.photowalking.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.photowalking.model.MainItem;
import com.photowalking.model.RowModel;

/**
 * Created by lionel on 2017/7/13.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {
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

    public void addItem(RowModel rowModel){
        modelList.add(rowModel);
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
