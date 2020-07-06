package com.example.taskfour.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskfour.R;

import java.util.ArrayList;
import java.util.List;

public class PatternAdapter extends RecyclerView.Adapter<PatternAdapter.MyViewHolder> {

    Context context;
    int[] colors;
    public static List<MyViewHolder> myViewHolders = new ArrayList<> (  );

    public PatternAdapter( Context context , int[] colors ) {
        this.context = context;
        this.colors = colors;
    }

    @NonNull
    @Override
    public PatternAdapter.MyViewHolder onCreateViewHolder( @NonNull ViewGroup parent , int viewType ) {
        return new MyViewHolder ( LayoutInflater.from ( context ).inflate ( R.layout.pattern_item,parent,false ) );
    }

    @Override
    public void onBindViewHolder( @NonNull PatternAdapter.MyViewHolder holder , int position ) {
        holder.cardView.setCardBackgroundColor ( colors[position] );
        if (myViewHolders.size ()<=25){
            myViewHolders.add ( holder );
        }
    }

    @Override
    public int getItemCount() {
        return 25;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        public MyViewHolder( @NonNull View itemView ) {
            super ( itemView );
            cardView = itemView.findViewById ( R.id.card );
        }
    }
}
