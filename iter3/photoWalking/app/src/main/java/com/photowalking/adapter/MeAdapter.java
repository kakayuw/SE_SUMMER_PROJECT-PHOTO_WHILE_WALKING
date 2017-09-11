package com.photowalking.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.photowalking.R;
import com.photowalking.model.RowModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lionel on 2017/8/24.
 */

public class MeAdapter extends RecyclerView.Adapter<MeAdapter.Holder> {
    LayoutInflater inflater;
    List<RowRes> modelList = new ArrayList<>();

    public MeAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.me_row, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.bindData(modelList.get(position));
    }

    @Override
    public int getItemCount() {
        if (modelList == null)
            return 0;
        return modelList.size();
    }

    public void addItem(int res, String text, String hiddenText){
        modelList.add(new RowRes(res, text, hiddenText));
    }

    public RowRes getItem(int position) {
        return modelList.get(position);
    }


    public class Holder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView tv_show;
        TextView tv_hidden;

        public Holder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.me_row_img);
            tv_show = (TextView) itemView.findViewById(R.id.me_row_text);
            tv_hidden = (TextView) itemView.findViewById(R.id.me_row_hidden_text);
        }

        public void bindData(RowRes rowRes) {
            img.setImageResource(rowRes.res);
            tv_show.setText(rowRes.text);
            tv_hidden.setText(rowRes.hiddenText);
        }
    }

    public class RowRes{
        int res;
        String text;
        String hiddenText;

        public RowRes(int res, String text, String hiddenText){
            this.res = res;
            this.text = text;
            this.hiddenText = hiddenText;
        }
    }
}
