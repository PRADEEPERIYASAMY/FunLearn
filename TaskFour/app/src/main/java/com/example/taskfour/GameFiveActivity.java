package com.example.taskfour;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taskfour.Adapters.PuzzleAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameFiveActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private static List<String> id = new ArrayList<> (  );
    private static List<String> original = new ArrayList<> (  );
    private static List<Integer> measure = new ArrayList<> (  );
    private int image;
    private static TextView timer,introCountdown;
    private Button play,reset,exit;
    private RelativeLayout relativeLayout;
    private CountDownTimer countDownTimer,countDownTimer1;
    private MediaPlayer mediaPlayer,mediaPlayer1,mediaPlayer2;
    private CardView cexit,cplay,creset;
    public List<String> url = new ArrayList<> (  );

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
        setContentView ( R.layout.activity_game_five );
        image = getIntent ().getIntExtra ( "image",1 );

        mediaPlayer = new MediaPlayer ().create(getApplicationContext (),R.raw.puzzle);
        mediaPlayer1 = new MediaPlayer ().create(getApplicationContext (),R.raw.rubberone);
        mediaPlayer2 = new MediaPlayer ().create(getApplicationContext (),R.raw.negative);

        timer = findViewById ( R.id.timer );
        introCountdown = findViewById ( R.id.intro_countdown );
        play = findViewById ( R.id.play );
        reset = findViewById ( R.id.reset );
        exit = findViewById ( R.id.exit );
        relativeLayout = findViewById ( R.id.countdownl_container );

        timer.setText ( "150" );
        introCountdown.setText ( "3" );

        id.clear ();
        original.clear ();
        measure.clear ();

        cexit = findViewById ( R.id.exitcard );
        cplay = findViewById ( R.id.cardplay );
        creset = findViewById ( R.id.cardreset );

        countDownTimer = null;
        countDownTimer1 = null;

        fetchData ();

    }

    /*private void imagesetter(){
        if (image == 1){
            for (int i = 0;i<16;i++){
                id.add ( PuzzleImages.one[i] );
            }
            for (int i = 0;i<16;i++){
                original.add ( PuzzleImages.one[i] );
            }
        }
        else if (image == 2){
            for (int i = 0;i<16;i++){
                id.add ( PuzzleImages.two[i] );
            }
            for (int i = 0;i<16;i++){
                original.add ( PuzzleImages.two[i] );
            }
        }
        else if (image == 3){
            for (int i = 0;i<16;i++){
                id.add ( PuzzleImages.three[i] );
            }
            for (int i = 0;i<16;i++){
                original.add ( PuzzleImages.three[i] );
            }
        }
        else if (image == 4){
            for (int i = 0;i<16;i++){
                id.add ( PuzzleImages.four[i] );
            }
            for (int i = 0;i<16;i++){
                original.add ( PuzzleImages.four[i] );
            }
        }
        else if (image == 5){
            for (int i = 0;i<16;i++){
                id.add ( PuzzleImages.five[i] );
            }
            for (int i = 0;i<16;i++){
                original.add ( PuzzleImages.five[i] );
            }
        }
        else if (image == 6){
            for (int i = 0;i<16;i++){
                id.add ( PuzzleImages.six[i] );
            }
            for (int i = 0;i<16;i++){
                original.add ( PuzzleImages.six[i] );
            }
        }
        else if (image == 7){
            for (int i = 0;i<16;i++){
                id.add ( PuzzleImages.seven[i] );
            }
            for (int i = 0;i<16;i++){
                original.add ( PuzzleImages.seven[i] );
            }
        }
        else if (image == 8){
            for (int i = 0;i<16;i++){
                id.add ( PuzzleImages.eight[i] );
            }
            for (int i = 0;i<16;i++){
                original.add ( PuzzleImages.eight[i] );
            }
        }
        else if (image == 9){
            for (int i = 0;i<16;i++){
                id.add ( PuzzleImages.nine[i] );
            }
            for (int i = 0;i<16;i++){
                original.add ( PuzzleImages.nine[i] );
            }
        }
        else if (image == 10){
            for (int i = 0;i<16;i++){
                id.add ( PuzzleImages.ten[i] );
            }
            for (int i = 0;i<16;i++){
                original.add ( PuzzleImages.ten[i] );
            }
        }
    }*/

    @Override
    public void onBackPressed() {
        if (image == 1000){
            super.onBackPressed ();
        }
        else {
            exit ();
        }
    }

    private void exit(){
        mediaPlayer2.start ();
        final String last = timer.getText ().toString ();
        final String lastIntro = introCountdown.getText ().toString ();

        if (play.getVisibility () == View.VISIBLE && countDownTimer1 != null){
            countDownTimer1.cancel ();
        }
        if (play.getVisibility () == View.INVISIBLE && countDownTimer != null){
            countDownTimer.cancel ();
        }
        final AlertDialog.Builder builder = new AlertDialog.Builder ( GameFiveActivity.this,R.style.CustomDialog );
        View view = LayoutInflater.from ( GameFiveActivity.this ).inflate ( R.layout.alphabet_write_dialog,null,false );
        Button yes = view.findViewById ( R.id.yes );
        Button no = view.findViewById ( R.id.no );
        TextView firstText = view.findViewById ( R.id.firstText );
        TextView secondText = view.findViewById ( R.id.secontText );
        ImageView dialogImage = view.findViewById ( R.id.dialog_image );

        firstText.setText ( "Oh noooo !" );
        secondText.setText ( "Do you want to exit?" );
        dialogImage.setImageResource ( R.drawable.oh );
        final AlertDialog alertDialog = builder.create ();
        alertDialog.setView ( view );

        no.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                mediaPlayer1.start ();
                if (play.getVisibility () == View.VISIBLE && countDownTimer1 != null){
                    introCountdown.setText ( lastIntro );
                    countDownTimer1.start ();
                }
                else if (play.getVisibility () == View.INVISIBLE && countDownTimer != null){
                    timer.setText ( last );
                    countDownTimer.start ();
                }
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
    @Override
    protected void onPause() {
        super.onPause ();
        overridePendingTransition ( android.R.anim.fade_in,android.R.anim.fade_out );
        if (play.getVisibility () == View.VISIBLE && countDownTimer1 != null){
            countDownTimer1.cancel ();
        }
        if (play.getVisibility () == View.INVISIBLE && countDownTimer != null){
            countDownTimer.cancel ();
        }
    }

    @Override
    protected void onResume() {
        super.onResume ();
        if (play.getVisibility () == View.VISIBLE && countDownTimer1 != null){
            countDownTimer1.start ();
        }
        if (play.getVisibility () == View.INVISIBLE && countDownTimer != null){
            countDownTimer.start ();
        }
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

    private void fetchData(){
        url.clear ();
        DatabaseReference countRef = FirebaseDatabase.getInstance ().getReference ().child ( "Puzzle" );
        countRef.addListenerForSingleValueEvent ( new ValueEventListener () {
            @Override
            public void onDataChange( @NonNull DataSnapshot dataSnapshot ) {
                if (dataSnapshot.exists ()){
                    for (int i = 0; i < dataSnapshot.child ( String.valueOf ( image ) ).getChildrenCount (); i++) {
                        url.add ( dataSnapshot.child ( String.valueOf ( image ) ).child ( String.valueOf ( i+1 ) ).getValue ().toString () );
                    }

                    for (int i = 0;i<16;i++){
                        id.add ( url.get ( i ) );
                    }
                    for (int i = 0;i<16;i++){
                        original.add ( url.get ( i ) );
                    }

                    Collections.shuffle ( id );

                    for (int i = 0; i<16;i++){
                        measure.add ( original.indexOf ( id.get ( i ) )+1 ) ;
                    }

                    recyclerView = findViewById ( R.id.puzzle_recycler );
                    recyclerView.setHasFixedSize ( true );
                    recyclerView.setVisibility ( View.INVISIBLE );
                    recyclerView.setLayoutManager ( new GridLayoutManager ( getApplicationContext (),4 ) );
                    recyclerView.setAdapter ( new PuzzleAdapter ( getApplicationContext (), id, original ) );

                    View.OnClickListener onClickListener = new View.OnClickListener () {
                        @Override
                        public void onClick( View v ) {
                            switch (v.getId ()){
                                case R.id.play:
                                    mediaPlayer1.start ();
                                    cplay.startAnimation ( AnimationUtils.loadAnimation ( getApplicationContext (),R.anim.button ) );
                                    play.setEnabled ( false );
                                    countDownTimer1 = new CountDownTimer (3000,1000) {
                                        @Override
                                        public void onTick( long millisUntilFinished ) {
                                            if (Integer.parseInt ( introCountdown.getText ().toString () ) > 0){
                                                introCountdown.setText ( String.valueOf ( Integer.parseInt ( introCountdown.getText ().toString () )-1 ) );
                                            }
                                        }

                                        @Override
                                        public void onFinish() {
                                            countDownTimer  = new CountDownTimer (150000,1000) {
                                                @Override
                                                public void onTick( long millisUntilFinished ) {
                                                    int time = Integer.parseInt ( timer.getText ().toString () );
                                                    time = time-1;
                                                    timer.setText ( String.valueOf ( time ) );
                                                }

                                                @Override
                                                public void onFinish() {

                                                }
                                            };
                                            countDownTimer.start ();
                                            relativeLayout.setVisibility ( View.INVISIBLE );
                                            play.setVisibility ( View.INVISIBLE );
                                            recyclerView.setVisibility ( View.VISIBLE );
                                        }
                                    }.start ();
                                    break;
                                case R.id.reset:
                                    mediaPlayer1.start ();
                                    creset.startAnimation ( AnimationUtils.loadAnimation ( getApplicationContext (),R.anim.button ) );
                                    final String last = timer.getText ().toString ();
                                    final String lastIntro = introCountdown.getText ().toString ();

                                    if (play.getVisibility () == View.VISIBLE && countDownTimer1 != null){
                                        countDownTimer1.cancel ();
                                    }
                                    if (play.getVisibility () == View.INVISIBLE && countDownTimer != null){
                                        countDownTimer.cancel ();
                                    }
                                    final AlertDialog.Builder builder2 = new AlertDialog.Builder ( GameFiveActivity.this,R.style.CustomDialog );
                                    View view2 = LayoutInflater.from ( GameFiveActivity.this ).inflate ( R.layout.alphabet_write_dialog,null,false );
                                    Button yes2 = view2.findViewById ( R.id.yes );
                                    Button no2 = view2.findViewById ( R.id.no );
                                    TextView firstText2 = view2.findViewById ( R.id.firstText );
                                    TextView secondText2 = view2.findViewById ( R.id.secontText );
                                    ImageView dialogImage2 = view2.findViewById ( R.id.dialog_image );

                                    firstText2.setText ( "Are you sure !" );
                                    secondText2.setText ( "Do you want to reset?" );

                                    dialogImage2.setImageResource ( R.drawable.omg );
                                    final AlertDialog alertDialog2 = builder2.create ();
                                    alertDialog2.setView ( view2 );
                                    mediaPlayer2.start ();
                                    no2.setOnClickListener ( new View.OnClickListener () {
                                        @Override
                                        public void onClick( View v ) {
                                            mediaPlayer1.start ();
                                            if (play.getVisibility () == View.VISIBLE && countDownTimer1 != null){
                                                introCountdown.setText ( lastIntro );
                                                countDownTimer1.start ();
                                            }
                                            else if (play.getVisibility () == View.INVISIBLE && countDownTimer != null){
                                                timer.setText ( last );
                                                countDownTimer.start ();
                                            }
                                            alertDialog2.cancel ();
                                            full ();
                                        }
                                    } );

                                    yes2.setOnClickListener ( new View.OnClickListener () {
                                        @Override
                                        public void onClick( View v ) {
                                            mediaPlayer1.start ();
                                            Intent intent = new Intent ( getApplicationContext (),GameFiveActivity.class );
                                            intent.putExtra ( "id",image );
                                            startActivity ( intent );
                                            finish ();
                                        }
                                    } );
                                    alertDialog2.setCanceledOnTouchOutside ( false );
                                    alertDialog2.show ();
                                    break;
                                case R.id.exit:
                                    mediaPlayer1.start ();
                                    cexit.startAnimation ( AnimationUtils.loadAnimation ( getApplicationContext (),R.anim.button ) );
                                    exit ();
                                    break;
                            }
                        }
                    };

                    play.setOnClickListener ( onClickListener );
                    reset.setOnClickListener ( onClickListener );
                    exit.setOnClickListener ( onClickListener );

                    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback ( ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END ,0) {
                        @Override
                        public boolean onMove( @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target ) {
                            int d = viewHolder.getAdapterPosition ();
                            int t = target.getAdapterPosition ();

                            Collections.swap ( id,d,t );
                            Collections.swap ( measure,d,t );
                            recyclerView.getAdapter ().notifyDataSetChanged ();
                            mediaPlayer.start ();

                            List<Integer> verify = new ArrayList<> (  );
                            verify.clear ();
                            for (int i = 0;i<16;i++){
                                if ( measure.get ( i )  == i+1 ){
                                    verify.add ( i+1 );
                                }
                            }

                            if (verify.size () == 16){
                                final AlertDialog.Builder builder2 = new AlertDialog.Builder ( GameFiveActivity.this,R.style.CustomDialog );
                                View view2 = LayoutInflater.from ( GameFiveActivity.this ).inflate ( R.layout.alphabet_write_dialog,null,false );
                                Button yes2 = view2.findViewById ( R.id.yes );
                                Button no2 = view2.findViewById ( R.id.no );
                                TextView firstText2 = view2.findViewById ( R.id.firstText );
                                TextView secondText2 = view2.findViewById ( R.id.secontText );
                                ImageView dialogImage2 = view2.findViewById ( R.id.dialog_image );
                                mediaPlayer2.start ();
                                countDownTimer.cancel ();
                                if (Integer.parseInt ( timer.getText ().toString () ) <=15){
                                    firstText2.setText ( timer.getText ().toString ()+" is poor..." );
                                    dialogImage2.setImageResource ( R.drawable.poor );
                                }
                                else if (Integer.parseInt ( timer.getText ().toString () )<= 100){
                                    firstText2.setText (  timer.getText ().toString ()+" is good..." );
                                    dialogImage2.setImageResource ( R.drawable.good );
                                }
                                else {
                                    firstText2.setText (  timer.getText ().toString ()+" is excellent..." );
                                    dialogImage2.setImageResource ( R.drawable.excellent );
                                }

                                if (image < 5){
                                    secondText2.setText ( "Wanna move on ?" );
                                }
                                else {
                                    secondText2.setText ( "Wanna start fresh ?" );
                                }

                                final AlertDialog alertDialog2 = builder2.create ();
                                alertDialog2.setView ( view2 );

                                no2.setOnClickListener ( new View.OnClickListener () {
                                    @Override
                                    public void onClick( View v ) {
                                        mediaPlayer1.start ();
                                        finish ();
                                    }
                                } );

                                yes2.setOnClickListener ( new View.OnClickListener () {
                                    @Override
                                    public void onClick( View v ) {
                                        mediaPlayer1.start ();
                                        if (image<5){
                                            Intent intent =  new Intent ( getApplicationContext (),GameFiveActivity.class );
                                            intent.putExtra ( "image",image+1 );
                                            finish ();
                                            startActivity ( intent);
                                        }
                                        else {
                                            Intent intent =  new Intent ( getApplicationContext (),GameFiveActivity.class );
                                            intent.putExtra ( "image",1 );
                                            finish ();
                                            startActivity ( intent);
                                        }
                                    }
                                } );
                                alertDialog2.setCanceledOnTouchOutside ( false );
                                alertDialog2.show ();
                            }

                            return true;
                        }

                        @Override
                        public void onSwiped( @NonNull RecyclerView.ViewHolder viewHolder, int direction ) {

                        }
                    };

                    ItemTouchHelper itemTouchHelper = new ItemTouchHelper ( simpleCallback );
                    itemTouchHelper.attachToRecyclerView ( recyclerView );
                }
            }

            @Override
            public void onCancelled( @NonNull DatabaseError databaseError ) {

            }
        } );
    }
}
