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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class GameTwoActivity extends AppCompatActivity {

    private ImageView cub1,cub2,cub3;
    private ImageView parent1,parent2,parent3,tin1,tin2,tin3;
    private Button touch1,touch2,touch3;
    private List<String> integers = new ArrayList<> (  );
    private List<String> parents = new ArrayList<> (  );
    private int butNo = 2;
    private int pathNo = 2;
    private int heartvalue = 6;
    private TextView score,introCountdown,heart;
    private Button play,reset,exit;
    private RelativeLayout relativeLayout;
    private GifImageView blast1,blast2,blast3;
    private MediaPlayer mediaPlayer,mediaPlayer1,mediaPlayer2,mediaPlayer3,mediaPlayer4,mediaPlayer5;
    private int outer = 0;
    private Animation animation;
    private CardView cexit,cplay,creset;
    private static CountDownTimer countDownTimer1;

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
        setContentView ( R.layout.activity_game_two );

        mediaPlayer = new MediaPlayer ().create(getApplicationContext (),R.raw.explosion);
        mediaPlayer1 = new MediaPlayer ().create(getApplicationContext (),R.raw.gamewrong);
        mediaPlayer2 = new MediaPlayer ().create(getApplicationContext (),R.raw.sucess);
        mediaPlayer3 = new MediaPlayer ().create(getApplicationContext (),R.raw.highbase);
        mediaPlayer4 = new MediaPlayer ().create(getApplicationContext (),R.raw.rubberone);
        mediaPlayer5 = new MediaPlayer ().create(getApplicationContext (),R.raw.negative);

        cub1 = findViewById ( R.id.cub1 );
        cub2 = findViewById ( R.id.cub2 );
        cub3 = findViewById ( R.id.cub3 );

        parent1 = findViewById ( R.id.parent1 );
        parent2 = findViewById ( R.id.parent2 );
        parent3 = findViewById ( R.id.parent3 );

        tin1 = findViewById ( R.id.tin1 );
        tin2 = findViewById ( R.id.tin2 );
        tin3 = findViewById ( R.id.tin3 );

        touch1 = findViewById ( R.id.touch1 );
        touch2 = findViewById ( R.id.touch2 );
        touch3 = findViewById ( R.id.touch3 );
        touch1.setEnabled ( false );
        touch2.setEnabled ( false );
        touch3.setEnabled ( false );

        cexit = findViewById ( R.id.exitcard );
        cplay = findViewById ( R.id.cardplay );
        creset = findViewById ( R.id.cardreset );

        blast1 = findViewById ( R.id.blastone );
        blast2 = findViewById ( R.id.blasttwo );
        blast3 = findViewById ( R.id.blastthree );
        blast1.setVisibility ( View.INVISIBLE );
        blast2.setVisibility ( View.INVISIBLE );
        blast3.setVisibility ( View.INVISIBLE );

        score = findViewById ( R.id.score );
        heart = findViewById ( R.id.heart );
        score.setText ( "0" );
        heart.setTextColor ( Color.rgb ( 11,148,20 ) );

        introCountdown = findViewById ( R.id.intro_countdown );
        play = findViewById ( R.id.play );
        reset = findViewById ( R.id.reset );
        exit = findViewById ( R.id.exit );
        relativeLayout = findViewById ( R.id.countdownl_container );
        countDownTimer1 = null;

        visibilityTwo ();

        animation = AnimationUtils.loadAnimation ( getApplicationContext (),R.anim.gametwo );

        final Animation.AnimationListener animationListener = new Animation.AnimationListener () {
            @Override
            public void onAnimationStart( Animation animation ) {

                if (heartvalue >= 0 && outer == 0){
                    integers.clear ();
                    for (int i = 1;i<=10;i++){
                        integers.add ( String.valueOf ( i ) );
                    }

                    int index = (int)(Math.random ()*(9));
                    parents.clear ();
                    parents.add ( integers.get ( index ) );
                    ImageSetter ( Integer.parseInt ( parents.get ( 0 ) ),cub1 );
                    integers.remove ( index);
                    index = (int)(Math.random ()*(8));
                    parents.add ( integers.get ( index ) );
                    ImageSetter ( Integer.parseInt ( parents.get ( 1 ) ),cub2 );
                    integers.remove ( index );
                    index = (int)(Math.random ()*(7));
                    parents.add ( integers.get ( index ) );
                    ImageSetter ( Integer.parseInt ( parents.get ( 2 ) ),cub3 );
                    pathSetter ( parents );
                }
            }

            @Override
            public void onAnimationEnd( final Animation animation ) {
                if (outer == 0){
                    animationEnd ();
                }

            }

            @Override
            public void onAnimationRepeat( Animation animation ) {

            }
        };

        View.OnClickListener onClickListener = new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                mediaPlayer3.start ();
                switch (v.getId ()){
                    case R.id.touch1:
                        visibilityOne ();
                        butNo = 1;
                        break;
                    case R.id.touch2:
                        visibilityTwo ();
                        butNo = 2;
                        break;
                    case R.id.touch3:
                        visibilityThree ();
                        butNo = 3;
                        break;
                }
            }
        };

        touch1.setOnClickListener ( onClickListener );
        touch2.setOnClickListener ( onClickListener );
        touch3.setOnClickListener ( onClickListener );

        View.OnClickListener onClickListener1 = new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                switch (v.getId ()){
                    case R.id.play:
                        mediaPlayer4.start ();
                        cplay.startAnimation ( AnimationUtils.loadAnimation ( getApplicationContext (),R.anim.button ) );
                        introCountdown.setText ( "3" );
                        play.setEnabled ( false );
                        countDownTimer1 = new CountDownTimer (3000,1000) {
                            @Override
                            public void onTick( long millisUntilFinished ) {
                                introCountdown.setText ( String.valueOf ( Integer.parseInt ( introCountdown.getText ().toString () )-1 ) );
                            }

                            @Override
                            public void onFinish() {
                                relativeLayout.setVisibility ( View.INVISIBLE );
                                cub1.startAnimation ( animation );
                                cub2.startAnimation ( animation );
                                cub3.startAnimation ( animation );
                                animation.setAnimationListener ( animationListener );
                                touch1.setEnabled ( true );
                                touch2.setEnabled ( true );
                                touch3.setEnabled ( true );
                            }
                        }.start ();
                        break;
                    case R.id.reset:
                        mediaPlayer4.start ();
                        creset.startAnimation ( AnimationUtils.loadAnimation ( getApplicationContext (),R.anim.button ) );
                        outer = 1;
                        final AlertDialog.Builder builder2 = new AlertDialog.Builder ( GameTwoActivity.this,R.style.CustomDialog );
                        View view2 = LayoutInflater.from ( GameTwoActivity.this ).inflate ( R.layout.alphabet_write_dialog,null,false );
                        Button yes2 = view2.findViewById ( R.id.yes );
                        Button no2 = view2.findViewById ( R.id.no );
                        TextView firstText2 = view2.findViewById ( R.id.firstText );
                        TextView secondText2 = view2.findViewById ( R.id.secontText );
                        ImageView dialogImage2 = view2.findViewById ( R.id.dialog_image );

                        firstText2.setText ( "Are you sure !" );
                        secondText2.setText ( "Do you want to reset?" );
                        mediaPlayer5.start ();
                        dialogImage2.setImageResource ( R.drawable.omg );
                        final AlertDialog alertDialog2 = builder2.create ();
                        alertDialog2.setView ( view2 );

                        no2.setOnClickListener ( new View.OnClickListener () {
                            @Override
                            public void onClick( View v ) {
                                mediaPlayer4.start ();
                                alertDialog2.cancel ();
                                full ();
                                if (countDownTimer1 != null){
                                    animationEnd ();
                                }
                                outer = 0;
                            }
                        } );

                        yes2.setOnClickListener ( new View.OnClickListener () {
                            @Override
                            public void onClick( View v ) {
                                mediaPlayer4.start ();
                                finish ();
                                Intent intent = new Intent ( getApplicationContext (),GameTwoActivity.class );
                                startActivity ( intent );
                            }
                        } );
                        alertDialog2.setCanceledOnTouchOutside ( false );
                        alertDialog2.show ();
                        break;
                    case R.id.exit:
                        mediaPlayer4.start ();
                        cexit.startAnimation ( AnimationUtils.loadAnimation ( getApplicationContext (),R.anim.button ) );
                        exit ();
                        break;
                }
            }
        };

        play.setOnClickListener ( onClickListener1 );
        reset.setOnClickListener ( onClickListener1 );
        exit.setOnClickListener ( onClickListener1 );

    }

    private void ImageSetter(int num,ImageView imageView){
        if (num == 1){
            imageView.setImageResource ( R.drawable.animalkidone );
        }
        else if (num == 2){
            imageView.setImageResource ( R.drawable.animaltwokid );
        }
        else if (num == 3){
            imageView.setImageResource ( R.drawable.animalthreekid );
        }
        else if (num == 4){
            imageView.setImageResource ( R.drawable.animalfourkid );
        }
        else if (num == 5){
            imageView.setImageResource ( R.drawable.animalfivekid );
        }
        else if (num == 6){
            imageView.setImageResource ( R.drawable.animalsixkid );
        }
        else if (num == 7){
            imageView.setImageResource ( R.drawable.animalsevenkid );
        }
        else if (num == 8){
            imageView.setImageResource ( R.drawable.animaleightkid );
        }
        else if (num == 9){
            imageView.setImageResource ( R.drawable.animalninekid);
        }
        else if (num == 10){
            imageView.setImageResource ( R.drawable.animaltenkid );
        }
    }

    private void ImageSetterParent(int num,ImageView imageView){
        if (num == 1){
            imageView.setImageResource ( R.drawable.animalone );
        }
        else if (num == 2){
            imageView.setImageResource ( R.drawable.animaltwo );
        }
        else if (num == 3){
            imageView.setImageResource ( R.drawable.animalthree );
        }
        else if (num == 4){
            imageView.setImageResource ( R.drawable.animalfour );
        }
        else if (num == 5){
            imageView.setImageResource ( R.drawable.animalfive );
        }
        else if (num == 6){
            imageView.setImageResource ( R.drawable.animalsix );
        }
        else if (num == 7){
            imageView.setImageResource ( R.drawable.animalseven );
        }
        else if (num == 8){
            imageView.setImageResource ( R.drawable.animaleight );
        }
        else if (num == 9){
            imageView.setImageResource ( R.drawable.animalnine );
        }
        else if (num == 10){
            imageView.setImageResource ( R.drawable.animalten );
        }
    }

    private void visibilityOne(){
        parent1.setVisibility ( View.VISIBLE );
        tin1.setVisibility ( View.VISIBLE );
        parent2.setVisibility ( View.INVISIBLE );
        tin2.setVisibility ( View.INVISIBLE );
        parent3.setVisibility ( View.INVISIBLE );
        tin3.setVisibility ( View.INVISIBLE );
    }
    private void visibilityTwo(){
        parent1.setVisibility ( View.INVISIBLE );
        tin1.setVisibility ( View.INVISIBLE );
        parent2.setVisibility ( View.VISIBLE );
        tin2.setVisibility ( View.VISIBLE );
        parent3.setVisibility ( View.INVISIBLE );
        tin3.setVisibility ( View.INVISIBLE );
    }
    private void visibilityThree(){
        parent1.setVisibility ( View.INVISIBLE );
        tin1.setVisibility ( View.INVISIBLE );
        parent2.setVisibility ( View.INVISIBLE );
        tin2.setVisibility ( View.INVISIBLE );
        parent3.setVisibility ( View.VISIBLE );
        tin3.setVisibility ( View.VISIBLE );
    }

    private void pathSetter(List<String> p){
        int index = (int)(Math.random ()*300);

        if (index <=100){
            pathNo = 1;
            ImageSetterParent ( Integer.parseInt ( p.get ( 0) ),parent1 );
            ImageSetterParent ( Integer.parseInt ( p.get ( 0) ),parent2 );
            ImageSetterParent ( Integer.parseInt ( p.get ( 0) ),parent3 );
        }
        else if (index<=200){
            pathNo = 2;
            ImageSetterParent ( Integer.parseInt ( p.get ( 1) ),parent1 );
            ImageSetterParent ( Integer.parseInt ( p.get ( 1) ),parent2 );
            ImageSetterParent ( Integer.parseInt ( p.get ( 1) ),parent3 );
        }
        else {
            pathNo = 3;
            ImageSetterParent ( Integer.parseInt ( p.get ( 2) ),parent1 );
            ImageSetterParent ( Integer.parseInt ( p.get ( 2) ),parent2 );
            ImageSetterParent ( Integer.parseInt ( p.get ( 2) ),parent3 );
        }

    }

    private void exit(){
        outer = 1;
        mediaPlayer5.start ();
        final AlertDialog.Builder builder = new AlertDialog.Builder ( GameTwoActivity.this,R.style.CustomDialog );
        View view = LayoutInflater.from ( GameTwoActivity.this ).inflate ( R.layout.alphabet_write_dialog,null,false );
        Button yes = view.findViewById ( R.id.yes );
        Button no = view.findViewById ( R.id.no );
        TextView firstText = view.findViewById ( R.id.firstText );
        TextView secondText = view.findViewById ( R.id.secontText );
        ImageView dialogImage = view.findViewById ( R.id.dialog_image );

        firstText.setText ( "Oh noooo !" );
        secondText.setText ( "Do you want to exit?" );
        dialogImage.setImageResource ( R.drawable.oh );
        final AlertDialog alertDialog = builder.create ();
        alertDialog.setView ( view );

        no.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                mediaPlayer4.start ();
                outer = 0;
                if (countDownTimer1 != null){
                    animationEnd ();
                }
                alertDialog.cancel ();
                full ();
            }
        } );

        yes.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                mediaPlayer4.start ();
                finish ();
            }
        } );
        alertDialog.setCanceledOnTouchOutside ( false );
        alertDialog.show ();
    }

    @Override
    public void onBackPressed() {
        if (integers == null){
            super.onBackPressed ();
        }
        else {
            exit();
        }
    }

    private void gif( final GifImageView gifImageView){
        gifImageView.setVisibility ( View.VISIBLE );
        ((GifDrawable)gifImageView.getDrawable ()).reset ();
        ((GifDrawable)gifImageView.getDrawable ()).start ();
        new Handler (  ).postDelayed ( new Runnable () {
            @Override
            public void run() {
                gifImageView.setVisibility ( View.INVISIBLE );
            }
        },((GifDrawable)blast1.getDrawable ()).getDuration ());
    }
    private void animationEnd(){
        mediaPlayer.start ();
        gif ( blast1 );
        gif ( blast2 );
        gif ( blast3 );

        if (butNo == pathNo){
            mediaPlayer2.start ();
            score.setText ( String.valueOf ( Integer.parseInt ( score.getText ().toString () ) +1 ));
        }
        else {
            mediaPlayer1.start ();
            heartvalue = heartvalue-1;
            heart.setText ( "" );
            for (int i = 0; i < heartvalue; i++) {
                heart.append ( "l" );
            }
        }

        if (heartvalue >=5){
            heart.setTextColor ( Color.rgb ( 11,148,20 ) );
        }
        else if (heartvalue >=3){
            heart.setTextColor ( Color.rgb ( 243,252,20 ));
        }
        else {
            heart.setTextColor ( Color.rgb ( 255,0,0 ) );
        }

        if (heartvalue != 0){
            new Handler (  ).postDelayed ( new Runnable () {
                @Override
                public void run() {
                    int duration = (int) ( Math.random () * 3 )+3;
                    animation.setDuration ( duration*1000 );
                    cub1.startAnimation ( animation );
                    cub2.startAnimation ( animation );
                    cub3.startAnimation ( animation );
                }
            },1000 );
        }
        else {
            cub1.setVisibility ( View.INVISIBLE );
            cub2.setVisibility ( View.INVISIBLE );
            cub3.setVisibility ( View.INVISIBLE );
            final AlertDialog.Builder builder2 = new AlertDialog.Builder ( GameTwoActivity.this,R.style.CustomDialog );
            View view2 = LayoutInflater.from ( GameTwoActivity.this ).inflate ( R.layout.alphabet_write_dialog,null,false );
            Button yes2 = view2.findViewById ( R.id.yes );
            Button no2 = view2.findViewById ( R.id.no );
            TextView firstText2 = view2.findViewById ( R.id.firstText );
            TextView secondText2 = view2.findViewById ( R.id.secontText );
            ImageView dialogImage2 = view2.findViewById ( R.id.dialog_image );
            mediaPlayer5.start ();
            if (Integer.parseInt ( score.getText ().toString () ) <=15){
                firstText2.setText ( score.getText ().toString ()+" is poor..." );
                dialogImage2.setImageResource ( R.drawable.poor );
            }
            else if (Integer.parseInt ( score.getText ().toString () )<= 30){
                firstText2.setText (  score.getText ().toString ()+" is good..." );
                dialogImage2.setImageResource ( R.drawable.good );
            }
            else {
                firstText2.setText (  score.getText ().toString ()+" is excellent..." );
                dialogImage2.setImageResource ( R.drawable.excellent );
            }

            secondText2.setText ( "Do you wanna retry?" );
            final AlertDialog alertDialog2 = builder2.create ();
            alertDialog2.setView ( view2 );

            no2.setOnClickListener ( new View.OnClickListener () {
                @Override
                public void onClick( View v ) {
                    mediaPlayer4.start ();
                    finish ();
                }
            } );

            yes2.setOnClickListener ( new View.OnClickListener () {
                @Override
                public void onClick( View v ) {
                    mediaPlayer4.start ();
                    finish ();
                    startActivity ( new Intent ( getApplicationContext (),GameTwoActivity.class ) );
                }
            } );
            alertDialog2.setCanceledOnTouchOutside ( false );
            alertDialog2.show ();
        }
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
        outer = 0;
        if (countDownTimer1 != null){
            animationEnd ();
        }
        full ();
    }

    @Override
    protected void onPause() {
        outer = 1;
        super.onPause ();
        overridePendingTransition ( android.R.anim.fade_in,android.R.anim.fade_out );
    }
}
