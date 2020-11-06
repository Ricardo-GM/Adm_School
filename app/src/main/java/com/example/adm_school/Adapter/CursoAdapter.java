package com.example.adm_school.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adm_school.Models.Curso;
import com.example.adm_school.R;

import java.util.List;

public class CursoAdapter extends RecyclerView.Adapter<CursoAdapter.ViewHolder> {
    private List<Curso> cursos;
    private int layout;
    private OnItemClickListener itemClickListener;
    private OnLongItemClickListener longItemClickListener;
    private Context context;

    public CursoAdapter(List<Curso> cursos, int layout, OnItemClickListener itemClickListener, OnLongItemClickListener longItemClickListener) {
        this.cursos = cursos;
        this.layout = layout;
        this.itemClickListener = itemClickListener;
        this.longItemClickListener = longItemClickListener;
    }

    @NonNull
    @Override
    public CursoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        context = parent.getContext();
         ViewHolder vh = new ViewHolder(v);
         return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull CursoAdapter.ViewHolder holder, int position) {
        holder.bind(cursos.get(position), itemClickListener);
        holder.bind2(cursos.get(position), longItemClickListener);
    }


    @Override
    public int getItemCount() {return cursos.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.info_text);
        }

        public void bind(final Curso curso, final OnItemClickListener listener){
            textView.setText("Nombre de Curso: "+curso.getNombreCurso());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(curso, getAdapterPosition());
                }
            });
        }

        public void bind2(final Curso curso, final OnLongItemClickListener listener){
            textView.setText("Nombre de Curso: "+curso.getNombreCurso());

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.onLongItemClick(curso, getAdapterPosition());
                    return true;
                }
            });
        }
    }
    public interface OnItemClickListener{
        void onItemClick(Curso curso, int position);
    }

    public interface OnLongItemClickListener{
        void onLongItemClick(Curso curso, int position);
    }

}
