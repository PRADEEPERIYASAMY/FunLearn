package com.example.taskfour;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.taskfour.Adapters.MatchAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AlphabetMatchActivity extends AppCompatActivity {

    private ImageView i1,i2,i3,i4,i5;
    private TextView t1,t2,t3,t4,t5;
    private List<String> selected = new ArrayList<> (  );
    private List<String> all = new ArrayList<> (  );
    private static List<String> shuffled = new ArrayList<> (  );
    private RecyclerView recyclerView;
    private Button back;
    private MediaPlayer mediaPlayer,mediaPlayer1,mediaPlayer2;
    public static List<String> names = new ArrayList<> (  );
    private List<String> images = new ArrayList<> (  );

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
        setContentView ( R.layout.activity_alphabet_match );
        mediaPlayer = new MediaPlayer ().create(getApplicationContext (),R.raw.negative);
        mediaPlayer1 = new MediaPlayer ().create(getApplicationContext (),R.raw.rubberone);
        mediaPlayer2 = new MediaPlayer ().create(getApplicationContext (),R.raw.puzzle);

        recyclerView = findViewById ( R.id.match_recycler );
        back = findViewById ( R.id.back );

        i1 = findViewById ( R.id.i1 );
        i2 = findViewById ( R.id.i2 );
        i3 = findViewById ( R.id.i3 );
        i4 = findViewById ( R.id.i4 );
        i5 = findViewById ( R.id.i5 );

        t1 = findViewById ( R.id.t1 );
        t2 = findViewById ( R.id.t2 );
        t3 = findViewById ( R.id.t3 );
        t4 = findViewById ( R.id.t4 );
        t5 = findViewById ( R.id.t5 );

        fetchData ();

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback (ItemTouchHelper.UP|ItemTouchHelper.DOWN|ItemTouchHelper.START|ItemTouchHelper.END,0) {
            @Override
            public boolean onMove( @NonNull RecyclerView recyclerView , @NonNull RecyclerView.ViewHolder viewHolder , @NonNull RecyclerView.ViewHolder target ) {

                int d = viewHolder.getAdapterPosition ();
                int t = target.getAdapterPosition ();

                Collections.swap ( shuffled,d,t );
                recyclerView.getAdapter ().notifyDataSetChanged ();
                mediaPlayer2.start ();
                List<Integer> var = new ArrayList<> (  );
                var.clear ();

                for (int i = 0; i< 5;i++){
                    if ( selected.get ( i ) == shuffled.get ( i ) ){
                        var.add ( 1 );
                    }
                }

                if (var.size () == 5){
                    mediaPlayer.start ();
                    final AlertDialog.Builder builder = new AlertDialog.Builder ( AlphabetMatchActivity.this,R.style.CustomDialog );
                    View view = LayoutInflater.from ( AlphabetMatchActivity.this ).inflate ( R.layout.alphabet_write_dialog,null,false );
                    Button yes = view.findViewById ( R.id.yes );
                    Button no = view.findViewById ( R.id.no );
                    TextView firstText = view.findViewById ( R.id.firstText );
                    TextView secondText = view.findViewById ( R.id.secontText );
                    ImageView dialogImage = view.findViewById ( R.id.dialog_image );

                    firstText.setText ( "Well done !" );
                    secondText.setText ( "Wanna move to next ?" );
                    dialogImage.setImageResource ( R.drawable.excellent );
                    final AlertDialog alertDialog = builder.create ();
                    alertDialog.setView ( view );

                    no.setOnClickListener ( new View.OnClickListener () {
                        @Override
                        public void onClick( View v ) {
                            mediaPlayer1.start ();
                            finish ();
                        }
                    } );

                    yes.setOnClickListener ( new View.OnClickListener () {
                        @Override
                        public void onClick( View v ) {
                            mediaPlayer1.start ();
                            startActivity ( new Intent ( getApplicationContext (),AlphabetMatchActivity.class ) );
                            finish ();
                        }
                    } );
                    alertDialog.setCanceledOnTouchOutside ( false );
                    alertDialog.show ();
                }

                return true;
            }

            @Override
            public void onSwiped( @NonNull RecyclerView.ViewHolder viewHolder , int direction ) {

            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper ( simpleCallback );
        itemTouchHelper.attachToRecyclerView ( recyclerView );

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
                mediaPlayer.start ();
                final AlertDialog.Builder builder = new AlertDialog.Builder ( AlphabetMatchActivity.this,R.style.CustomDialog );
                View view = LayoutInflater.from ( AlphabetMatchActivity.this ).inflate ( R.layout.about_dialog,null,false );

                Button close = view.findViewById ( R.id.info_close );
                TextView infoText = view.findViewById ( R.id.info_text );

                infoText.setText ( "Users are requested to find the correct words which matches the given image. Once completed user are asked to move on or skip the session." );

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
        final AlertDialog.Builder builder = new AlertDialog.Builder ( AlphabetMatchActivity.this,R.style.CustomDialog );
        View view = LayoutInflater.from ( AlphabetMatchActivity.this ).inflate ( R.layout.alphabet_write_dialog,null,false );
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
        DatabaseReference matchRef = FirebaseDatabase.getInstance ().getReference ().child ( "Match" );
        matchRef.addListenerForSingleValueEvent ( new ValueEventListener () {
            @Override
            public void onDataChange( @NonNull DataSnapshot dataSnapshot ) {
                if (dataSnapshot.exists ()){
                    String n = dataSnapshot.child ( "Names" ).getValue ().toString ();
                    String im = dataSnapshot.child ( "Images" ).getValue ().toString ();
                    names = Arrays.asList ( n.split ( "----------" ) );
                    images = Arrays.asList ( im.split ( "----------" ) );
                    all.clear ();
                    selected.clear ();
                    shuffled.clear ();

                    for (int i = 0; i < names.size (); i++) {
                        all.add ( String.valueOf ( i ) );
                    }

                    for (int i = 0; i < 5; i++) {
                        int index = (int) ( Math.random () * all.size () ) -1;
                        selected.add ( all.get ( index ) );
                        all.remove ( index );
                    }

                    Collections.shuffle ( selected );

                    for (int i = 0; i < 5; i++) {
                        shuffled.add ( selected.get ( i ) );
                    }

                    Collections.shuffle ( shuffled );

                    Glide.with ( getApplicationContext () ).load ( images.get ( Integer.parseInt ( selected.get ( 0 ) ) ) ).into ( i1 );
                    Glide.with ( getApplicationContext () ).load ( images.get ( Integer.parseInt ( selected.get ( 1 ) ) ) ).into ( i2 );
                    Glide.with ( getApplicationContext () ).load ( images.get ( Integer.parseInt ( selected.get ( 2 ) ) ) ).into ( i3 );
                    Glide.with ( getApplicationContext () ).load ( images.get ( Integer.parseInt ( selected.get ( 3 ) ) ) ).into ( i4 );
                    Glide.with ( getApplicationContext () ).load ( images.get ( Integer.parseInt ( selected.get ( 4 ) ) ) ).into ( i5 );

                    t1.setText ( selected.get ( 0 ) );
                    t2.setText ( selected.get ( 1 ) );
                    t3.setText ( selected.get ( 2 ) );
                    t4.setText ( selected.get ( 3 ) );
                    t5.setText ( selected.get ( 4 ) );

                    recyclerView.setHasFixedSize ( true );
                    recyclerView.setLayoutManager ( new LinearLayoutManager ( getApplicationContext () ) );
                    recyclerView.setAdapter ( new MatchAdapter ( getApplicationContext (),shuffled ) );
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
