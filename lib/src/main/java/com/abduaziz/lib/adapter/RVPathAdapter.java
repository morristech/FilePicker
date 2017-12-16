package com.abduaziz.lib.adapter;

/**
 * Created by abduaziz on 11/27/17.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.abduaziz.lib.R;

import java.io.File;
import java.util.List;

/**
 * Created by abduaziz on 11/6/17.
 */

public class RVPathAdapter extends RecyclerView.Adapter<RVPathAdapter.ViewHolder> {

    List<File> list;

    public RVPathAdapter(List<File> list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_file_path, parent, false);
        return new ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (position == 0)
            holder.textView.setText("Storage"); //<-- todo HARDCODED string
        else
            holder.textView.setText(list.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text);
        }

    }

}

