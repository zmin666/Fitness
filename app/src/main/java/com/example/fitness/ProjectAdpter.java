package com.example.fitness;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * @author: ZhangMin
 * @date: 2020/7/3 17:41
 * @version: 1.0
 * @desc:
 */
public class ProjectAdpter extends RecyclerView.Adapter<ProjectAdpter.ViewHolder> {

    List<SportBean> list;
    Context context;

    interface Listener {
        void clickItem(String s,int position);
    }

    Listener listener;

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public ProjectAdpter(List<SportBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_project, null);
        ViewHolder viewHolder = new ViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final String s = list.get(position).getName();
        boolean select = list.get(position).select;
        holder.text.setText(s);
        int wcolor = ContextCompat.getColor(context, R.color.colorWhite);
        int pcolor = ContextCompat.getColor(context, R.color.colorPrimary);
        holder.text.setTextColor(select ? pcolor : wcolor);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.clickItem(s,position);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView text;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.text);
        }
    }
}
