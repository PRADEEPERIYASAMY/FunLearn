package com.example.taskfour;

import androidx.annotation.NonNull;
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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.taskfour.Adapters.WordsAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WordActivity extends AppCompatActivity {

    private int id;
    private RecyclerView recyclerView;
    private TextView textView;
    private Button back;
    private MediaPlayer mediaPlayer,mediaPlayer1;
    public static List<String> names = new ArrayList<> (  );
    public static List<String> images = new ArrayList<> (  );

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

        fetchData ();

        switch (id){
            case 1:
                textView.setText ( "Fruits" );
                break;
            case 2:
                textView.setText ( "Vegetables" );
                break;
            case 3:
                textView.setText ( "Spices" );
                break;
            case 4:
                textView.setText ( "Space" );
                break;
            case 5:
                textView.setText ( "Musical" );
                break;
            case 6:
                textView.setText ( "Birds" );
                break;
            case 7:
                textView.setText ( "Wild" );
                break;
            case 8:
                textView.setText ( "Aquatic" );
                break;
            case 9:
                textView.setText ( "Domestic" );
                break;
            case 10:
                textView.setText ( "Insects" );
                break;
            case 11:
                textView.setText ( "Colors" );
                break;
            case 12:
                textView.setText ( "School" );
                break;
            case 13:
                textView.setText ( "Vehicles" );
                break;
            case 14:
                textView.setText ( "Sports" );
                break;
            case 15:
                textView.setText ( "Jobs" );
                break;
            case 16:
                textView.setText ( "Shapes" );
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

    private void fetchData(){
        final ProgressBar progressBar = findViewById ( R.id.progressBar2 );
        progressBar.setMax ( 100 );
        progressBar.setVisibility ( View.VISIBLE );
        DatabaseReference wordRef = FirebaseDatabase.getInstance ().getReference ().child ( "AlphabetWords" );
        wordRef.addListenerForSingleValueEvent ( new ValueEventListener () {
            @Override
            public void onDataChange( @NonNull DataSnapshot dataSnapshot ) {
                if (dataSnapshot.exists ()){
                    String n = dataSnapshot.child ( "Words" ).child ( String.valueOf ( id ) ).getValue ().toString ();
                    String im = dataSnapshot.child ( "Images" ).child ( String.valueOf ( id ) ).getValue ().toString ();
                    names = Arrays.asList ( n.split ( "----------" ) );
                    images = Arrays.asList ( im.split ( "----------" ) );
                    recyclerView.setHasFixedSize ( true );
                    recyclerView.setLayoutManager ( new GridLayoutManager ( getApplicationContext (),2 ) );
                    recyclerView.setAdapter ( new WordsAdapter ( getApplicationContext (),id ) );
                    progressBar.setVisibility ( View.INVISIBLE );
                }
            }

            @Override
            public void onCancelled( @NonNull DatabaseError databaseError ) {
                progressBar.setVisibility ( View.INVISIBLE );
            }
        } );
    }
}
