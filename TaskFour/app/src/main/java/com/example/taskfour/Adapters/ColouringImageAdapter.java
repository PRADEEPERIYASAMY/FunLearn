package com.example.taskfour.Adapters;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.taskfour.PaintingTwoActivity;
import com.example.taskfour.R;

import java.util.List;

public class ColouringImageAdapter extends RecyclerView.Adapter<ColouringImageAdapter.MyViewHolder> {

    Context context;
    MediaPlayer mediaPlayer;
    List<String> colouringUrls;

    public ColouringImageAdapter( Context context , List<String> colouringUrls ) {
        this.context = context;
        this.colouringUrls = colouringUrls;
    }

    @NonNull
    @Override
    public ColouringImageAdapter.MyViewHolder onCreateViewHolder( @NonNull ViewGroup parent , int viewType ) {
        return new MyViewHolder( LayoutInflater.from ( context ).inflate ( R.layout.colouring_list_item,parent,false ) );
    }

    @Override
    public void onBindViewHolder( @NonNull ColouringImageAdapter.MyViewHolder holder , int position ) {
        Glide.with ( context ).load ( colouringUrls.get ( position ) ).into ( holder.imageView );
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        CardView cardView;

        public MyViewHolder( @NonNull View itemView ) {
            super ( itemView );
            imageView = itemView.findViewById ( R.id.colouring_image );
            cardView = itemView.findViewById ( R.id.c1 );
            mediaPlayer = new MediaPlayer ().create(context,R.raw.rubberone);
            itemView.setOnClickListener ( new View.OnClickListener () {
                @Override
                public void onClick( View v ) {
                    mediaPlayer.start ();
                    cardView.startAnimation ( AnimationUtils.loadAnimation ( context,R.anim.button ) );
                    Intent intent =  new Intent ( context, PaintingTwoActivity.class );
                    intent.putExtra ( "imageid",colouringUrls.get ( getAdapterPosition () ) );
                    intent.setFlags ( Intent.FLAG_ACTIVITY_NEW_TASK );
                    context.startActivity ( intent);
                }
            } );
        }
    }
}
