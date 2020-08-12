package com.example.taskfour.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskfour.PaintingOneActivity;
import com.example.taskfour.PaintingTwoActivity;
import com.example.taskfour.R;
import com.example.taskfour.SignInActivity;

import java.io.File;
import java.util.List;

public class FilesAdapter extends RecyclerView.Adapter<FilesAdapter.MyViewHolder> {

    private Context mContext;
    private List<File> fileList;
    private int id;
    private MediaPlayer mediaPlayer,mediaPlayer1;

    public FilesAdapter( Context mContext, List<File> fileList,int id ) {
        this.mContext = mContext;
        this.fileList = fileList;
        this.id = id;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder( @NonNull ViewGroup parent, int viewType ) {
        View view = LayoutInflater.from ( mContext ).inflate ( R.layout.row_file,parent,false );
        return new MyViewHolder ( view );
    }

    @Override
    public void onBindViewHolder( @NonNull MyViewHolder holder, int position ) {
        holder.imageView.setImageURI ( Uri.fromFile ( fileList.get ( position ) ) );

    }

    @Override
    public int getItemCount() {
        return fileList.size ();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        Button selected , delete;
        CardView c1,c2;

        public MyViewHolder( @NonNull final View itemView ) {
            super ( itemView );
            imageView = itemView.findViewById ( R.id.image );
            selected = itemView.findViewById ( R.id.selected );
            delete = itemView.findViewById ( R.id.delete );
            c1 = itemView.findViewById ( R.id.c1 );
            c2 = itemView.findViewById ( R.id.c2 );
            mediaPlayer = new MediaPlayer ().create(mContext,R.raw.glass);
            mediaPlayer1 = new MediaPlayer ().create(mContext,R.raw.rubberone);

            selected.setOnClickListener ( new View.OnClickListener () {
                @Override
                public void onClick( View v ) {
                    mediaPlayer1.start ();
                    c1.startAnimation ( AnimationUtils.loadAnimation ( mContext,R.anim.button ) );
                    if (id == 1){
                        ((Activity)mContext).finish ();
                        Intent intent = new Intent ( mContext, PaintingOneActivity.class );
                        intent.setData ( Uri.fromFile ( fileList.get ( getAdapterPosition () ) ) );
                        intent.setFlags ( Intent.FLAG_ACTIVITY_NEW_TASK );
                        mContext.startActivity ( intent );
                    }
                    else {
                        ((Activity)mContext).finish ();
                        Intent intent = new Intent ( mContext, PaintingTwoActivity.class );
                        intent.setData ( Uri.fromFile ( fileList.get ( getAdapterPosition () ) ) );
                        intent.setFlags ( Intent.FLAG_ACTIVITY_NEW_TASK );
                        mContext.startActivity ( intent );
                    }
                }
            } );

            delete.setOnClickListener ( new View.OnClickListener () {
                @Override
                public void onClick( View v ) {
                    mediaPlayer.start ();
                    c2.startAnimation ( AnimationUtils.loadAnimation ( mContext,R.anim.button ) );
                    Uri imageUri;
                    imageUri = Uri.parse(fileList.get ( getAdapterPosition () ).toString ());
                    File delete = new File( String.valueOf (imageUri ) );

                    if (delete.exists()) {
                        int pos = getAdapterPosition ();
                        fileList.remove ( pos );
                        notifyItemRemoved ( pos );
                        if (delete.delete()) {
                            View view = LayoutInflater.from ( itemView.getContext () ).inflate ( R.layout.toast,null,false );
                            TextView textView = view.findViewById ( R.id.toast_text );
                            textView.setText ( "Deleted" );
                            Toast toast = new Toast ( itemView.getContext () );
                            toast.setView ( view );
                            toast.show ();
                        } else {
                            View view = LayoutInflater.from ( itemView.getContext () ).inflate ( R.layout.toast,null,false );
                            TextView textView = view.findViewById ( R.id.toast_text );
                            textView.setText ( "Failed" );
                            Toast toast = new Toast ( itemView.getContext () );
                            toast.setView ( view );
                            toast.show ();
                        }
                    }
                }
            } );

        }
    }
}
