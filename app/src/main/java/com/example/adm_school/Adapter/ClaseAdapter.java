package com.example.adm_school.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adm_school.Models.Clase;
import com.example.adm_school.R;

import java.util.List;

public class ClaseAdapter extends RecyclerView.Adapter<ClaseAdapter.ViewHolder> {
    private List<Clase> clases;
    private int layout;
    private OnItemClickListener itemClickListener;
    private OnLongItemClickListener longItemClickListener;
    private Context context;

    public ClaseAdapter(List<Clase> clases, int layout, OnItemClickListener itemClickListener, OnLongItemClickListener longItemClickListener) {
        this.clases = clases;
        this.layout = layout;
        this.itemClickListener = itemClickListener;
        this.longItemClickListener = longItemClickListener;
    }

    @NonNull
    @Override
    public ClaseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        context = parent.getContext();
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ClaseAdapter.ViewHolder holder, int position) {
        holder.bind(clases.get(position), itemClickListener);
        holder.bind2(clases.get(position), longItemClickListener);
    }

    @Override
    public int getItemCount() { return clases.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.info_text);
        }

        public void bind(final Clase clase, final OnItemClickListener listener){
            textView.setText("Nombre de clase: "+clase.getNombreClase());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(clase, getAdapterPosition());
                }
            });
        }

        public void bind2(final Clase clase, final OnLongItemClickListener listener){
            textView.setText("Nombre de clase: "+clase.getNombreClase());

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.onLongItemClick(clase, getAdapterPosition());
                    return true;
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(Clase clase, int position);
    }

    public interface OnLongItemClickListener{
        void onLongItemClick(Clase clase, int position);
    }
}
