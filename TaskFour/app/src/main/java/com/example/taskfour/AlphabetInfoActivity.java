package com.example.taskfour;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.taskfour.Adapters.AlphabetWordsAdapter;
import com.example.taskfour.alphabets.ListOfAlphabets;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AlphabetInfoActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView alphabetCaps,alphabetSmall,alphabetSentence;
    private int id;
    private List<String> alphabetWord = new ArrayList<> (  );
    private TextToSpeech textToSpeech;
    private Button letter,sentence,back;
    private MediaPlayer mediaPlayer,mediaPlayer2;
    private CardView c1,c2;


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
        setContentView ( R.layout.activity_alphabet_info );

        id = getIntent ().getIntExtra ( "id",0 );

        mediaPlayer = new MediaPlayer ().create(getApplicationContext (),R.raw.rubberone);
        mediaPlayer2 = new MediaPlayer ().create(getApplicationContext (),R.raw.negative);

        recyclerView = findViewById ( R.id.alphabet_word_recycler );
        recyclerView.setHasFixedSize ( true );
        recyclerView.setLayoutManager ( new GridLayoutManager ( getApplicationContext (),2) );

        AlphabetWordsAdapter alphabetWordsAdapter = new AlphabetWordsAdapter ( getApplicationContext (),id );
        recyclerView.setAdapter ( alphabetWordsAdapter );

        alphabetCaps = findViewById ( R.id.alphabet_caps );
        alphabetSmall = findViewById ( R.id.alphabet_small );
        alphabetSentence = findViewById ( R.id.alphabet_sentence );
        letter = findViewById ( R.id.alphabet_speak_button );
        sentence = findViewById ( R.id.alphabet_sentence_speak_button );
        back = findViewById ( R.id.back );
        c1 = findViewById ( R.id.c1 );
        c2 = findViewById ( R.id.c2 );

        alphabetCaps.setText ( ListOfAlphabets.alphabets[id].toUpperCase () );
        alphabetSmall.setText ( ListOfAlphabets.alphabets[id].toLowerCase () );

        for (int i = 0;i<ListOfAlphabets.sentences[id].split ( ListOfAlphabets.alphabets[id] ).length;i++){
            alphabetWord.add ( ListOfAlphabets.sentences[id].split ( ListOfAlphabets.alphabets[id] )[i] );
        }
        for (int i= 0;i<alphabetWord.size ();i++){
            String text = "<font color=#F9333333>"+alphabetWord.get ( i )+"</font>";
            String text2 = "<font color=#FF0000>"+ListOfAlphabets.alphabets[id]+"</font>";
            String text3 = "<font color=#FF0000>"+ListOfAlphabets.alphabetsUpper[id]+"</font>";

            if ( i ==0 && alphabetWord.get ( 0 ).length () == 0){
                alphabetSentence.append ( Html.fromHtml ( text ) );
                alphabetSentence.append ( Html.fromHtml ( text3 ));
            }
            else if (i<alphabetWord.size ()-1){
                alphabetSentence.append ( Html.fromHtml ( text ) );
                alphabetSentence.append ( Html.fromHtml ( text2 ) );
            }
            else {
                alphabetSentence.append ( Html.fromHtml ( text ) );
            }
        }

        textToSpeech = new TextToSpeech ( getApplicationContext (), new TextToSpeech.OnInitListener () {
            @Override
            public void onInit( int status ) {
                if (status!=TextToSpeech.ERROR){
                    textToSpeech.setLanguage ( Locale.UK );
                }
            }
        } );

        View.OnClickListener onClickListener = new View.OnClickListener () {
            @Override
            public void onClick( final View v ) {
                mediaPlayer.start ();
                new Handler (  ).postDelayed ( new Runnable () {
                    @Override
                    public void run() {
                        switch (v.getId ()){
                            case R.id.alphabet_speak_button:
                                c1.startAnimation ( AnimationUtils.loadAnimation ( getApplicationContext (),R.anim.button ) );
                                textToSpeech.speak ( alphabetSmall.getText ().toString (),TextToSpeech.QUEUE_FLUSH,null );
                                break;
                            case R.id.alphabet_sentence_speak_button:
                                c2.startAnimation ( AnimationUtils.loadAnimation ( getApplicationContext (),R.anim.button ) );
                                textToSpeech.speak ( alphabetSentence.getText ().toString (),TextToSpeech.QUEUE_FLUSH,null );
                                break;
                        }
                    }
                },250 );

            }
        };

        letter.setOnClickListener ( onClickListener );
        sentence.setOnClickListener ( onClickListener );

        back.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                exit ();
            }
        } );

        final  Button info = findViewById ( R.id.info );
        info.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                mediaPlayer2.start ();
                final AlertDialog.Builder builder = new AlertDialog.Builder ( AlphabetInfoActivity.this,R.style.CustomDialog );
                View view = LayoutInflater.from ( AlphabetInfoActivity.this ).inflate ( R.layout.about_dialog,null,false );

                Button close = view.findViewById ( R.id.info_close );
                TextView infoText = view.findViewById ( R.id.info_text );

                infoText.setText ( "This page shows the sentence usage and words related to the selected alphabet along with proper voice which spells the content for better understanding. " );

                final AlertDialog alertDialog = builder.create ();
                alertDialog.setView ( view );

                close.setOnClickListener ( new View.OnClickListener () {
                    @Override
                    public void onClick( View v ) {
                        mediaPlayer.start ();
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
    public void onBackPressed() {
        exit ();
    }

    private void exit(){
        mediaPlayer2.start ();
        final AlertDialog.Builder builder = new AlertDialog.Builder ( AlphabetInfoActivity.this,R.style.CustomDialog );
        View view = LayoutInflater.from ( AlphabetInfoActivity.this ).inflate ( R.layout.alphabet_write_dialog,null,false );
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
                mediaPlayer.start ();
                alertDialog.cancel ();
                full ();
            }
        } );

        yes.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                mediaPlayer.start ();
                finish ();
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
