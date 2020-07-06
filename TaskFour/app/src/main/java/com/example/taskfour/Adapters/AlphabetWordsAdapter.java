package com.example.taskfour.Adapters;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.taskfour.R;
import com.example.taskfour.alphabets.AlphabetImagesAndNames;

import java.util.Locale;

public class AlphabetWordsAdapter extends RecyclerView.Adapter<AlphabetWordsAdapter.MyViewHolder> {

    Context context;
    int id;
    MediaPlayer mediaPlayer;

    public AlphabetWordsAdapter( Context context, int id ) {
        this.context = context;
        this.id = id;
    }

    @NonNull
    @Override
    public AlphabetWordsAdapter.MyViewHolder onCreateViewHolder( @NonNull ViewGroup parent, int viewType ) {

        View itemView = LayoutInflater.from ( context ).inflate ( R.layout.alphabet_info_item,parent,false);
        return new MyViewHolder ( itemView );
    }

    @Override
    public void onBindViewHolder( @NonNull AlphabetWordsAdapter.MyViewHolder holder, int position ) {
        holder.textView.setText ( AlphabetImagesAndNames.name[id][position] );
        Glide.with ( context ).load ( AlphabetImagesAndNames.images[id][position] ).into ( holder.imageView );
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        ImageView imageView;
        Button speak;
        TextToSpeech textToSpeech;
        CardView cardView;
        public MyViewHolder( @NonNull View itemView ) {
            super ( itemView );
            textView = itemView.findViewById ( R.id.alphabet_word_name );
            imageView = itemView.findViewById ( R.id.alphabet_word_image );
            speak = itemView.findViewById ( R.id.word_speak_button );
            cardView = itemView.findViewById ( R.id.c1 );
            textToSpeech = new TextToSpeech ( context, new TextToSpeech.OnInitListener () {
                @Override
                public void onInit( int status ) {
                    if (status != TextToSpeech.ERROR){
                        textToSpeech.setLanguage ( Locale.UK );
                    }
                }
            } );
            mediaPlayer = new MediaPlayer ().create(context,R.raw.rubberone);
            speak.setOnClickListener ( new View.OnClickListener () {
                @Override
                public void onClick( View v ) {
                    cardView.startAnimation ( AnimationUtils.loadAnimation ( context,R.anim.button ) );
                    mediaPlayer.start ();
                    new Handler (  ).postDelayed ( new Runnable () {
                        @Override
                        public void run() {
                            textToSpeech.speak ( textView.getText ().toString (),TextToSpeech.QUEUE_FLUSH,null );
                        }
                    },250 );
                }
            } );
        }
    }
}
