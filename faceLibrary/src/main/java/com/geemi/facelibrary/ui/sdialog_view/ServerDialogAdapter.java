package com.geemi.facelibrary.ui.sdialog_view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.geemi.facelibrary.R;

import java.util.List;


/**
 * Created by Thisfeng on 2017/3/9 0009 21:13
 */

public class ServerDialogAdapter extends RecyclerView.Adapter<ServerDialogAdapter.ViewHolder> {

    /**
     * 数据源
     */
    private List<String> datas;

    /**
     * 回调
     */
    private OnItemClickListener onItemClickListener;

    /**
     * @param datas    数据源
     * @param listener item 点击回调
     */
    public ServerDialogAdapter(List<String> datas, OnItemClickListener listener) {
        this.datas = datas;
        this.onItemClickListener = listener;
        notifyDataSetChanged();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_server_url, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tv.setText(datas.get(position));
        holder.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(holder.itemView, holder.getLayoutPosition(), datas.get(position));
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv;

        public ViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv);
        }
    }


    public interface OnItemClickListener {
        void onItemClick(View view, int position, String url);
    }

}
