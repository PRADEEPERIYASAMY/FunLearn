package com.example.taskfour.Adapters;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.taskfour.PdfViewActivity;
import com.example.taskfour.R;

import java.util.List;

public class PdfAdapter extends RecyclerView.Adapter<PdfAdapter.MyViewHolder> {
    List<String> name ;
    List<String> url ;
    List<String> image;

    public PdfAdapter( List<String> name , List<String> url , List<String> image ) {
        this.name = name;
        this.url = url;
        this.image = image;
    }

    @NonNull
    @Override
    public PdfAdapter.MyViewHolder onCreateViewHolder( @NonNull ViewGroup parent , int viewType ) {
        View view = LayoutInflater.from ( parent.getContext () ).inflate ( R.layout.pdf_item,parent,false );
        return new MyViewHolder ( view );
    }

    @Override
    public void onBindViewHolder( @NonNull PdfAdapter.MyViewHolder holder , int position ) {
        holder.name.setText ( name.get ( position ) );
        Glide.with ( holder.itemView.getContext () ).load ( image.get ( position) ).into ( holder.image );
    }

    @Override
    public int getItemCount() {
        return url.size ();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name;
        public MyViewHolder( @NonNull final View itemView ) {
            super ( itemView );
            image = itemView.findViewById ( R.id.pdf_image );
            name = itemView.findViewById ( R.id.pdf_name );

            final MediaPlayer mediaPlayer1 = new MediaPlayer ().create(itemView.getContext (),R.raw.rubberone);

            itemView.setOnClickListener ( new View.OnClickListener () {
                @Override
                public void onClick( View v ) {
                    mediaPlayer1.start ();
                    itemView.startAnimation ( AnimationUtils.loadAnimation ( itemView.getContext (),R.anim.transparantbut ) );
                    Intent intent = new Intent ( itemView.getContext (),PdfViewActivity.class );
                    intent.setFlags ( Intent.FLAG_ACTIVITY_NEW_TASK );
                    intent.putExtra ( "url",url.get ( getAdapterPosition () ) );
                    itemView.getContext ().startActivity ( intent );
                }
            } );
        }
    }
}
