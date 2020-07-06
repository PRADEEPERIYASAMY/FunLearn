package com.example.taskfour.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.opengl.Visibility;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.taskfour.Database.Level;
import com.example.taskfour.GameObject.GameImages;
import com.example.taskfour.GameOneActivity;
import com.example.taskfour.GameOnePlay;
import com.example.taskfour.Main2Activity;
import com.example.taskfour.R;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class GameOneAdapter extends RecyclerView.Adapter<GameOneAdapter.MyViewHolder> {

    Context context;
    int count;
    List<String> numbers;
    static int first;
    static int second;
    String string1;
    String string2;
    TextView timer;
    MediaPlayer mediaPlayer,mediaPlayer1,mediaPlayer2,mediaPlayer3,mediaPlayer4;

    List<MyViewHolder> myViewHolders = new ArrayList<> (  );

    public GameOneAdapter( Context context, int count, List<String> numbers,TextView timer ) {
        this.context = context;
        this.count = count;
        this.numbers = numbers;
        this.timer = timer;
        first = 100;
        second = 100;
        string1 = "";
        string2 = "";
        mediaPlayer = new MediaPlayer ().create(context,R.raw.gamewood);
        mediaPlayer1 = new MediaPlayer ().create(context,R.raw.gamewrong);
        mediaPlayer2 = new MediaPlayer ().create(context,R.raw.sucess);
        mediaPlayer3 = new MediaPlayer ().create(context,R.raw.rubberone);
        mediaPlayer4 = new MediaPlayer ().create(context,R.raw.negative);
    }

    @NonNull
    @Override
    public GameOneAdapter.MyViewHolder onCreateViewHolder( @NonNull ViewGroup parent, int viewType ) {
        View itemView = LayoutInflater.from ( context ).inflate ( R.layout.game_one_item_one,parent,false);
        return new MyViewHolder ( itemView );
    }

    @Override
    public void onBindViewHolder( @NonNull GameOneAdapter.MyViewHolder holder, int position ) {
        holder.imageView.setImageResource ( R.drawable.wood);
        holder.textView.setText ( numbers.get ( position ) );
        Glide.with ( context ).load ( GameImages.images[Integer.parseInt ( numbers.get ( position ) )-1] ).into ( holder.object );
        myViewHolders.add ( holder);
    }

    @Override
    public int getItemCount() {
        return count;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        Button button;
        ImageView object;
        public MyViewHolder( @NonNull final View itemView ) {
            super ( itemView );
            imageView = itemView.findViewById ( R.id.game_one_item_image );
            textView = itemView.findViewById ( R.id.game_one_item_name );
            button = itemView.findViewById ( R.id.game_but );
            object = itemView.findViewById ( R.id.game_object_image );

            final View.OnClickListener onClickListener = new View.OnClickListener () {
                @Override
                public void onClick( View v ) {

                    final Level level = new Level ( context );
                    final Cursor cursor = level.FetchData ();

                    if (first == 100){
                        mediaPlayer.start ();
                        itemView.startAnimation( AnimationUtils.loadAnimation (context ,R.anim.scale ) );
                        imageView.setVisibility ( View.INVISIBLE );
                        first = getAdapterPosition ();
                        second = 100;
                        string1 = textView.getText ().toString ();
                        string2 = "";
                    }
                    else if (first != getAdapterPosition ()){
                        mediaPlayer.start ();
                        itemView.startAnimation( AnimationUtils.loadAnimation (context ,R.anim.scale ) );
                        imageView.setVisibility ( View.INVISIBLE );
                        disable ();
                        new Handler (  ).postDelayed ( new Runnable () {
                            @Override
                            public void run() {
                                second = getAdapterPosition ();
                                string2 = textView.getText ().toString ();
                                if (string1.equals ( string2 )){
                                    button.setVisibility ( View.INVISIBLE );
                                    myViewHolders.get ( first ).button.setVisibility ( View.INVISIBLE );
                                    mediaPlayer2.start ();
                                    List<Integer> vis = new ArrayList<> (  );
                                    for (int i = 0; i < myViewHolders.size (); i++) {
                                        int visibility =  myViewHolders.get ( i ).button.getVisibility ();
                                        if (visibility == View.INVISIBLE){
                                            vis.add ( i );
                                        }
                                    }

                                    if (vis.size () == count ){
                                        if (GameOnePlay.countDownTimer != null){
                                            GameOnePlay.countDownTimer.cancel ();
                                        }
                                        mediaPlayer4.start ();
                                        final AlertDialog.Builder builder = new AlertDialog.Builder ( context,R.style.CustomDialog );
                                        View view = LayoutInflater.from ( context ).inflate ( R.layout.alphabet_write_dialog,null,false );
                                        Button yes = view.findViewById ( R.id.yes );
                                        Button no = view.findViewById ( R.id.no );
                                        TextView firstText = view.findViewById ( R.id.firstText );
                                        TextView secondText = view.findViewById ( R.id.secontText );
                                        ImageView dialogImage = view.findViewById ( R.id.dialog_image );

                                        final AlertDialog alertDialog = builder.create ();
                                        alertDialog.setView ( view );

                                        if (Integer.parseInt ( timer.getText ().toString () )>=75 && GameOnePlay.id != 6){
                                            firstText.setText ( "Excellent work !" );
                                            secondText.setText ( "Do you wanna continue?" );
                                            dialogImage.setImageResource ( R.drawable.starthree );
                                            cursor.moveToPosition ( GameOnePlay.id - 1 );
                                            if (Integer.parseInt ( cursor.getString ( 1 ) ) < 3){
                                                level.update ( GameOnePlay.id,"3" );
                                            }
                                            yes.setOnClickListener ( new View.OnClickListener () {
                                                @Override
                                                public void onClick( View v ) {
                                                    mediaPlayer3.start ();
                                                    intent ();
                                                }
                                            } );
                                        }
                                        else if (Integer.parseInt ( timer.getText ().toString () ) >= 20 && GameOnePlay.id != 6){
                                            firstText.setText ( "Good work !" );
                                            secondText.setText ( "Do you wanna continue?" );
                                            dialogImage.setImageResource ( R.drawable.startwo );
                                            cursor.moveToPosition ( GameOnePlay.id - 1 );
                                            if (Integer.parseInt ( cursor.getString ( 1 ) ) < 2){
                                                level.update ( GameOnePlay.id,"2" );
                                            }
                                            yes.setOnClickListener ( new View.OnClickListener () {
                                                @Override
                                                public void onClick( View v ) {
                                                    mediaPlayer3.start ();
                                                    intent ();
                                                }
                                            } );
                                        }
                                        else {
                                            if (GameOnePlay.id == 6 && Integer.parseInt ( timer.getText ().toString () )>=75){
                                                firstText.setText ( "Excellent work !" );
                                                secondText.setText ( "Start fresh?" );
                                                dialogImage.setImageResource ( R.drawable.starthree );
                                                cursor.moveToPosition ( GameOnePlay.id - 1 );
                                                if (Integer.parseInt ( cursor.getString ( 1 ) ) < 3){
                                                    level.update ( GameOnePlay.id,"3" );
                                                }
                                                yes.setOnClickListener ( new View.OnClickListener () {
                                                    @Override
                                                    public void onClick( View v ) {
                                                        mediaPlayer3.start ();
                                                        intent ();
                                                    }
                                                } );
                                            }
                                            else if (GameOnePlay.id == 6 && Integer.parseInt ( timer.getText ().toString () )>=20){
                                                firstText.setText ( "Good work !" );
                                                secondText.setText ( "Start fresh?" );
                                                dialogImage.setImageResource ( R.drawable.startwo );
                                                cursor.moveToPosition ( GameOnePlay.id - 1 );
                                                if (Integer.parseInt ( cursor.getString ( 1 ) ) < 2){
                                                    level.update ( GameOnePlay.id,"2" );
                                                }
                                                yes.setOnClickListener ( new View.OnClickListener () {
                                                    @Override
                                                    public void onClick( View v ) {
                                                        mediaPlayer3.start ();
                                                        intent ();
                                                    }
                                                } );
                                            }
                                            else {
                                                firstText.setText ( "Poor work" );
                                                secondText.setText ( "Do you wanna Retry?" );
                                                dialogImage.setImageResource ( R.drawable.starone );
                                                cursor.moveToPosition ( GameOnePlay.id - 1 );
                                                if (Integer.parseInt ( cursor.getString ( 1 ) ) < 1){
                                                    level.update ( GameOnePlay.id,"1" );
                                                }
                                                yes.setOnClickListener ( new View.OnClickListener () {
                                                    @Override
                                                    public void onClick( View v ) {
                                                        mediaPlayer3.start ();
                                                        (( Activity )context).recreate ();
                                                    }
                                                } );
                                            }
                                        }

                                        no.setOnClickListener ( new View.OnClickListener () {
                                            @Override
                                            public void onClick( View v ) {
                                                mediaPlayer3.start ();
                                                (( Activity )context).finish ();
                                                Intent intent = new Intent ( context , GameOneActivity.class );
                                                intent.setFlags ( Intent.FLAG_ACTIVITY_NEW_TASK );
                                                context.startActivity ( intent );
                                            }
                                        } );
                                        alertDialog.setCanceledOnTouchOutside ( false );
                                        alertDialog.show ();
                                    }
                                }
                                else {
                                    itemView.startAnimation( AnimationUtils.loadAnimation (context ,R.anim.scale ) );
                                    myViewHolders.get ( first ).itemView.startAnimation( AnimationUtils.loadAnimation (context ,R.anim.scale ) );
                                    imageView.setVisibility ( View.VISIBLE );
                                    myViewHolders.get ( first ).imageView.setVisibility ( View.VISIBLE );
                                    imageView.setImageResource ( R.drawable.wood);
                                    myViewHolders.get ( first ).imageView.setImageResource ( R.drawable.wood);
                                    mediaPlayer1.start ();
                                }
                                first = 100;
                                enable ();
                            }
                        },500 );
                    }
                }
            };
            button.setOnClickListener ( onClickListener );
        }

        private void enable(){
            for (int i = 0;i<myViewHolders.size ();i++){
                myViewHolders.get ( i ).button.setEnabled ( true );
            }
        }
        private void disable(){
            for (int i = 0;i<myViewHolders.size ();i++){
                myViewHolders.get ( i ).button.setEnabled ( false );
            }
        }
        private void intent(){
            Intent intent = new Intent ( context, GameOnePlay.class );
            int id = 0;
            int drawable = 0;
            switch (GameOnePlay.id){
                case 1:
                    id = 2;
                    drawable = R.drawable.landtwo;
                    break;
                case 2:
                    id = 3;
                    drawable = R.drawable.landthree;
                    break;
                case 3:
                    id = 4;
                    drawable = R.drawable.landfour;
                    break;
                case 4:
                    id = 5;
                    drawable = R.drawable.landfive;
                    break;
                case 5:
                    id = 6;
                    drawable = R.drawable.landsix;
                    break;
                case 6:
                    id = 1;
                    drawable = R.drawable.landone;
                    break;
            }

            intent.putExtra ( "id",id );
            intent.putExtra ( "drawable",drawable );
            context.startActivity ( intent );
            ((Activity)context).finish ();
        }
    }
}
