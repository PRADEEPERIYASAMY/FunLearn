package com.example.taskfour.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskfour.Numbers.NumberConverter;
import com.example.taskfour.R;

public class NumberInfoAdapter extends RecyclerView.Adapter<NumberInfoAdapter.MyViewHolder> {

    Context context;

    public NumberInfoAdapter( Context context ) {
        this.context = context;
    }

    @NonNull
    @Override
    public NumberInfoAdapter.MyViewHolder onCreateViewHolder( @NonNull ViewGroup parent , int viewType ) {
        View view = LayoutInflater.from ( context ).inflate ( R.layout.number_info_item ,parent,false);
        return new MyViewHolder ( view );
    }

    @Override
    public void onBindViewHolder( @NonNull NumberInfoAdapter.MyViewHolder holder , int position ) {
        holder.numbers.setText ( String.valueOf ( position+1 ) );
        holder.numberWords.setText ( NumberConverter.numberNames[position] );
    }

    @Override
    public int getItemCount() {
        return 100;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView numberWords,numbers ;
        public MyViewHolder( @NonNull View itemView ) {
            super ( itemView );
            numberWords = itemView.findViewById ( R.id.number_words );
            numbers = itemView.findViewById ( R.id.number );
        }
    }
}
