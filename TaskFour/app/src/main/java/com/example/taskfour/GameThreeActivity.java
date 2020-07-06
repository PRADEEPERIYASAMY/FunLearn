package com.example.taskfour;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
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

import java.util.ArrayList;
import java.util.List;

public class GameThreeActivity extends AppCompatActivity {

    private Button b1,b2,b3,b4,b5,b6,b7,b8,b9;
    private CardView c1,c2,c3,c4,c5,c6,c7,c8,c9,sample,sample1;
    private ImageView i1,i2,i3,i4,i5,i6,i7,i8,i9;
    private static List<Button> buttons = new ArrayList<> (  );
    private static List<CardView> cardViews = new ArrayList<> (  );
    private static List<ImageView> imageViews = new ArrayList<> (  );
    private MediaPlayer mediaPlayer,mediaPlayer1,mediaPlayer2;
    private static CountDownTimer countDownTimer1;
    private Button play,reset,exit;
    private static TextView timer,introCountdown;
    private RelativeLayout relativeLayout;
    private ConstraintLayout constraintLayout;
    private CardView cexit,cplay,creset;

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
        setContentView ( R.layout.activity_game_three );

        imageViews.clear ();
        buttons.clear ();
        cardViews.clear ();

        cexit = findViewById ( R.id.exitcard );
        cplay = findViewById ( R.id.cardplay );
        creset = findViewById ( R.id.cardreset );

        timer = findViewById ( R.id.timer );
        introCountdown = findViewById ( R.id.intro_countdown );
        play = findViewById ( R.id.play );
        reset = findViewById ( R.id.reset );
        exit = findViewById ( R.id.exit );
        relativeLayout = findViewById ( R.id.countdownl_container );
        constraintLayout = findViewById ( R.id.game_one_layout_one );
        constraintLayout.setVisibility ( View.INVISIBLE );

        b1 = findViewById ( R.id.b1 );
        buttons.add ( b1 );
        b2 = findViewById ( R.id.b2 );
        buttons.add ( b2 );
        b3 = findViewById ( R.id.b3 );
        buttons.add ( b3 );
        b4 = findViewById ( R.id.b4 );
        buttons.add ( b4 );
        b5 = findViewById ( R.id.b5 );
        buttons.add ( b5 );
        b6 = findViewById ( R.id.b6 );
        buttons.add ( b6 );
        b7 = findViewById ( R.id.b7 );
        buttons.add ( b7 );
        b8 = findViewById ( R.id.b8 );
        buttons.add ( b8 );
        b9 = findViewById ( R.id.b9 );
        buttons.add ( b9 );

        i1 = findViewById ( R.id.i1 );
        imageViews.add ( i1 );
        i2 = findViewById ( R.id.i2 );
        imageViews.add ( i2 );
        i3 = findViewById ( R.id.i3 );
        imageViews.add ( i3 );
        i4 = findViewById ( R.id.i4 );
        imageViews.add ( i4 );
        i5 = findViewById ( R.id.i5 );
        imageViews.add ( i5 );
        i6 = findViewById ( R.id.i6 );
        imageViews.add ( i6 );
        i7 = findViewById ( R.id.i7 );
        imageViews.add ( i7 );
        i8 = findViewById ( R.id.i8 );
        imageViews.add ( i8 );
        i9 = findViewById ( R.id.i9 );
        imageViews.add ( i9 );

        c1 = findViewById ( R.id.c1 );
        cardViews.add ( c1 );
        c2 = findViewById ( R.id.c2 );
        cardViews.add ( c2 );
        c3 = findViewById ( R.id.c3 );
        cardViews.add ( c3 );
        c4 = findViewById ( R.id.c4 );
        cardViews.add ( c4 );
        c5 = findViewById ( R.id.c5 );
        cardViews.add ( c5 );
        c6 = findViewById ( R.id.c6 );
        cardViews.add ( c6 );
        c7 = findViewById ( R.id.c7 );
        cardViews.add ( c7 );
        c8 = findViewById ( R.id.c8 );
        cardViews.add ( c8 );
        c9 = findViewById ( R.id.c9 );
        cardViews.add ( c9 );
        sample = findViewById ( R.id.sample );
        sample1 = findViewById ( R.id.sample1 );
        sample1.setCardBackgroundColor ( Color.rgb ( 255,255,254 ) );
        mediaPlayer = new MediaPlayer ().create(getApplicationContext (),R.raw.tictac);
        mediaPlayer1 = new MediaPlayer ().create(getApplicationContext (),R.raw.rubberone);
        mediaPlayer2 = new MediaPlayer ().create(getApplicationContext (),R.raw.negative);
        countDownTimer1 = null;

        View.OnClickListener onClickListener = new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                mediaPlayer.start ();

                if (buttons.size () >0){
                    timer.setText ( "Me" );
                }
                v.setVisibility ( View.INVISIBLE );
                switch (v.getId ()){
                    case R.id.b1:
                        player ( i1,c1,b1 );
                        break;
                    case R.id.b2:
                        player ( i2,c2,b2 );
                        break;
                    case R.id.b3:
                        player ( i3,c3,b3 );
                        break;
                    case R.id.b4:
                        player ( i4,c4,b4 );
                        break;
                    case R.id.b5:
                        player ( i5,c5,b5 );
                        break;
                    case R.id.b6:
                        player ( i6,c6,b6 );
                        break;
                    case R.id.b7:
                        player ( i7,c7,b7 );
                        break;
                    case R.id.b8:
                        player ( i8,c8,b8 );
                        break;
                    case R.id.b9:
                        player ( i9,c9,b9 );
                        break;
                }
                checker ();
                disable ();
                opponent ();
            }
        };

        View.OnClickListener onClickListener1 = new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                switch (v.getId ()){
                    case R.id.play:
                        mediaPlayer1.start ();
                        cplay.startAnimation ( AnimationUtils.loadAnimation ( getApplicationContext (),R.anim.button ) );
                        introCountdown.setText ( "3" );
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
                                relativeLayout.setVisibility ( View.INVISIBLE );
                                play.setVisibility ( View.INVISIBLE );
                                constraintLayout.setVisibility ( View.VISIBLE );
                            }
                        }.start ();
                        break;
                    case R.id.reset:
                        mediaPlayer1.start ();
                        creset.startAnimation ( AnimationUtils.loadAnimation ( getApplicationContext (),R.anim.button ) );
                        final AlertDialog.Builder builder2 = new AlertDialog.Builder ( GameThreeActivity.this,R.style.CustomDialog );
                        View view2 = LayoutInflater.from ( GameThreeActivity.this ).inflate ( R.layout.alphabet_write_dialog,null,false );
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
                        mediaPlayer2.start ();

                        no2.setOnClickListener ( new View.OnClickListener () {
                            @Override
                            public void onClick( View v ) {
                                mediaPlayer1.start ();
                                alertDialog2.cancel ();
                                full ();
                            }
                        } );

                        yes2.setOnClickListener ( new View.OnClickListener () {
                            @Override
                            public void onClick( View v ) {
                                mediaPlayer1.start ();
                                finish ();
                                Intent intent = new Intent ( GameThreeActivity.this,GameThreeActivity.class );
                                startActivity ( intent );
                            }
                        } );
                        alertDialog2.setCanceledOnTouchOutside ( false );
                        alertDialog2.show ();
                        break;
                    case R.id.exit:
                        mediaPlayer1.start ();
                        cexit.startAnimation ( AnimationUtils.loadAnimation ( getApplicationContext (),R.anim.button ) );
                        exit ();
                        break;
                }
            }
        };

        play.setOnClickListener ( onClickListener1 );
        reset.setOnClickListener ( onClickListener1 );
        exit.setOnClickListener ( onClickListener1 );

        for (int i =0;i<9;i++){
            buttons.get ( i ).setOnClickListener ( onClickListener );
        }

    }

    private void opponent(){
        int delay = (int) (Math.random ()*3000+4);
        new Handler ( ).postDelayed ( new Runnable () {
            @Override
            public void run() {
                enable ();
                if (buttons.size () >0){
                    if (buttons.size () == 8){
                        if (b1.getVisibility () == View.INVISIBLE){
                            int choice = (int) (Math.random ()*2)+1;
                            if (choice == 1){
                                opponenthelper ( b2 );
                            }
                            else if (choice == 2){
                                opponenthelper ( b4 );
                            }
                            else if (choice == 3){
                                opponenthelper ( b5 );
                            }
                        }
                        else if (b2.getVisibility () == View.INVISIBLE){
                            int choice = (int) (Math.random ()*4)+1;
                            if (choice == 1){
                                opponenthelper ( b1 );
                            }
                            else if (choice == 2){
                                opponenthelper ( b3 );
                            }
                            else if (choice == 3){
                                opponenthelper ( b4 );
                            }
                            else if (choice == 4){
                                opponenthelper ( b5 );
                            }
                            else if (choice == 5){
                                opponenthelper ( b6 );
                            }
                        }
                        else if (b3.getVisibility () == View.INVISIBLE){
                            int choice = (int) (Math.random ()*2)+1;
                            if (choice == 1){
                                opponenthelper ( b2 );
                            }
                            else if (choice == 2){
                                opponenthelper ( b5 );
                            }
                            else if (choice == 3){
                                opponenthelper ( b6 );
                            }
                        }
                        else if (b4.getVisibility () == View.INVISIBLE){
                            int choice = (int) (Math.random ()*4)+1;
                            if (choice == 1){
                                opponenthelper ( b1 );
                            }
                            else if (choice == 2){
                                opponenthelper ( b2 );
                            }
                            else if (choice == 3){
                                opponenthelper ( b5 );
                            }
                            else if (choice == 4){
                                opponenthelper ( b8 );
                            }
                            else if (choice == 5){
                                opponenthelper ( b7 );
                            }
                        }
                        else if (b5.getVisibility () == View.INVISIBLE){
                            int choice = (int) (Math.random ()*7);
                            opponenthelper ( buttons.get ( choice ) );
                        }
                        else if (b6.getVisibility () == View.INVISIBLE){
                            int choice = (int) (Math.random ()*4)+1;
                            if (choice == 1){
                                opponenthelper ( b2 );
                            }
                            else if (choice == 2){
                                opponenthelper ( b3 );
                            }
                            else if (choice == 3){
                                opponenthelper ( b5 );
                            }
                            else if (choice == 4){
                                opponenthelper ( b8 );
                            }
                            else if (choice == 5){
                                opponenthelper ( b9 );
                            }
                        }
                        else if (b7.getVisibility () == View.INVISIBLE){
                            int choice = (int) (Math.random ()*2)+1;
                            if (choice == 1){
                                opponenthelper ( b4 );
                            }
                            else if (choice == 2){
                                opponenthelper ( b5 );
                            }
                            else if (choice == 3){
                                opponenthelper ( b8 );
                            }
                        }
                        else if (b8.getVisibility () == View.INVISIBLE){
                            int choice = (int) (Math.random ()*4)+1;
                            if (choice == 1){
                                opponenthelper ( b4 );
                            }
                            else if (choice == 2){
                                opponenthelper ( b5 );
                            }
                            else if (choice == 3){
                                opponenthelper ( b6 );
                            }
                            else if (choice == 4){
                                opponenthelper ( b7 );
                            }
                            else if (choice == 5){
                                opponenthelper ( b9 );
                            }
                        }
                        else if (b9.getVisibility () == View.INVISIBLE){
                            int choice = (int) (Math.random ()*2)+1;
                            if (choice == 1){
                                opponenthelper ( b5 );
                            }
                            else if (choice == 2){
                                opponenthelper ( b6 );
                            }
                            else if (choice == 3){
                                opponenthelper ( b8 );
                            }
                        }
                        else {
                            int choice = (int) (Math.random ()*7);
                            opponenthelper ( buttons.get ( choice ) );
                        }
                    }
                    else if (buttons.size () == 6 ){
                        if (c1.getCardBackgroundColor () == sample1.getCardBackgroundColor () && c2.getCardBackgroundColor () == sample1.getCardBackgroundColor () && b3.getVisibility () == View.VISIBLE){
                            opponenthelper ( b3 );
                        }
                        else if (c1.getCardBackgroundColor () == sample1.getCardBackgroundColor () && c4.getCardBackgroundColor () == sample1.getCardBackgroundColor () && b7.getVisibility () == View.VISIBLE){
                            opponenthelper ( b7 );
                        }
                        else if (c1.getCardBackgroundColor () == sample1.getCardBackgroundColor () && c5.getCardBackgroundColor () == sample1.getCardBackgroundColor () && b9.getVisibility () == View.VISIBLE){
                            opponenthelper ( b9 );
                        }
                        else if (c3.getCardBackgroundColor () == sample1.getCardBackgroundColor () && c2.getCardBackgroundColor () == sample1.getCardBackgroundColor () && b1.getVisibility () == View.VISIBLE){
                            opponenthelper ( b1 );
                        }
                        else if (c3.getCardBackgroundColor () == sample1.getCardBackgroundColor () && c5.getCardBackgroundColor () == sample1.getCardBackgroundColor () && b7.getVisibility () == View.VISIBLE){
                            opponenthelper ( b7 );
                        }
                        else if (c3.getCardBackgroundColor () == sample1.getCardBackgroundColor () && c6.getCardBackgroundColor () == sample1.getCardBackgroundColor () && b9.getVisibility () == View.VISIBLE){
                            opponenthelper ( b9 );
                        }
                        else if (c7.getCardBackgroundColor () == sample1.getCardBackgroundColor () && c4.getCardBackgroundColor () == sample1.getCardBackgroundColor () && b1.getVisibility () == View.VISIBLE){
                            opponenthelper ( b1 );
                        }
                        else if (c7.getCardBackgroundColor () == sample1.getCardBackgroundColor () && c5.getCardBackgroundColor () == sample1.getCardBackgroundColor () && b3.getVisibility () == View.VISIBLE){
                            opponenthelper ( b3 );
                        }
                        else if (c7.getCardBackgroundColor () == sample1.getCardBackgroundColor () && c8.getCardBackgroundColor () == sample1.getCardBackgroundColor () && b9.getVisibility () == View.VISIBLE){
                            opponenthelper ( b9 );
                        }
                        else if (c9.getCardBackgroundColor () == sample1.getCardBackgroundColor () && c5.getCardBackgroundColor () == sample1.getCardBackgroundColor () && b1.getVisibility () == View.VISIBLE){
                            opponenthelper ( b1 );
                        }
                        else if (c9.getCardBackgroundColor () == sample1.getCardBackgroundColor () && c6.getCardBackgroundColor () == sample1.getCardBackgroundColor () && b3.getVisibility () == View.VISIBLE){
                            opponenthelper ( b3 );
                        }
                        else if (c9.getCardBackgroundColor () == sample1.getCardBackgroundColor () && c8.getCardBackgroundColor () == sample1.getCardBackgroundColor () && b7.getVisibility () == View.VISIBLE){
                            opponenthelper ( b7 );
                        }
                        else if (c2.getCardBackgroundColor () == sample1.getCardBackgroundColor () && c5.getCardBackgroundColor () == sample1.getCardBackgroundColor () && b8.getVisibility () == View.VISIBLE){
                            opponenthelper ( b8 );
                        }
                        else if (c4.getCardBackgroundColor () == sample1.getCardBackgroundColor () && c5.getCardBackgroundColor () == sample1.getCardBackgroundColor () && b6.getVisibility () == View.VISIBLE){
                            opponenthelper ( b6 );
                        }
                        else if (c6.getCardBackgroundColor () == sample1.getCardBackgroundColor () && c5.getCardBackgroundColor () == sample1.getCardBackgroundColor () && b4.getVisibility () == View.VISIBLE){
                            opponenthelper ( b4 );
                        }
                        else if (c8.getCardBackgroundColor () == sample1.getCardBackgroundColor () && c5.getCardBackgroundColor () == sample1.getCardBackgroundColor () && b2.getVisibility () == View.VISIBLE){
                            opponenthelper ( b2 );
                        }
                        else if (c1.getCardBackgroundColor () == sample1.getCardBackgroundColor () && c9.getCardBackgroundColor () == sample1.getCardBackgroundColor () && b5.getVisibility () == View.VISIBLE){
                            opponenthelper ( b5 );
                        }
                        else if (c3.getCardBackgroundColor () == sample1.getCardBackgroundColor () && c7.getCardBackgroundColor () == sample1.getCardBackgroundColor () && b5.getVisibility () == View.VISIBLE){
                            opponenthelper ( b5 );
                        }
                        else if (c2.getCardBackgroundColor () == sample1.getCardBackgroundColor () && c8.getCardBackgroundColor () == sample1.getCardBackgroundColor () && b5.getVisibility () == View.VISIBLE){
                            opponenthelper ( b5 );
                        }
                        else if (c4.getCardBackgroundColor () == sample1.getCardBackgroundColor () && c6.getCardBackgroundColor () == sample1.getCardBackgroundColor () && b5.getVisibility () == View.VISIBLE){
                            opponenthelper ( b5 );
                        }
                        else if (c1.getCardBackgroundColor () == sample1.getCardBackgroundColor () && c7.getCardBackgroundColor () == sample1.getCardBackgroundColor () && b4.getVisibility () == View.VISIBLE){
                            opponenthelper ( b4 );
                        }
                        else if (c7.getCardBackgroundColor () == sample1.getCardBackgroundColor () && c9.getCardBackgroundColor () == sample1.getCardBackgroundColor () && b8.getVisibility () == View.VISIBLE){
                            opponenthelper ( b8 );
                        }
                        else if (c9.getCardBackgroundColor () == sample1.getCardBackgroundColor () && c3.getCardBackgroundColor () == sample1.getCardBackgroundColor () && b6.getVisibility () == View.VISIBLE){
                            opponenthelper ( b6 );
                        }
                        else if (c3.getCardBackgroundColor () == sample1.getCardBackgroundColor () && c1.getCardBackgroundColor () == sample1.getCardBackgroundColor () && b2.getVisibility () == View.VISIBLE){
                            opponenthelper ( b2 );
                        }
                        else {
                            int choice = (int) (Math.random ()*5);
                            opponenthelper ( buttons.get ( choice ) );
                        }
                    }
                    else if (buttons.size () == 4 ){
                        if (c1.getCardBackgroundColor () == sample1.getCardBackgroundColor () && c2.getCardBackgroundColor () == sample1.getCardBackgroundColor () && b3.getVisibility () == View.VISIBLE){
                            opponenthelper ( b3 );
                        }
                        else if (c1.getCardBackgroundColor () == sample1.getCardBackgroundColor () && c4.getCardBackgroundColor () == sample1.getCardBackgroundColor () && b7.getVisibility () == View.VISIBLE){
                            opponenthelper ( b7 );
                        }
                        else if (c1.getCardBackgroundColor () == sample1.getCardBackgroundColor () && c5.getCardBackgroundColor () == sample1.getCardBackgroundColor () && b9.getVisibility () == View.VISIBLE){
                            opponenthelper ( b9 );
                        }
                        else if (c3.getCardBackgroundColor () == sample1.getCardBackgroundColor () && c2.getCardBackgroundColor () == sample1.getCardBackgroundColor () && b1.getVisibility () == View.VISIBLE){
                            opponenthelper ( b1 );
                        }
                        else if (c3.getCardBackgroundColor () == sample1.getCardBackgroundColor () && c5.getCardBackgroundColor () == sample1.getCardBackgroundColor () && b7.getVisibility () == View.VISIBLE){
                            opponenthelper ( b7 );
                        }
                        else if (c3.getCardBackgroundColor () == sample1.getCardBackgroundColor () && c6.getCardBackgroundColor () == sample1.getCardBackgroundColor () && b9.getVisibility () == View.VISIBLE){
                            opponenthelper ( b9 );
                        }
                        else if (c7.getCardBackgroundColor () == sample1.getCardBackgroundColor () && c4.getCardBackgroundColor () == sample1.getCardBackgroundColor () && b1.getVisibility () == View.VISIBLE){
                            opponenthelper ( b1 );
                        }
                        else if (c7.getCardBackgroundColor () == sample1.getCardBackgroundColor () && c5.getCardBackgroundColor () == sample1.getCardBackgroundColor () && b3.getVisibility () == View.VISIBLE){
                            opponenthelper ( b3 );
                        }
                        else if (c7.getCardBackgroundColor () == sample1.getCardBackgroundColor () && c8.getCardBackgroundColor () == sample1.getCardBackgroundColor () && b9.getVisibility () == View.VISIBLE){
                            opponenthelper ( b9 );
                        }
                        else if (c9.getCardBackgroundColor () == sample1.getCardBackgroundColor () && c5.getCardBackgroundColor () == sample1.getCardBackgroundColor () && b1.getVisibility () == View.VISIBLE){
                            opponenthelper ( b1 );
                        }
                        else if (c9.getCardBackgroundColor () == sample1.getCardBackgroundColor () && c6.getCardBackgroundColor () == sample1.getCardBackgroundColor () && b3.getVisibility () == View.VISIBLE){
                            opponenthelper ( b3 );
                        }
                        else if (c9.getCardBackgroundColor () == sample1.getCardBackgroundColor () && c8.getCardBackgroundColor () == sample1.getCardBackgroundColor () && b7.getVisibility () == View.VISIBLE){
                            opponenthelper ( b7 );
                        }
                        else if (c2.getCardBackgroundColor () == sample1.getCardBackgroundColor () && c5.getCardBackgroundColor () == sample1.getCardBackgroundColor () && b8.getVisibility () == View.VISIBLE){
                            opponenthelper ( b8 );
                        }
                        else if (c4.getCardBackgroundColor () == sample1.getCardBackgroundColor () && c5.getCardBackgroundColor () == sample1.getCardBackgroundColor () && b6.getVisibility () == View.VISIBLE){
                            opponenthelper ( b6 );
                        }
                        else if (c6.getCardBackgroundColor () == sample1.getCardBackgroundColor () && c5.getCardBackgroundColor () == sample1.getCardBackgroundColor () && b4.getVisibility () == View.VISIBLE){
                            opponenthelper ( b4 );
                        }
                        else if (c8.getCardBackgroundColor () == sample1.getCardBackgroundColor () && c5.getCardBackgroundColor () == sample1.getCardBackgroundColor () && b2.getVisibility () == View.VISIBLE){
                            opponenthelper ( b2 );
                        }
                        else if (c1.getCardBackgroundColor () == sample1.getCardBackgroundColor () && c9.getCardBackgroundColor () == sample1.getCardBackgroundColor () && b5.getVisibility () == View.VISIBLE){
                            opponenthelper ( b5 );
                        }
                        else if (c3.getCardBackgroundColor () == sample1.getCardBackgroundColor () && c7.getCardBackgroundColor () == sample1.getCardBackgroundColor () && b5.getVisibility () == View.VISIBLE){
                            opponenthelper ( b5 );
                        }
                        else if (c2.getCardBackgroundColor () == sample1.getCardBackgroundColor () && c8.getCardBackgroundColor () == sample1.getCardBackgroundColor () && b5.getVisibility () == View.VISIBLE){
                            opponenthelper ( b5 );
                        }
                        else if (c4.getCardBackgroundColor () == sample1.getCardBackgroundColor () && c6.getCardBackgroundColor () == sample1.getCardBackgroundColor () && b5.getVisibility () == View.VISIBLE){
                            opponenthelper ( b5 );
                        }
                        else if (c1.getCardBackgroundColor () == sample1.getCardBackgroundColor () && c7.getCardBackgroundColor () == sample1.getCardBackgroundColor () && b4.getVisibility () == View.VISIBLE){
                            opponenthelper ( b4 );
                        }
                        else if (c7.getCardBackgroundColor () == sample1.getCardBackgroundColor () && c9.getCardBackgroundColor () == sample1.getCardBackgroundColor () && b8.getVisibility () == View.VISIBLE){
                            opponenthelper ( b8 );
                        }
                        else if (c9.getCardBackgroundColor () == sample1.getCardBackgroundColor () && c3.getCardBackgroundColor () == sample1.getCardBackgroundColor () && b6.getVisibility () == View.VISIBLE){
                            opponenthelper ( b6 );
                        }
                        else if (c3.getCardBackgroundColor () == sample1.getCardBackgroundColor () && c1.getCardBackgroundColor () == sample1.getCardBackgroundColor () && b2.getVisibility () == View.VISIBLE){
                            opponenthelper ( b2 );
                        }
                        else {
                            int choice = (int) (Math.random ()*3);
                            opponenthelper ( buttons.get ( choice ) );
                        }
                    }
                    else {
                        int choice = (int) (Math.random ()*(buttons.size ()-1));
                        opponenthelper ( buttons.get ( choice ) );
                    }
                }
                if (buttons.size () > 0  ){
                    mediaPlayer.start ();
                    timer.setText ( "you" );
                }

            }
        },delay );
    }

    private void enable(){
        for (int i = 0;i<buttons.size ();i++){
            buttons.get ( i ).setEnabled ( true );
        }
    }

    private void disable(){
        for (int i = 0;i<buttons.size ();i++){
            buttons.get ( i ).setEnabled ( false );
        }
    }
    private void Invisible(){
        b1.setVisibility ( View.INVISIBLE );
        b2.setVisibility ( View.INVISIBLE );
        b3.setVisibility ( View.INVISIBLE );
        b4.setVisibility ( View.INVISIBLE );
        b5.setVisibility ( View.INVISIBLE );
        b6.setVisibility ( View.INVISIBLE );
        b7.setVisibility ( View.INVISIBLE );
        b8.setVisibility ( View.INVISIBLE );
        b9.setVisibility ( View.INVISIBLE );
    }

    private void checker(){
        if ((c1.getCardBackgroundColor () == c2.getCardBackgroundColor () && c2.getCardBackgroundColor () == c3.getCardBackgroundColor ()) && c1.getCardBackgroundColor () != sample.getCardBackgroundColor () && c2.getCardBackgroundColor () != sample.getCardBackgroundColor () && c3.getCardBackgroundColor () != sample.getCardBackgroundColor () ){
            gamedecider ( c1,c2,c3 );
        }
        else if ((c4.getCardBackgroundColor () == c5.getCardBackgroundColor () && c5.getCardBackgroundColor () == c6.getCardBackgroundColor ()) && c4.getCardBackgroundColor () != sample.getCardBackgroundColor () && c5.getCardBackgroundColor () != sample.getCardBackgroundColor () && c6.getCardBackgroundColor () != sample.getCardBackgroundColor () ){
            gamedecider ( c4,c5,c6 );
        }
        else if ((c7.getCardBackgroundColor () == c8.getCardBackgroundColor () && c8.getCardBackgroundColor () == c9.getCardBackgroundColor ()) && c7.getCardBackgroundColor () != sample.getCardBackgroundColor () && c8.getCardBackgroundColor () != sample.getCardBackgroundColor () && c9.getCardBackgroundColor () != sample.getCardBackgroundColor () ){
            gamedecider ( c7,c8,c9 );
        }
        else if ((c1.getCardBackgroundColor () == c4.getCardBackgroundColor () && c4.getCardBackgroundColor () == c7.getCardBackgroundColor ()) && c1.getCardBackgroundColor () != sample.getCardBackgroundColor () && c4.getCardBackgroundColor () != sample.getCardBackgroundColor () && c7.getCardBackgroundColor () != sample.getCardBackgroundColor () ){
            gamedecider ( c1,c4,c7 );
        }
        else if ((c2.getCardBackgroundColor () == c5.getCardBackgroundColor () && c5.getCardBackgroundColor () == c8.getCardBackgroundColor ()) && c2.getCardBackgroundColor () != sample.getCardBackgroundColor () && c5.getCardBackgroundColor () != sample.getCardBackgroundColor () && c8.getCardBackgroundColor () != sample.getCardBackgroundColor () ){
            gamedecider ( c2,c5,c8 );
        }
        else if ((c3.getCardBackgroundColor () == c6.getCardBackgroundColor () && c6.getCardBackgroundColor () == c9.getCardBackgroundColor ()) && c3.getCardBackgroundColor () != sample.getCardBackgroundColor () && c6.getCardBackgroundColor () != sample.getCardBackgroundColor () && c9.getCardBackgroundColor () != sample.getCardBackgroundColor () ){
            gamedecider ( c3,c6,c9 );
        }
        else if ((c1.getCardBackgroundColor () == c5.getCardBackgroundColor () && c5.getCardBackgroundColor () == c9.getCardBackgroundColor ()) && c1.getCardBackgroundColor () != sample.getCardBackgroundColor () && c5.getCardBackgroundColor () != sample.getCardBackgroundColor () && c9.getCardBackgroundColor () != sample.getCardBackgroundColor () ){
            gamedecider ( c1,c5,c9 );
        }
        else if ((c3.getCardBackgroundColor () == c5.getCardBackgroundColor () && c5.getCardBackgroundColor () == c7.getCardBackgroundColor ()) && c3.getCardBackgroundColor () != sample.getCardBackgroundColor () && c5.getCardBackgroundColor () != sample.getCardBackgroundColor () && c7.getCardBackgroundColor () != sample.getCardBackgroundColor () ){
            gamedecider ( c3,c5,c7 );
        }
        else if (buttons.size () == 0){
            winScreen ( 2 );
        }
    }
    private void gamedecider( final CardView cardView1, final CardView cardView2, final CardView cardView3){
        Invisible ();
        if (cardView1.getCardBackgroundColor () == sample1.getCardBackgroundColor ( )){
            winScreen ( 1 );
        }
        else {
            winScreen ( 3 );
        }
        cardView1.setCardBackgroundColor ( Color.rgb ( 100,200,255 ) );
        cardView2.setCardBackgroundColor ( Color.rgb ( 100,200,255 ) );
        cardView3.setCardBackgroundColor ( Color.rgb ( 100,200,255 ) );
        cardViews.clear ();
        buttons.clear ();
    }
    private void player(ImageView imageView,CardView cardView,Button button){
        imageView.setImageResource ( R.drawable.x );
        cardView.setCardBackgroundColor ( Color.rgb ( 255,255,254 ) );
        cardViews.remove ( cardView );
        button.setVisibility ( View.INVISIBLE );
        buttons.remove ( button );
        imageViews.remove ( imageView );
    }
    private void opponenthelper(Button button){
        int index = buttons.indexOf ( button );
        buttons.get ( index ).setVisibility ( View.INVISIBLE );
        imageViews.get ( index ).setImageResource ( R.drawable.o);
        cardViews.get ( index ).setCardBackgroundColor ( Color.rgb ( 255,254,255 ));
        imageViews.remove ( index );
        cardViews.remove ( index );
        buttons.remove ( index  );
        checker ();
    }
    private void exit(){
        mediaPlayer2.start ();
        final AlertDialog.Builder builder = new AlertDialog.Builder ( GameThreeActivity.this,R.style.CustomDialog );
        View view = LayoutInflater.from ( GameThreeActivity.this ).inflate ( R.layout.alphabet_write_dialog,null,false );
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
                mediaPlayer1.start ();
                full ();
                alertDialog.cancel ();
            }
        } );

        yes.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                mediaPlayer1.start ();
                finish ();
            }
        } );
        alertDialog.setCanceledOnTouchOutside ( false );
        alertDialog.show ();
    }

    @Override
    public void onBackPressed() {
        exit ();
    }

    private void winScreen(int type){
        final AlertDialog.Builder builder = new AlertDialog.Builder ( GameThreeActivity.this,R.style.CustomDialog );
        View view = LayoutInflater.from ( GameThreeActivity.this ).inflate ( R.layout.alphabet_write_dialog,null,false );
        Button yes = view.findViewById ( R.id.yes );
        Button no = view.findViewById ( R.id.no );
        TextView firstText = view.findViewById ( R.id.firstText );
        TextView secondText = view.findViewById ( R.id.secontText );
        ImageView dialogImage = view.findViewById ( R.id.dialog_image );

        if (type == 1){
            firstText.setText ( "Won game !" );
            dialogImage.setImageResource ( R.drawable.win );
        }
        else if (type == 2){
            firstText.setText ( "Game tie !" );
            dialogImage.setImageResource ( R.drawable.handshake );
        }
        else {
            firstText.setText ( "Lost game !" );
            dialogImage.setImageResource ( R.drawable.locked );
        }
        secondText.setText ( "Wanna play again ?" );
        final AlertDialog alertDialog = builder.create ();
        alertDialog.setView ( view );

        no.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                finish ();
            }
        } );

        yes.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                finish ();
                Intent intent = new Intent ( getApplicationContext (),GameThreeActivity.class );
                startActivity ( intent );
            }
        } );
        alertDialog.setCanceledOnTouchOutside ( false );
        new Handler (  ).postDelayed ( new Runnable () {
            @Override
            public void run() {
                alertDialog.show ();
            }
        },1000 );
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

    @Override
    protected void onResume() {
        super.onResume ();
        full ();
    }

    @Override
    protected void onPause() {
        super.onPause ();
        overridePendingTransition ( android.R.anim.fade_in,android.R.anim.fade_out );
    }
}
