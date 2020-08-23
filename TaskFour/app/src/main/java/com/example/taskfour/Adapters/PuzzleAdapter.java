package com.example.taskfour.Adapters;

import android.content.Context;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.taskfour.R;

import java.util.ArrayList;
import java.util.List;

public class PuzzleAdapter extends RecyclerView.Adapter<PuzzleAdapter.MyViewHolder> {

    Context context;
    List<String> id ;
    List<String> original;

    public PuzzleAdapter( Context context, List<String> id,List<String> original ) {
        this.context = context;
        this.id = id;
        this.original = original;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder( @NonNull ViewGroup parent, int viewType ) {

        View view = LayoutInflater.from ( context ).inflate ( R.layout.puzzle_item,parent,false );
        return new MyViewHolder ( view ) ;
    }

    @Override
    public void onBindViewHolder( @NonNull MyViewHolder holder, int position ) {
        Glide.with ( context ).load ( id.get ( position ) ).into ( holder.imageView );
        holder.textView.setText ( String.valueOf ( original.indexOf ( id.get ( position ) )+1 ) );
    }

    @Override
    public int getItemCount() {
        return id.size ();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;

        public MyViewHolder( @NonNull View itemView ) {
            super ( itemView );
            imageView = itemView.findViewById ( R.id.puzzle_image );
            textView = itemView.findViewById ( R.id.id );
        }
    }
}
