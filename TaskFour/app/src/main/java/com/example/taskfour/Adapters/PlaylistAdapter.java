package com.example.taskfour.Adapters;

import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskfour.R;
import com.example.taskfour.TutorialActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.MyViewHolder> {

    private List<String> urlList = new ArrayList<> (  );
    private List<String> titleList = new ArrayList<> (  );
    private List<String> descriptionList = new ArrayList<> (  );

    public PlaylistAdapter( List<String> urlList , List<String> titleList , List<String> descriptionList ) {
        this.urlList = urlList;
        this.titleList = titleList;
        this.descriptionList = descriptionList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder( @NonNull ViewGroup parent , int viewType ) {
        return new MyViewHolder ( LayoutInflater.from ( parent.getContext () ).inflate ( R.layout.sample_play,parent,false ) );
    }

    @Override
    public void onBindViewHolder( @NonNull MyViewHolder holder , int position ) {
        holder.title.setText ( titleList.get ( position ).toString () );
    }

    @Override
    public int getItemCount() {
        return urlList.size ();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        Button play;
        TextView title;
        public MyViewHolder( @NonNull final View itemView ) {
            super ( itemView );
            play = itemView.findViewById ( R.id.but );
            title = itemView.findViewById ( R.id.title );
            final MediaPlayer mediaPlayer1 = new MediaPlayer ().create(itemView.getContext (),R.raw.rubberone);
            play.setOnClickListener ( new View.OnClickListener () {
                @Override
                public void onClick( View v ) {
                    mediaPlayer1.start ();
                    itemView.startAnimation ( AnimationUtils.loadAnimation ( itemView.getContext (),R.anim.transparantbut ) );
                    TutorialActivity.videoLayout.reset ();
                    try {
                        TutorialActivity.videoLayout.setVideoURI ( Uri.parse ( urlList.get ( getAdapterPosition () ) ) );
                    } catch (IOException e) {
                        e.printStackTrace ();
                    }
                    TutorialActivity.videoLayout.start ();
                    TutorialActivity.title.setText ( titleList.get ( getAdapterPosition () ) );
                    TutorialActivity.description.setText ( descriptionList.get ( getAdapterPosition () ) );
                }
            } );
        }
    }
}
