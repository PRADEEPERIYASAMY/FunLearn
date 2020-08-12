package com.example.taskfour;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.taskfour.Adapters.FilesAdapter;
import com.example.taskfour.alphabets.ListOfAlphabets;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ListColoredActivity extends AppCompatActivity {

    List<File> fileList;
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
        setContentView ( R.layout.activity_list_colored );
        Main2Activity.mediaPlayer.start ();
        mediaPlayer = new MediaPlayer ().create(getApplicationContext (),R.raw.negative);
        mediaPlayer1 = new MediaPlayer ().create(getApplicationContext (),R.raw.rubberone);
        RecyclerView recyclerView = findViewById ( R.id.recycler_view_files );
        recyclerView.setHasFixedSize ( true );
        GridLayoutManager gridLayoutManager = new GridLayoutManager ( this,2 );
        recyclerView.setLayoutManager ( gridLayoutManager );
        fileList = loadFiles ();
        FilesAdapter filesAdapter = new FilesAdapter ( this,fileList,2 );
        recyclerView.setAdapter ( filesAdapter );

        Button back = findViewById ( R.id.back );
        back.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                mediaPlayer.start ();
                finish ();
            }
        } );

        final Button info = findViewById ( R.id.info );
        info.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                mediaPlayer.start ();
                final AlertDialog.Builder builder = new AlertDialog.Builder ( ListColoredActivity.this,R.style.CustomDialog );
                View view = LayoutInflater.from ( ListColoredActivity.this ).inflate ( R.layout.about_dialog,null,false );

                Button close = view.findViewById ( R.id.info_close );
                TextView infoText = view.findViewById ( R.id.info_text );

                infoText.setText ( "This page shows the images saved after colouring, and allowed to delete from list or use it once again to recolor" );

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

    private List<File> loadFiles() {
        ArrayList<File> inFiles = new ArrayList<> (  );
        File parentDir = new File ( getExternalFilesDir ( Environment.DIRECTORY_PICTURES )+File.separator+"Coloured"  );
        File[] files = parentDir.listFiles ();

        try {
            for (File file:files){
                if (file.getName ().endsWith ( ".png" )){
                    inFiles.add ( file );
                }
            }

            TextView textView = findViewById ( R.id.status_empty );

            if (files.length >0){
                textView.setVisibility ( View.GONE );

            }
            else {
                textView.setVisibility ( View.VISIBLE );
            }

        }
        catch (NullPointerException e){
            TextView textView = findViewById ( R.id.status_empty );
            textView.setVisibility ( View.VISIBLE );
        }

        return inFiles;
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
