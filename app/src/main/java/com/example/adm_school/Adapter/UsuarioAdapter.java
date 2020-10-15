package com.example.adm_school.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adm_school.Models.Salon;
import com.example.adm_school.Models.Usuario;
import com.example.adm_school.R;

import java.util.List;

public class UsuarioAdapter extends RecyclerView.Adapter<UsuarioAdapter.ViewHolder> {
    private List<Usuario> usuarios;
    private int layout;
    private OnItemClickListener itemClickListener;
    private OnLongItemClickListener longClickListener;

    private Context context;

    public UsuarioAdapter(List<Usuario> usuarios, int layout, OnItemClickListener itemClickListener) {
        this.usuarios = usuarios;
        this.layout = layout;
        this.itemClickListener = itemClickListener;
        this.longClickListener = longClickListener;
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
        holder.bind(usuarios.get(position), itemClickListener);
    }

    @Override
    public int getItemCount() { return usuarios.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.info_text);
        }

        public void bind(final Usuario usuario, final OnItemClickListener listener){
            textView.setText("Nombre de Usuario: "+usuario.getNombre());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(usuario, getAdapterPosition());
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(Usuario usuario, int position);
    }

    public interface OnLongItemClickListener{
        void onLongItemClick(Usuario usuario, int position);
    }


}
