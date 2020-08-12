package com.example.taskfour;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.taskfour.Adapters.AlphabetAdapters;

public class AlphabetItemActivity extends AppCompatActivity {

    private RecyclerView alphabetItemRecyclerView;
    private Button back;
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
        setContentView ( R.layout.activity_alphabet_item );

        Main2Activity.mediaPlayer.start ();
        mediaPlayer = new MediaPlayer ().create(getApplicationContext (),R.raw.negative);
        mediaPlayer1 = new MediaPlayer ().create ( getApplicationContext () , R.raw.rubberone );

        alphabetItemRecyclerView = findViewById ( R.id.recycler_alphabet );
        back = findViewById ( R.id.back );
        alphabetItemRecyclerView.setHasFixedSize ( true );
        alphabetItemRecyclerView.setLayoutManager ( new LinearLayoutManager ( getApplicationContext ()) );

        AlphabetAdapters alphabetAdapters = new AlphabetAdapters ( getApplicationContext () );
        alphabetItemRecyclerView.setAdapter ( alphabetAdapters );

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
                final AlertDialog.Builder builder = new AlertDialog.Builder ( AlphabetItemActivity.this,R.style.CustomDialog );
                View view = LayoutInflater.from ( AlphabetItemActivity.this ).inflate ( R.layout.about_dialog,null,false );

                Button close = view.findViewById ( R.id.info_close );
                TextView infoText = view.findViewById ( R.id.info_text );

                infoText.setText ( "This page shows the list of alphabets with small and capital letters, users are requested to select any one alphabet to know details about it." );

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
