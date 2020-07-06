package com.example.taskfour;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.taskfour.alphabets.AlphabetPhrases;
import com.example.taskfour.alphabets.ListOfAlphabets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pl.droidsonroids.gif.GifDrawable;

public class AlphabetCountActivity extends AppCompatActivity {

    private static List<String> alphabet = new ArrayList<> (  );
    private Button option1,option2,option3,retry,next,back;
    private ImageView wrong;
    private TextView container,resultname;
    private static int count;
    private MediaPlayer mediaPlayer,mediaPlayer1,mediaPlayer2,mediaPlayer3;
    private CardView c1,c2,c3,c4,c5;

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
        setContentView ( R.layout.activity_alphabet_count );
        mediaPlayer = new MediaPlayer ().create(getApplicationContext (),R.raw.negative);
        mediaPlayer1 = new MediaPlayer ().create(getApplicationContext (),R.raw.rubberone);
        mediaPlayer3 = new MediaPlayer ().create(getApplicationContext (),R.raw.explosion);

        option1 = findViewById ( R.id.option_one );
        option2 = findViewById ( R.id.option_two );
        option3 = findViewById ( R.id.option_three );
        retry = findViewById ( R.id.retry );
        next = findViewById ( R.id.next );
        wrong = findViewById ( R.id.addition_wrong );
        container = findViewById ( R.id.container );
        resultname = findViewById ( R.id.result_name );
        back = findViewById ( R.id.back );

        c1 = findViewById ( R.id.c1 );
        c2 = findViewById ( R.id.c2 );
        c3 = findViewById ( R.id.c3 );
        c4 = findViewById ( R.id.c4 );
        c5 = findViewById ( R.id.c5 );

        alphabet.clear ();
        for (int i = 0; i < 26; i++) {
            alphabet.add ( String.valueOf ( i ) );
        }

        master ();
        final Animation animation = AnimationUtils.loadAnimation ( getApplicationContext (),R.anim.button );

        View.OnClickListener onClickListener = new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                mediaPlayer1.start ();
                switch (v.getId ()){
                    case R.id.option_one:
                        c1.startAnimation ( animation );
                        dissable ();
                        if (String.valueOf ( count ).equals ( option1.getText ().toString () )){
                            right ();
                        }
                        else {
                            wrong();
                        }
                        break;
                    case R.id.option_two:
                        c2.startAnimation ( animation );
                        dissable ();
                        if (String.valueOf ( count ).equals ( option2.getText ().toString () )){
                            right ();
                        }
                        else {
                            wrong ();
                        }
                        break;
                    case R.id.option_three:
                        c3.startAnimation ( animation );
                        dissable ();
                        if (String.valueOf ( count ).equals ( option3.getText ().toString () )){
                            right ();
                        }
                        else {
                            wrong();
                        }
                        break;
                    case R.id.retry:
                        c4.startAnimation ( animation );
                        enabled ();
                        wrong.setVisibility ( View.INVISIBLE );
                        container.setVisibility ( View.VISIBLE );
                        break;
                    case R.id.next:
                        c5.startAnimation ( animation );
                        enabled ();
                        master ();
                        mediaPlayer2.stop ();
                        break;
                }
            }
        };
        option1.setOnClickListener ( onClickListener );
        option2.setOnClickListener ( onClickListener );
        option3.setOnClickListener ( onClickListener );
        retry.setOnClickListener ( onClickListener );
        next.setOnClickListener ( onClickListener );
        back.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                exit ();
            }
        } );

    }

    @Override
    protected void onResume() {
        super.onResume ();
        if (mediaPlayer2 != null ){
            mediaPlayer2.start ();
        }
        full ();
    }

    @Override
    protected void onPause() {
        super.onPause ();
        overridePendingTransition ( android.R.anim.fade_in,android.R.anim.fade_out );
        if (mediaPlayer2 != null){
            mediaPlayer2.pause ();
        }
    }

    @Override
    public void onBackPressed() {
        exit ();
    }

    private void exit(){
        mediaPlayer.start ();
        final AlertDialog.Builder builder = new AlertDialog.Builder ( AlphabetCountActivity.this,R.style.CustomDialog );
        View view = LayoutInflater.from ( AlphabetCountActivity.this ).inflate ( R.layout.alphabet_write_dialog,null,false );
        Button yes = view.findViewById ( R.id.yes );
        Button no = view.findViewById ( R.id.no );
        TextView firstText = view.findViewById ( R.id.firstText );
        TextView secondText = view.findViewById ( R.id.secontText );
        ImageView dialogImage = view.findViewById ( R.id.dialog_image );

        firstText.setText ( "Oh noooo !" );
        secondText.setText ( "Do you wanna exit ?" );
        dialogImage.setImageResource ( R.drawable.oh );
        final AlertDialog alertDialog = builder.create ();
        alertDialog.setView ( view );

        no.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                mediaPlayer1.start ();
                alertDialog.cancel ();
                full ();
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

    private void wrong(){
        mediaPlayer3.start ();
        retry.setEnabled ( true );
        next.setEnabled ( false );
        container.setVisibility ( View.INVISIBLE );
        wrong.setVisibility ( View.VISIBLE );
        wrong.setImageResource ( R.drawable.boom );
        ((GifDrawable)wrong.getDrawable()).start ();
        ((GifDrawable)wrong.getDrawable()).reset ();
        new Handler (  ).postDelayed ( new Runnable () {
            @Override
            public void run() {
                ((GifDrawable)wrong.getDrawable()).stop();
            }
        },((GifDrawable)wrong.getDrawable()).getDuration ()-300);
    }
    private void right(){
        mediaPlayer2 = new MediaPlayer ().create(getApplicationContext (),R.raw.beat);
        mediaPlayer2.setLooping ( true );
        mediaPlayer2.start ();
        retry.setEnabled ( false );
        container.setVisibility ( View.INVISIBLE );
        wrong.setVisibility ( View.VISIBLE );
        wrong.setImageResource ( R.drawable.dancer );
        next.setEnabled ( true );
    }
    private void dissable(){
        option3.setEnabled ( false );
        option2.setEnabled ( false );
        option1.setEnabled ( false );
    }
    private void enabled(){
        option1.setEnabled ( true );
        option2.setEnabled ( true );
        option3.setEnabled ( true );
    }
    private void master(){
        List<String> setter = new ArrayList<> (  );
        wrong.setVisibility ( View.INVISIBLE );
        container.setVisibility ( View.VISIBLE );
        int randomAlpha = (int)(Math.random ()*(alphabet.size ()-1));
        int randomIndex = (int)(Math.random ()*(AlphabetPhrases.phrases[randomAlpha].length-1));
        count = 0;
        container.setText ( AlphabetPhrases.phrases[ randomAlpha ][randomIndex] );
        for (int i = 0;i<container.getText ().toString ().length ();i++){
            if (Character.toString ( container.getText ().toString ().charAt ( i ) ).toLowerCase ().equals ( ListOfAlphabets.alphabets[randomAlpha].toLowerCase () )){
                count++;
            }
        }

        resultname.setText ( "Find all "+ListOfAlphabets.alphabets[randomAlpha].toUpperCase () );
        for (int i = 0; i < count+10; i++) {
            setter.add ( String.valueOf ( i+1 ) );
        }
        setter.remove ( String.valueOf ( count ) );
        List<String> inter = new ArrayList<> (  );
        inter.add ( String.valueOf ( count ) );
        int index = (int) ( Math.random () * ( setter.size () - 1 ) );
        inter.add ( String.valueOf ( setter.get ( index ) ) );
        setter.remove ( index );
        index = (int) ( Math.random () * ( setter.size () - 1 ) );
        inter.add ( String.valueOf ( setter.get ( index ) ) );
        Collections.shuffle ( inter );
        option1.setText ( inter.get ( 0 ) );
        option2.setText ( inter.get ( 1 ) );
        option3.setText ( inter.get ( 2 ) );
        retry.setEnabled ( false );
        next.setEnabled ( false );
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
