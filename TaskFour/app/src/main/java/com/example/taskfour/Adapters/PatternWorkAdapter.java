package com.example.taskfour.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskfour.GameOneActivity;
import com.example.taskfour.GameOnePlay;
import com.example.taskfour.PaintingThreeActivity;
import com.example.taskfour.Pattern.Patterns;
import com.example.taskfour.R;

import java.util.ArrayList;
import java.util.List;

public class PatternWorkAdapter extends RecyclerView.Adapter<PatternWorkAdapter.MyViewHolder> {

    Context context;
    List<MyViewHolder> myViewHolders = new ArrayList<> (  );
    MediaPlayer mediaPlayer,mediaPlayer1,mediaPlayer2;

    public PatternWorkAdapter( Context context ) {
        this.context = context;
        mediaPlayer = new MediaPlayer ().create(context,R.raw.splash);
        mediaPlayer1 = new MediaPlayer ().create(context,R.raw.negative);
        mediaPlayer2 = new MediaPlayer ().create(context,R.raw.rubberone);
    }

    @NonNull
    @Override
    public PatternWorkAdapter.MyViewHolder onCreateViewHolder( @NonNull ViewGroup parent , int viewType ) {
        return new MyViewHolder ( LayoutInflater.from ( context ).inflate ( R.layout.pattern_work_item,parent,false ) );
    }

    @Override
    public void onBindViewHolder( @NonNull PatternWorkAdapter.MyViewHolder holder , int position ) {
        holder.cardView.setCardBackgroundColor ( Patterns.white );
        if (myViewHolders.size () <= 25){
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
            itemView.setOnClickListener ( new View.OnClickListener () {
                @Override
                public void onClick( View v ) {
                    cardView.startAnimation ( AnimationUtils.loadAnimation ( context,R.anim.click ) );
                    cardView.setCardBackgroundColor ( PaintingThreeActivity.selectedColor );

                    mediaPlayer.start ();

                    List<String> verify = new ArrayList<> (  );
                    verify.clear ();

                    for (int i = 0; i < 25; i++) {
                        if (myViewHolders.get ( i ).cardView.getCardBackgroundColor () == PatternAdapter.myViewHolders.get ( i ).cardView.getCardBackgroundColor ()){
                            verify.add ( "o" );
                        }
                    }

                    if (verify.size () == 25){
                        mediaPlayer1.start ();
                        PatternAdapter.myViewHolders.clear ();
                        myViewHolders.clear ();
                        final AlertDialog.Builder builder = new AlertDialog.Builder ( context,R.style.CustomDialog );
                        View view = LayoutInflater.from ( context ).inflate ( R.layout.alphabet_write_dialog,null,false );
                        Button yes = view.findViewById ( R.id.yes );
                        Button no = view.findViewById ( R.id.no );
                        TextView firstText = view.findViewById ( R.id.firstText );
                        TextView secondText = view.findViewById ( R.id.secontText );
                        ImageView dialogImage = view.findViewById ( R.id.dialog_image );

                        final AlertDialog alertDialog = builder.create ();
                        alertDialog.setView ( view );

                        if (PaintingThreeActivity.id <15){
                            firstText.setText ( "Excellent work !" );
                            secondText.setText ( "Wanna Move to Next ?" );
                            dialogImage.setImageResource ( R.drawable.excellent );

                            yes.setOnClickListener ( new View.OnClickListener () {
                                @Override
                                public void onClick( View v ) {
                                    mediaPlayer2.start ();
                                    (( Activity )context).finish ();
                                    Intent intent = new Intent ( context,PaintingThreeActivity.class );
                                    intent.setFlags ( Intent.FLAG_ACTIVITY_NEW_TASK );
                                    intent.putExtra ( "id",PaintingThreeActivity.id+1 );
                                    context.startActivity ( intent );
                                }
                            } );
                        }
                        else {
                            firstText.setText ( "End of show..." );
                            secondText.setText ( "Do you wanna retry ?" );
                            dialogImage.setImageResource ( R.drawable.excellent );

                            yes.setOnClickListener ( new View.OnClickListener () {
                                @Override
                                public void onClick( View v ) {
                                    mediaPlayer2.start ();
                                    (( Activity )context).finish ();
                                    Intent intent = new Intent ( context,PaintingThreeActivity.class );
                                    intent.setFlags ( Intent.FLAG_ACTIVITY_NEW_TASK );
                                    intent.putExtra ( "id",0 );
                                    context.startActivity ( intent );
                                }
                            } );
                        }

                        no.setOnClickListener ( new View.OnClickListener () {
                            @Override
                            public void onClick( View v ) {
                                mediaPlayer2.start ();
                                (( Activity )context).finish ();
                            }
                        } );
                        alertDialog.setCanceledOnTouchOutside ( false );
                        alertDialog.show ();
                    }

                }
            } );
        }
    }
}
