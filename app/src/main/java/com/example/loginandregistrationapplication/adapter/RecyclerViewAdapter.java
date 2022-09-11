package com.example.loginandregistrationapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginandregistrationapplication.R;
import com.example.loginandregistrationapplication.models.TramXe;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private ArrayList<TramXe> tramXeArrayList;
    private Context context;

    public RecyclerViewAdapter(ArrayList<TramXe> tramXeArrayList, Context context) {
        this.tramXeArrayList = tramXeArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlist_tramxe,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TramXe tramXe = tramXeArrayList.get(position);
        holder.tvName.setText(tramXe.getMatramxe());
        holder.tvDiachi.setText(tramXe.getDiachi());
        holder.tvSoluong.setText("Số xe: " + Integer.toString(tramXe.getSoluongxe()));
        holder.diachi_img.setImageDrawable(context.getResources().getDrawable(R.drawable.vitriicon));
        holder.soluong_img.setImageDrawable(context.getResources().getDrawable(R.drawable.soluongicon));
        holder.dieuhuong_btn.setBackground(context.getResources().getDrawable(R.drawable.dieuhuong));
        holder.dieuhuong_btn.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View view) {
                Uri gmmIntentUri = Uri.parse("geo:" + tramXe.getLatitude() + "," + tramXe.getLongitude());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                context.startActivity(mapIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tramXeArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName, tvDiachi, tvSoluong;
        private ImageView diachi_img, soluong_img;
        private Button dieuhuong_btn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tramxe);
            tvDiachi = itemView.findViewById(R.id.diachi);
            tvSoluong = itemView.findViewById(R.id.soluong);
            diachi_img = itemView.findViewById(R.id.vitriIcon);
            soluong_img = itemView.findViewById(R.id.soluongIcon);
            dieuhuong_btn = itemView.findViewById(R.id.dieuhuong);
        }
    }




}
