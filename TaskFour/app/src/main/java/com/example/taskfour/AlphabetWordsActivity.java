package com.example.taskfour;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class AlphabetWordsActivity extends AppCompatActivity {

    private Button b1,b2,b3,b4,b5,b6,b7,b8,b9,b10,b11,b12,b13,b14,b15,b16,back;
    private CardView c1,c2,c3,c4,c5,c6,c7,c8,c9,c10,c11,c12,c13,c14,c15,c16;
    private MediaPlayer mediaPlayer,mediaPlayer1;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        requestWindowFeature ( Window.FEATURE_NO_TITLE );
        getWindow ().setFlags ( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );

        getWindow ().addFlags ( WindowManager.LayoutParams.FLAG_FULLSCREEN
                | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS );

        View decorView = getWindow ().getDecorView ();
        decorView.setSystemUiVisibility ( View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY );
        setContentView ( R.layout.activity_alphabet_words );

        Main2Activity.mediaPlayer.start ();
        mediaPlayer = new MediaPlayer ().create(getApplicationContext (),R.raw.negative);
        mediaPlayer1 = new MediaPlayer ().create(getApplicationContext (),R.raw.rubberone);

        b1 = findViewById ( R.id.word_one_but );
        b2 = findViewById ( R.id.word_two_but );
        b3 = findViewById ( R.id.word_three_but );
        b4 = findViewById ( R.id.word_four_but );
        b5 = findViewById ( R.id.word_five_but );
        b6 = findViewById ( R.id.word_six_but );
        b7 = findViewById ( R.id.word_seven_but );
        b8 = findViewById ( R.id.word_eight_but );
        b9 = findViewById ( R.id.word_nine_but );
        b10 = findViewById ( R.id.word_ten_but );
        b11 = findViewById ( R.id.word_eleven_but );
        b12 = findViewById ( R.id.word_twelve_but );
        b13 = findViewById ( R.id.word_thirteen_but );
        b14 = findViewById ( R.id.word_fourteen_but );
        b15 = findViewById ( R.id.word_fifteen_but );
        b16 = findViewById ( R.id.word_sixteen_but );

        back = findViewById ( R.id.back );

        c1 = findViewById ( R.id.c1 );
        c2 = findViewById ( R.id.c2 );
        c3 = findViewById ( R.id.c3 );
        c4 = findViewById ( R.id.c4 );
        c5 = findViewById ( R.id.c5 );
        c6 = findViewById ( R.id.c6 );
        c7 = findViewById ( R.id.c7 );
        c8 = findViewById ( R.id.c8 );
        c9 = findViewById ( R.id.c9 );
        c10 = findViewById ( R.id.c10 );
        c11 = findViewById ( R.id.c11 );
        c12 = findViewById ( R.id.c12 );
        c13 = findViewById ( R.id.c13 );
        c14 = findViewById ( R.id.c14 );
        c15 = findViewById ( R.id.c15 );
        c16 = findViewById ( R.id.c16 );

        final Animation animation = AnimationUtils.loadAnimation ( getApplicationContext (),R.anim.button );

        final View.OnClickListener onClickListener = new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                mediaPlayer1.start ();
                Intent intent = new Intent ( getApplicationContext (),WordActivity.class );
                switch (v.getId ()){
                    case R.id.word_one_but:
                        c1.startAnimation ( animation );
                        intent.putExtra ( "id",1 );
                        break;
                    case R.id.word_two_but:
                        c2.startAnimation ( animation );
                        intent.putExtra ( "id",2 );
                        break;
                    case R.id.word_three_but:
                        c3.startAnimation ( animation );
                        intent.putExtra ( "id",3 );
                        break;
                    case R.id.word_four_but:
                        c4.startAnimation ( animation );
                        intent.putExtra ( "id",4 );
                        break;
                    case R.id.word_five_but:
                        c5.startAnimation ( animation );
                        intent.putExtra ( "id",5 );
                        break;
                    case R.id.word_six_but:
                        c6.startAnimation ( animation );
                        intent.putExtra ( "id",6 );
                        break;
                    case R.id.word_seven_but:
                        c7.startAnimation ( animation );
                        intent.putExtra ( "id",7 );
                        break;
                    case R.id.word_eight_but:
                        c8.startAnimation ( animation );
                        intent.putExtra ( "id",8 );
                        break;
                    case R.id.word_nine_but:
                        c9.startAnimation ( animation );
                        intent.putExtra ( "id",9 );
                        break;
                    case R.id.word_ten_but:
                        c10.startAnimation ( animation );
                        intent.putExtra ( "id",10 );
                        break;
                    case R.id.word_eleven_but:
                        c11.startAnimation ( animation );
                        intent.putExtra ( "id",11 );
                        break;
                    case R.id.word_twelve_but:
                        c12.startAnimation ( animation );
                        intent.putExtra ( "id",12 );
                        break;
                    case R.id.word_thirteen_but:
                        c13.startAnimation ( animation );
                        intent.putExtra ( "id",13 );
                        break;
                    case R.id.word_fourteen_but:
                        c14.startAnimation ( animation );
                        intent.putExtra ( "id",14 );
                        break;
                    case R.id.word_fifteen_but:
                        c15.startAnimation ( animation );
                        intent.putExtra ( "id",15 );
                        break;
                    case R.id.word_sixteen_but:
                        c16.startAnimation ( animation );
                        intent.putExtra ( "id",16 );
                        break;
                }

                startActivity ( intent );

            }
        };

        b1.setOnClickListener ( onClickListener );
        b2.setOnClickListener ( onClickListener );
        b3.setOnClickListener ( onClickListener );
        b4.setOnClickListener ( onClickListener );
        b5.setOnClickListener ( onClickListener );
        b6.setOnClickListener ( onClickListener );
        b7.setOnClickListener ( onClickListener );
        b8.setOnClickListener ( onClickListener );
        b9.setOnClickListener ( onClickListener );
        b10.setOnClickListener ( onClickListener );
        b11.setOnClickListener ( onClickListener );
        b12.setOnClickListener ( onClickListener );
        b13.setOnClickListener ( onClickListener );
        b14.setOnClickListener ( onClickListener );
        b15.setOnClickListener ( onClickListener );
        b16.setOnClickListener ( onClickListener );

        back.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                mediaPlayer.start ();
                finish ();
            }
        } );

        final  Button info = findViewById ( R.id.info );
        info.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                mediaPlayer.start ();
                final AlertDialog.Builder builder = new AlertDialog.Builder ( AlphabetWordsActivity.this,R.style.CustomDialog );
                View view = LayoutInflater.from ( AlphabetWordsActivity.this ).inflate ( R.layout.about_dialog,null,false );

                Button close = view.findViewById ( R.id.info_close );
                TextView infoText = view.findViewById ( R.id.info_text );

                infoText.setText ( "This page shows the list of topic for users to select. The users will be given words based on the topic selected" );

                final AlertDialog alertDialog = builder.create ();
                alertDialog.setView ( view );

                close.setOnClickListener ( new View.OnClickListener () {
                    @Override
                    public void onClick( View v ) {
                        mediaPlayer1.start ();
                        alertDialog.cancel ();
                        full ();
                    }
                } );

                alertDialog.setCanceledOnTouchOutside ( false );
                alertDialog.show ();
            }
        } );

    }
    @Override
    protected void onPause() {
        super.onPause ();
        Main2Activity.mediaPlayer.pause ();
        overridePendingTransition ( android.R.anim.fade_in,android.R.anim.fade_out );
    }

    @Override
    protected void onResume() {
        super.onResume ();
        Main2Activity.mediaPlayer.start ();
        full ();
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
