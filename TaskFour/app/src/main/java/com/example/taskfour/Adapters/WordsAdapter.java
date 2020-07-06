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
import com.example.taskfour.alphabets.AlphabetsWords;

import java.util.Locale;

public class WordsAdapter extends RecyclerView.Adapter<WordsAdapter.MyViewHolder> {

    Context context;
    int id;
    MediaPlayer mediaPlayer;

    public WordsAdapter( Context context , int id ) {
        this.context = context;
        this.id = id;
    }

    @NonNull
    @Override
    public WordsAdapter.MyViewHolder onCreateViewHolder( @NonNull ViewGroup parent , int viewType ) {
        View view = LayoutInflater.from ( context ).inflate ( R.layout.words_info_item,parent,false );
        return new MyViewHolder ( view );
    }

    @Override
    public void onBindViewHolder( @NonNull WordsAdapter.MyViewHolder holder , int position ) {
        holder.textView.setText ( AlphabetsWords.words[id-1][position] );
        Glide.with ( context ).load (  AlphabetsWords.images[id-1][position] ).into ( holder.imageView );
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        ImageView imageView;
        Button speak;
        TextToSpeech textToSpeech;
        CardView c1;

        public MyViewHolder( @NonNull View itemView ) {
            super ( itemView );
            textView = itemView.findViewById ( R.id.alphabet_word_name );
            imageView = itemView.findViewById ( R.id.alphabet_word_image );
            speak = itemView.findViewById ( R.id.word_speak_button );
            c1 = itemView.findViewById ( R.id.c1 );
            mediaPlayer = new MediaPlayer ().create(context,R.raw.rubberone);
            textToSpeech = new TextToSpeech ( context, new TextToSpeech.OnInitListener () {
                @Override
                public void onInit( int status ) {
                    if (status != TextToSpeech.ERROR){
                        textToSpeech.setLanguage ( Locale.UK );
                    }
                }
            } );
            speak.setOnClickListener ( new View.OnClickListener () {
                @Override
                public void onClick( View v ) {
                    c1.startAnimation ( AnimationUtils.loadAnimation ( context,R.anim.button ) );
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
