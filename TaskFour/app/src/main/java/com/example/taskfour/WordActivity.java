package com.example.taskfour;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.taskfour.Adapters.WordsAdapter;

public class WordActivity extends AppCompatActivity {

    private int id;
    private RecyclerView recyclerView;
    private TextView textView;
    private Button back;
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
        setContentView ( R.layout.activity_word );
        id = getIntent ().getIntExtra ( "id",1 );
        mediaPlayer = new MediaPlayer ().create(getApplicationContext (),R.raw.negative);
        mediaPlayer1 = new MediaPlayer ().create(getApplicationContext (),R.raw.rubberone);

        recyclerView = findViewById ( R.id.alphabet_word_recycler );
        textView = findViewById ( R.id.label );
        back = findViewById ( R.id.back );

        recyclerView.setHasFixedSize ( true );
        recyclerView.setLayoutManager ( new GridLayoutManager ( getApplicationContext (),2 ) );

        switch (id){
            case 1:
                recyclerView.setAdapter ( new WordsAdapter ( getApplicationContext (),1 ) );
                textView.setText ( "Fruits     " );
                break;
            case 2:
                recyclerView.setAdapter ( new WordsAdapter ( getApplicationContext (),2 ) );
                textView.setText ( "Vegetables      " );
                break;
            case 3:
                recyclerView.setAdapter ( new WordsAdapter ( getApplicationContext (),3 ) );
                textView.setText ( "Spices       " );
                break;
            case 4:
                recyclerView.setAdapter ( new WordsAdapter ( getApplicationContext (),4 ) );
                textView.setText ( "Space       " );
                break;
            case 5:
                recyclerView.setAdapter ( new WordsAdapter ( getApplicationContext (),5 ) );
                textView.setText ( "Musical      " );
                break;
            case 6:
                recyclerView.setAdapter ( new WordsAdapter ( getApplicationContext (),6 ) );
                textView.setText ( "Birds       " );
                break;
            case 7:
                recyclerView.setAdapter ( new WordsAdapter ( getApplicationContext (),7 ) );
                textView.setText ( "Wild      " );
                break;
            case 8:
                recyclerView.setAdapter ( new WordsAdapter ( getApplicationContext (),8 ) );
                textView.setText ( "Aquatic       " );
                break;
            case 9:
                recyclerView.setAdapter ( new WordsAdapter ( getApplicationContext (),9 ) );
                textView.setText ( "Domestic     " );
                break;
            case 10:
                recyclerView.setAdapter ( new WordsAdapter ( getApplicationContext (),10 ) );
                textView.setText ( "Insects       " );
                break;
            case 11:
                recyclerView.setAdapter ( new WordsAdapter ( getApplicationContext (),11 ) );
                textView.setText ( "Colors       " );
                break;
            case 12:
                recyclerView.setAdapter ( new WordsAdapter ( getApplicationContext (),12 ) );
                textView.setText ( "School       " );
                break;
            case 13:
                recyclerView.setAdapter ( new WordsAdapter ( getApplicationContext (),13 ) );
                textView.setText ( "Vehicles       " );
                break;
            case 14:
                recyclerView.setAdapter ( new WordsAdapter ( getApplicationContext (),14 ) );
                textView.setText ( "Sports       " );
                break;
            case 15:
                recyclerView.setAdapter ( new WordsAdapter ( getApplicationContext (),15 ) );
                textView.setText ( "Jobs       " );
                break;
            case 16:
                recyclerView.setAdapter ( new WordsAdapter ( getApplicationContext (),16 ) );
                textView.setText ( "Shapes       " );
                break;
        }

        back.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                exit ();
            }
        } );

        final Button info = findViewById ( R.id.info );
        info.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                mediaPlayer.start ();
                final AlertDialog.Builder builder = new AlertDialog.Builder ( WordActivity.this,R.style.CustomDialog );
                View view = LayoutInflater.from ( WordActivity.this ).inflate ( R.layout.about_dialog,null,false );

                Button close = view.findViewById ( R.id.info_close );
                TextView infoText = view.findViewById ( R.id.info_text );

                infoText.setText ( "This page shows the simple and basic words with image, based on the topic selected with proper voice to spell it clearly." );

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
    public void onBackPressed() {
        exit ();
    }

    private void exit(){
        mediaPlayer.start ();
        final AlertDialog.Builder builder = new AlertDialog.Builder ( WordActivity.this,R.style.CustomDialog );
        View view = LayoutInflater.from ( WordActivity.this ).inflate ( R.layout.alphabet_write_dialog,null,false );
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
