package com.example.taskfour;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taskfour.Adapters.PlaylistAdapter;
import com.example.taskfour.Tutorial.ClassRoom;
import com.github.rtoshiro.view.video.FullscreenVideoLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TutorialActivity extends AppCompatActivity {

    public static FullscreenVideoLayout videoLayout;
    private DatabaseReference tutRef;
    private List<String> urlList = new ArrayList<> (  );
    private List<String> titleList = new ArrayList<> (  );
    private List<String> descriptionList = new ArrayList<> (  );
    private int tut;
    private ClassRoom classRoom;
    private RecyclerView recyclerView1;
    public static TextView title,description;
    private MediaPlayer mediaPlayer,mediaPlayer1;
    private TextView label;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        requestWindowFeature( Window.FEATURE_NO_TITLE);
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        final View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        setContentView ( R.layout.activity_tutorial );

        videoLayout = findViewById ( R.id.videoView );
        videoLayout.setActivity ( this );

        mediaPlayer = new MediaPlayer ().create(getApplicationContext (),R.raw.negative);
        mediaPlayer1 = new MediaPlayer ().create(getApplicationContext (),R.raw.rubberone);

        tut = getIntent ().getIntExtra ( "tut",1 );
        label = findViewById ( R.id.label );
        if (tut == 1){
            label.setText ( "Alphabets" );
        }
        else if (tut == 2){
            label.setText ( "Drawings" );
        }
        else if (tut ==3){
            label.setText ( "Numbers" );
        }

        urlList.clear ();
        titleList.clear ();
        descriptionList.clear ();

        recyclerView1 = findViewById ( R.id.play_list_recycler );
        title = findViewById ( R.id.title);
        description = findViewById ( R.id.description );

        tutRef = FirebaseDatabase.getInstance ().getReference ().child ( "Tutorial" );

        tutRef.addListenerForSingleValueEvent ( new ValueEventListener () {
            @Override
            public void onDataChange( @NonNull DataSnapshot dataSnapshot ) {
                if (tut == 1){
                    for (int i = 0; i < dataSnapshot.child ( "Alphabet" ).getChildrenCount () ; i++) {
                        classRoom = dataSnapshot.child ( "Alphabet" ).child ( "V"+String.valueOf ( i+1 ) ).getValue ( ClassRoom.class );
                        urlList.add ( classRoom.getUrl () );
                        titleList.add ( classRoom.getTitle () );
                        descriptionList.add ( classRoom.getDescription () );
                    }
                }
                else if (tut == 2){
                    for (int i = 0; i < dataSnapshot.child ( "Drawings" ).getChildrenCount () ; i++) {
                        classRoom = dataSnapshot.child ( "Drawings" ).child ( "V"+String.valueOf ( i+1 ) ).getValue ( ClassRoom.class );
                        urlList.add ( classRoom.getUrl () );
                        titleList.add ( classRoom.getTitle () );
                        descriptionList.add ( classRoom.getDescription () );
                    }
                }
                else if (tut == 3){
                    for (int i = 0; i < dataSnapshot.child ( "Numbers" ).getChildrenCount () ; i++) {
                        classRoom = dataSnapshot.child ( "Numbers" ).child ( "V"+String.valueOf ( i+1 ) ).getValue ( ClassRoom.class );
                        urlList.add ( classRoom.getUrl () );
                        titleList.add ( classRoom.getTitle () );
                        descriptionList.add ( classRoom.getDescription () );
                    }
                }
                title.setText ( titleList.get ( 0 ) );
                description.setText ( descriptionList.get ( 0 ) );
                try {
                    videoLayout.setVideoURI ( Uri.parse ( urlList.get ( 0 ) ) );
                } catch (IOException e) {
                    e.printStackTrace ();
                }
                videoLayout.start ();
                PlaylistAdapter playlistAdapter = new PlaylistAdapter ( urlList,titleList,descriptionList );
                recyclerView1.setHasFixedSize ( true );
                recyclerView1.setLayoutManager ( new LinearLayoutManager ( TutorialActivity.this ) );
                recyclerView1.setAdapter ( playlistAdapter );
            }

            @Override
            public void onCancelled( @NonNull DatabaseError databaseError ) {

            }
        } );

        final Button info = findViewById ( R.id.info );
        info.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                mediaPlayer.start ();
                final AlertDialog.Builder builder = new AlertDialog.Builder ( TutorialActivity.this,R.style.CustomDialog );
                View view = LayoutInflater.from ( TutorialActivity.this ).inflate ( R.layout.about_dialog,null,false );

                Button close = view.findViewById ( R.id.info_close );
                TextView infoText = view.findViewById ( R.id.info_text );

                infoText.setText ( "This page shows the list of video based on the topic selected and content of this video will be updated frequently." );

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

        Button back = findViewById ( R.id.back );
        back.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                exit ();
            }
        } );

    }

    private void exit(){
        mediaPlayer.start ();
        final AlertDialog.Builder builder = new AlertDialog.Builder ( TutorialActivity.this,R.style.CustomDialog );
        View view = LayoutInflater.from ( TutorialActivity.this ).inflate ( R.layout.alphabet_write_dialog,null,false );
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
    public void onConfigurationChanged( @NonNull Configuration newConfig ) {
        super.onConfigurationChanged ( newConfig );
    }
    @Override
    public void onBackPressed() {
        exit ();
    }

    @Override
    protected void onPause() {
        super.onPause ();
        overridePendingTransition ( android.R.anim.fade_in,android.R.anim.fade_out );
    }

    @Override
    protected void onResume() {
        super.onResume ();
        full ();
    }
}