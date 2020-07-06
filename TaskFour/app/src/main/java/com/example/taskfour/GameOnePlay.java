package com.example.taskfour;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taskfour.Adapters.GameOneAdapter;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameOnePlay extends AppCompatActivity {

    public static int id;
    private int drawable;
    private RecyclerView recyclerView11;
    private List<String> numbers;
    private List<String> allNum = new ArrayList<> (  );
    private static TextView timer,introCountdown;
    private Button play,reset,exit;
    private RelativeLayout relativeLayout;
    public static CountDownTimer countDownTimer,countDownTimer1;
    private CardView cexit,cplay,creset;
    private MediaPlayer mediaPlayer,mediaPlayer1;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        requestWindowFeature( Window.FEATURE_NO_TITLE);
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        setContentView ( R.layout.activity_game_one_play );
        id = getIntent ().getIntExtra ( "id",0 );
        drawable = getIntent ().getIntExtra ( "drawable",0 );
        ImageView imageView = findViewById ( R.id.game_one_play_container );
        imageView.setBackgroundResource ( drawable );

        timer = findViewById ( R.id.timer );
        introCountdown = findViewById ( R.id.intro_countdown );
        play = findViewById ( R.id.play );
        reset = findViewById ( R.id.reset );
        exit = findViewById ( R.id.exit );
        relativeLayout = findViewById ( R.id.countdownl_container );

        cexit = findViewById ( R.id.exitcard );
        cplay = findViewById ( R.id.cardplay );
        creset = findViewById ( R.id.cardreset );

        mediaPlayer = new MediaPlayer ().create(getApplicationContext (),R.raw.rubberone);
        mediaPlayer1 = new MediaPlayer ().create(getApplicationContext (),R.raw.negative);

        timer.setText ( "150" );
        introCountdown.setText ( "3" );

        countDownTimer = null;
        countDownTimer1 = null;

        recyclerView11 = findViewById ( R.id.game_one_recycler_11 );
        recyclerView11.setHasFixedSize ( true );
        recyclerView11.setVisibility ( View.INVISIBLE );

        for (int i = 1;i<=99;i++){
            allNum.add ( String.valueOf ( i ) );
            Collections.shuffle ( allNum );
        }

        if (id == 1){
            numbers = new ArrayList<> (  );
            for (int i = 1;i<=4;i++){
                int index = (int)Math.random ()*(allNum.size ()-1) ;
                numbers.add ( allNum.get ( index ));
                numbers.add ( allNum.get ( index ));
                allNum.remove ( index );
            }
            Collections.shuffle ( numbers );
            recyclerView11.setLayoutManager ( new GridLayoutManager ( GameOnePlay.this,2 ) );
            recyclerView11.setAdapter ( new GameOneAdapter ( GameOnePlay.this,8,numbers,timer ) );
        }
        else if (id == 2){
            numbers = new ArrayList<> (  );
            for (int i = 1;i<=6;i++){
                int index = (int)Math.random ()*(allNum.size ()-1) ;
                numbers.add ( allNum.get ( index ));
                numbers.add ( allNum.get ( index ));
                allNum.remove ( index );
            }
            Collections.shuffle ( numbers );
            recyclerView11.setLayoutManager ( new GridLayoutManager ( GameOnePlay.this,3 ) );
            recyclerView11.setAdapter ( new GameOneAdapter ( GameOnePlay.this,12,numbers,timer ) );
        }
        else if (id == 3){
            numbers = new ArrayList<> (  );
            for (int i = 1;i<=8;i++){
                int index = (int)Math.random ()*(allNum.size ()-1) ;
                numbers.add ( allNum.get ( index ));
                numbers.add ( allNum.get ( index ));
                allNum.remove ( index );
            }
            Collections.shuffle ( numbers );
            recyclerView11.setLayoutManager ( new GridLayoutManager ( GameOnePlay.this,4 ) );
            recyclerView11.setAdapter ( new GameOneAdapter ( GameOnePlay.this,16,numbers,timer ) );
        }
        else if (id == 4){
            numbers = new ArrayList<> (  );
            for (int i = 1;i<=10;i++){
                int index = (int)Math.random ()*(allNum.size ()-1) ;
                numbers.add ( allNum.get ( index ));
                numbers.add ( allNum.get ( index ));
                allNum.remove ( index );
            }
            Collections.shuffle ( numbers );
            recyclerView11.setLayoutManager ( new GridLayoutManager ( GameOnePlay.this,5 ) );
            recyclerView11.setAdapter ( new GameOneAdapter ( GameOnePlay.this,20,numbers,timer ) );
        }
        else if (id == 5){
            numbers = new ArrayList<> (  );
            for (int i = 1;i<=12;i++){
                int index = (int)Math.random ()*(allNum.size ()-1) ;
                numbers.add ( allNum.get ( index ));
                numbers.add ( allNum.get ( index ));
                allNum.remove ( index );
            }
            Collections.shuffle ( numbers );
            recyclerView11.setLayoutManager ( new GridLayoutManager ( GameOnePlay.this,6 ) );
            recyclerView11.setAdapter ( new GameOneAdapter ( GameOnePlay.this,24,numbers,timer ) );
        }
        else if (id == 6){
            numbers = new ArrayList<> (  );
            for (int i = 1;i<=15;i++){
                int index = (int)Math.random ()*(allNum.size ()-1) ;
                numbers.add ( allNum.get ( index ));
                numbers.add ( allNum.get ( index ));
                allNum.remove ( index );
            }
            Collections.shuffle ( numbers );
            recyclerView11.setLayoutManager ( new GridLayoutManager ( GameOnePlay.this,6 ) );
            recyclerView11.setAdapter ( new GameOneAdapter ( GameOnePlay.this,30,numbers,timer ) );
        }

        View.OnClickListener onClickListener = new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                switch (v.getId ()){
                    case R.id.play:
                        mediaPlayer.start ();
                        cplay.startAnimation ( AnimationUtils.loadAnimation ( getApplicationContext (),R.anim.button ) );
                        play.setEnabled ( false );
                            countDownTimer1 = new CountDownTimer (3000,1000) {
                            @Override
                            public void onTick( long millisUntilFinished ) {
                                if (Integer.parseInt ( introCountdown.getText ().toString () ) > 0){
                                    introCountdown.setText ( String.valueOf ( Integer.parseInt ( introCountdown.getText ().toString () )-1 ) );
                                }
                            }

                            @Override
                            public void onFinish() {
                                countDownTimer  = new CountDownTimer (150000,1000) {
                                    @Override
                                    public void onTick( long millisUntilFinished ) {
                                        int time = Integer.parseInt ( timer.getText ().toString () );
                                        if (time >= 0){
                                            time = time-1;
                                            timer.setText ( String.valueOf ( time ) );
                                        }
                                    }

                                    @Override
                                    public void onFinish() {
                                        mediaPlayer1.start ();
                                        final AlertDialog.Builder builder = new AlertDialog.Builder ( GameOnePlay.this,R.style.CustomDialog );
                                        View view = LayoutInflater.from ( GameOnePlay.this ).inflate ( R.layout.alphabet_write_dialog,null,false );
                                        Button yes = view.findViewById ( R.id.yes );
                                        Button no = view.findViewById ( R.id.no );
                                        TextView firstText = view.findViewById ( R.id.firstText );
                                        TextView secondText = view.findViewById ( R.id.secontText );
                                        ImageView dialogImage = view.findViewById ( R.id.dialog_image );

                                        firstText.setText ( "Out of time..." );
                                        secondText.setText ( "Do you wanna retry ?" );
                                        dialogImage.setImageResource ( R.drawable.timeout );
                                        final AlertDialog alertDialog = builder.create ();
                                        alertDialog.setView ( view );

                                        no.setOnClickListener ( new View.OnClickListener () {
                                            @Override
                                            public void onClick( View v ) {
                                                mediaPlayer.start ();
                                                finish ();
                                            }
                                        } );

                                        yes.setOnClickListener ( new View.OnClickListener () {
                                            @Override
                                            public void onClick( View v ) {
                                                mediaPlayer.start ();
                                                finish ();
                                                Intent intent = new Intent ( getApplicationContext (), GameOnePlay.class );
                                                intent.putExtra ( "id",id );
                                                intent.putExtra ( "drawable",drawable );
                                                intent.setFlags ( Intent.FLAG_ACTIVITY_NEW_TASK );
                                                startActivity ( intent );
                                            }
                                        } );
                                        alertDialog.setCanceledOnTouchOutside ( false );
                                        alertDialog.show ();
                                    }
                                };
                                countDownTimer.start ();
                                relativeLayout.setVisibility ( View.INVISIBLE );
                                play.setVisibility ( View.INVISIBLE );
                                recyclerView11.setVisibility ( View.VISIBLE );
                            }
                        }.start ();
                        break;
                    case R.id.reset:
                        mediaPlayer.start ();
                        final String last = timer.getText ().toString ();
                        final String lastIntro = introCountdown.getText ().toString ();

                        if (play.getVisibility () == View.VISIBLE && countDownTimer1 != null){
                            countDownTimer1.cancel ();
                        }
                        if (play.getVisibility () == View.INVISIBLE && countDownTimer != null){
                            countDownTimer.cancel ();
                        }
                        creset.startAnimation ( AnimationUtils.loadAnimation ( getApplicationContext (),R.anim.button ) );
                        final AlertDialog.Builder builder2 = new AlertDialog.Builder ( GameOnePlay.this,R.style.CustomDialog );
                        View view2 = LayoutInflater.from ( GameOnePlay.this ).inflate ( R.layout.alphabet_write_dialog,null,false );
                        Button yes2 = view2.findViewById ( R.id.yes );
                        Button no2 = view2.findViewById ( R.id.no );
                        TextView firstText2 = view2.findViewById ( R.id.firstText );
                        TextView secondText2 = view2.findViewById ( R.id.secontText );
                        ImageView dialogImage2 = view2.findViewById ( R.id.dialog_image );

                        firstText2.setText ( "Are you sure !" );
                        secondText2.setText ( "Do you want to reset?" );

                        dialogImage2.setImageResource ( R.drawable.omg );
                        final AlertDialog alertDialog2 = builder2.create ();
                        alertDialog2.setView ( view2 );
                        mediaPlayer1.start ();
                        no2.setOnClickListener ( new View.OnClickListener () {
                            @Override
                            public void onClick( View v ) {
                                mediaPlayer.start ();
                                if (play.getVisibility () == View.VISIBLE && countDownTimer1 != null){
                                    introCountdown.setText ( lastIntro );
                                    countDownTimer1.start ();
                                }
                                else if (play.getVisibility () == View.INVISIBLE && countDownTimer != null){
                                    timer.setText ( last );
                                    countDownTimer.start ();
                                }
                                alertDialog2.cancel ();
                                full ();
                            }
                        } );

                        yes2.setOnClickListener ( new View.OnClickListener () {
                            @Override
                            public void onClick( View v ) {
                                mediaPlayer.start ();
                                finish ();
                                Intent intent = new Intent ( getApplicationContext (),GameOnePlay.class );
                                intent.putExtra ( "id",id );
                                intent.putExtra ( "drawable",drawable );
                                intent.setFlags ( Intent.FLAG_ACTIVITY_NEW_TASK );
                                startActivity ( intent );
                            }
                        } );
                        alertDialog2.setCanceledOnTouchOutside ( false );
                        alertDialog2.show ();
                        break;
                    case R.id.exit:
                        mediaPlayer.start ();
                        cexit.startAnimation ( AnimationUtils.loadAnimation ( getApplicationContext (),R.anim.button ) );
                        exit ();
                        break;
                }
            }
        };

        play.setOnClickListener ( onClickListener );
        reset.setOnClickListener ( onClickListener );
        exit.setOnClickListener ( onClickListener );

    }

    @Override
    public void onBackPressed() {
        exit();
    }

    @Override
    protected void onPause() {
        super.onPause ();
        overridePendingTransition ( android.R.anim.fade_in,android.R.anim.fade_out );
        if (play.getVisibility () == View.VISIBLE && countDownTimer1 != null){
            countDownTimer1.cancel ();
        }
        if (play.getVisibility () == View.INVISIBLE && countDownTimer != null){
            countDownTimer.cancel ();
        }
    }

    @Override
    protected void onResume() {
        super.onResume ();
        if (play.getVisibility () == View.VISIBLE && countDownTimer1 != null){
            countDownTimer1.start ();
        }
        if (play.getVisibility () == View.INVISIBLE && countDownTimer != null){
            countDownTimer.start ();
        }
        full ();
    }

    private void exit(){
        mediaPlayer1.start ();
        final String last = timer.getText ().toString ();
        final String lastIntro = introCountdown.getText ().toString ();

        if (play.getVisibility () == View.VISIBLE && countDownTimer1 != null){
            countDownTimer1.cancel ();
        }
        if (play.getVisibility () == View.INVISIBLE && countDownTimer != null){
            countDownTimer.cancel ();
        }

        final AlertDialog.Builder builder = new AlertDialog.Builder ( GameOnePlay.this,R.style.CustomDialog );
        View view = LayoutInflater.from ( GameOnePlay.this ).inflate ( R.layout.alphabet_write_dialog,null,false );
        Button yes = view.findViewById ( R.id.yes );
        Button no = view.findViewById ( R.id.no );
        TextView firstText = view.findViewById ( R.id.firstText );
        TextView secondText = view.findViewById ( R.id.secontText );
        ImageView dialogImage = view.findViewById ( R.id.dialog_image );

        firstText.setText ( "Oh noooo !" );
        secondText.setText ( "Do you wanna exit?" );
        dialogImage.setImageResource ( R.drawable.oh );
        final AlertDialog alertDialog = builder.create ();
        alertDialog.setView ( view );

        no.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                mediaPlayer.start ();
                if (play.getVisibility () == View.VISIBLE && countDownTimer1 != null){
                    introCountdown.setText ( lastIntro );
                    countDownTimer1.start ();
                }
                else if (play.getVisibility () == View.INVISIBLE && countDownTimer != null){
                    timer.setText ( last );
                    countDownTimer.start ();
                }
                alertDialog.cancel ();
                full ();
            }
        } );

        yes.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                mediaPlayer.start ();
                finish ();
                Intent intent = new Intent ( getApplicationContext (), GameOneActivity.class );
                intent.setFlags ( Intent.FLAG_ACTIVITY_NEW_TASK );
                startActivity ( intent );
            }
        } );
        alertDialog.setCanceledOnTouchOutside ( false );
        alertDialog.show ();
    }
    private void full(){
        final View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }
}
