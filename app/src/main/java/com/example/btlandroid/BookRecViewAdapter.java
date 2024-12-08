package com.example.btlandroid;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.ContentView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class BookRecViewAdapter extends RecyclerView.Adapter<BookRecViewAdapter.ViewHolder>{
    private ArrayList<Sach> listSach;
    private Context context;

    public BookRecViewAdapter(Context context, ArrayList<Sach> listSach) {
        this.context = context;
        this.listSach = listSach;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_bookslist_recview,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
           holder.txtTenSach.setText(listSach.get(position).getTenSach());
           holder.txtTacGia.setText("Tác giả: "+listSach.get(position).getTacGia());
           holder.txtSoTrang.setText("Số trang: "+String.valueOf(listSach.get(position).getSoTrang()));
           Glide.with(context).asBitmap().load(listSach.get(position).getUrlAnh())
                   .error(R.drawable.ic_launcher_background)
                   .into(holder.imageView)
                   .onLoadFailed(holder.imageView.getDrawable());
                   ;
           holder.parent.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   if(holder.txtTenSach.getVisibility()==View.GONE){
                       holder.txtTenSach.setVisibility(View.VISIBLE);
                       holder.txtTacGia.setVisibility(View.VISIBLE);
                       holder.txtSoTrang.setVisibility(View.VISIBLE);
                   }
                   else {
                       holder.txtTenSach.setVisibility(View.GONE);
                       holder.txtTacGia.setVisibility(View.GONE);
                       holder.txtSoTrang.setVisibility(View.GONE);
                   }
               }
           });
//           holder.parent.setOnLongClickListener(new View.OnLongClickListener() {
//               @Override
//               public boolean onLongClick(View view) {
//
//               }
//           });
    }

    @Override
    public int getItemCount() {
        return listSach.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout parent;
        private ImageView imageView;
        private TextView txtTenSach;
        private TextView txtTacGia;
        private TextView txtSoTrang;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.parent);
            imageView = itemView.findViewById(R.id.imageView);
            txtTenSach = itemView.findViewById(R.id.txtTenSach);
            txtTacGia = itemView.findViewById(R.id.txtTacGia);
            txtSoTrang = itemView.findViewById(R.id.txtSoTrang);
        }
    }
}
