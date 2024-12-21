package com.example.btlandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DanhGiaAdapter extends RecyclerView.Adapter<DanhGiaAdapter.ViewHolder> {
    private Context context;
    private ArrayList<DanhGia> danhGiaList;

    public DanhGiaAdapter(Context context, ArrayList<DanhGia> danhGiaList) {
        this.context = context;
        this.danhGiaList = danhGiaList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_danhgia, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DanhGia danhGia = danhGiaList.get(position);
        holder.tvDanhGia.setText(danhGia.getDanhGia());
        holder.tvRate.setText("Rating: " + danhGia.getRate());
    }

    @Override
    public int getItemCount() {
        return danhGiaList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDanhGia, tvRate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDanhGia = itemView.findViewById(R.id.tvDanhGia);
            tvRate = itemView.findViewById(R.id.tvRate);
        }
    }
}
