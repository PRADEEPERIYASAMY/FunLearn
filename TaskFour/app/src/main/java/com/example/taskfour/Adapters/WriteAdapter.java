package com.example.taskfour.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.taskfour.R;

public class WriteAdapter extends RecyclerView.Adapter<WriteAdapter.MyViewHolder> {

    Context context;
    int count;
    int index;
    private String[] image = {"https://media-public.canva.com/0-WT0/MADw8I0-WT0/1/tl.png","https://media-public.canva.com/MACC44an_5k/1/thumbnail_large.png","https://media-public.canva.com/VjvJU/MADk9yVjvJU/2/tl.png","https://media-public.canva.com/MADHVdgdxf0/1/thumbnail_large.png","https://media-public.canva.com/MADrPAcuRBw/1/thumbnail_large.png","https://media-public.canva.com/9R0lU/MADsQy9R0lU/1/thumbnail_large.png","https://media-public.canva.com/S2D-E/MADmjBS2D-E/2/tl.png","https://media-public.canva.com/MADpjq62--c/1/thumbnail_large.png","https://media-public.canva.com/MACBkwmKyhQ/1/thumbnail_large.png","https://media-public.canva.com/gPXd8/MADVnZgPXd8/2/tl.png"};
    public WriteAdapter( Context context, int count,int index ) {
        this.context = context;
        this.count = count;
        this.index  = index;
    }

    @NonNull
    @Override
    public WriteAdapter.MyViewHolder onCreateViewHolder( @NonNull ViewGroup parent, int viewType ) {
        View itemView = LayoutInflater.from ( context ).inflate ( R.layout.write_item,parent,false);
        return new MyViewHolder ( itemView );
    }

    @Override
    public void onBindViewHolder( @NonNull WriteAdapter.MyViewHolder holder, int position ) {
        Glide.with ( context ).load ( image[index]).into ( holder.imageView );
    }

    @Override
    public int getItemCount() {
        return count;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public MyViewHolder( @NonNull View itemView ) {
            super ( itemView );
            imageView = itemView.findViewById ( R.id.write_image );
        }
    }
}
