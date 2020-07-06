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
    private MediaPlayer mediaPlayer;

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
