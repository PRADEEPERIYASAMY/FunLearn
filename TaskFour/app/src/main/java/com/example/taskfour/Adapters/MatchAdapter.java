package com.example.taskfour.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskfour.R;
import com.example.taskfour.alphabets.Match;

import java.util.List;

public class MatchAdapter extends RecyclerView.Adapter<MatchAdapter.MyViewHolder> {

    Context context;
    List<String> index;

    public MatchAdapter( Context context , List<String> index ) {
        this.context = context;
        this.index = index;
    }

    @NonNull
    @Override
    public MatchAdapter.MyViewHolder onCreateViewHolder( @NonNull ViewGroup parent , int viewType ) {
        View view = LayoutInflater.from ( context ).inflate ( R.layout.match_item,parent,false );
        return new MyViewHolder ( view );
    }

    @Override
    public void onBindViewHolder( @NonNull MatchAdapter.MyViewHolder holder , int position ) {
        holder.textView.setText ( Match.name[Integer.parseInt ( index.get ( position ) )] );
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public MyViewHolder( @NonNull View itemView ) {
            super ( itemView );
            textView = itemView.findViewById ( R.id.match_name );
        }
    }
}
