package com.example.adm_school.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adm_school.R;
import com.example.adm_school.Models.Salon;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SalonAdapter extends RecyclerView.Adapter<SalonAdapter.ViewHolder>{

    private List<Salon> salones;
    private int layout;
    private OnItemClickListener itemClickListener;
    private OnLongItemClickListener longItemClickListener;

    private Context context;

    public SalonAdapter(List<Salon> salones, int layout, OnItemClickListener onItemClickListener, OnLongItemClickListener onLongItemClickListener){
        this.salones = salones;
        this.layout = layout;
        this.itemClickListener = onItemClickListener;
        this.longItemClickListener = onLongItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        context = parent.getContext();
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(salones.get(position), itemClickListener);
        holder.bind2(salones.get(position), longItemClickListener);
    }



    @Override
    public int getItemCount() { return salones.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            textView = itemView.findViewById(R.id.info_text);
        }

        public void bind(final Salon salon, final OnItemClickListener listener){
            textView.setText("Nombre de salón: "+salon.getNombreSalon());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(salon, getAdapterPosition());
                }
            });
        }

        public void bind2(final Salon salon, final OnLongItemClickListener listener){
            textView.setText("Nombre de salón: "+salon.getNombreSalon());

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.onLongItemClick(salon, getAdapterPosition());
                    return true;
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(Salon salon, int position);
    }

    public interface OnLongItemClickListener{
        void onLongItemClick(Salon salon, int position);
    }






}
