package com.example.taskfour;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.taskfour.Adapters.WriteAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pl.droidsonroids.gif.GifDrawable;

public class CountingActivity extends AppCompatActivity {

    private Button option1,option2,option3,retry,next;
    private MediaPlayer mediaPlayer,mediaPlayer1,mediaPlayer2,mediaPlayer3;

    private List<RecyclerView> recyclerViewList;
    private RecyclerView recyclerView1;
    private RecyclerView recyclerView2;
    private RecyclerView recyclerView3;
    private RecyclerView recyclerView4;
    private RecyclerView recyclerView5;
    private RecyclerView recyclerView6;
    private RecyclerView recyclerView7;
    private RecyclerView recyclerView8;
    private RecyclerView recyclerView9;
    private RecyclerView recyclerView10;
    private RelativeLayout container;
    private CardView c1,c2,c3,c4,c5;

    private static int index = 0;
    private static int type = 0;
    private ImageView wrong;
    private List<String> selected = new ArrayList<> (  );


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
        setContentView ( R.layout.activity_counting );

        mediaPlayer = new MediaPlayer ().create(getApplicationContext (),R.raw.negative);
        mediaPlayer1 = new MediaPlayer ().create(getApplicationContext (),R.raw.rubberone);
        mediaPlayer3 = new MediaPlayer ().create(getApplicationContext (),R.raw.explosion);

        option1 = findViewById ( R.id.option_one );
        option2 = findViewById ( R.id.option_two );
        option3 = findViewById ( R.id.option_three );
        retry = findViewById ( R.id.retry );
        next = findViewById ( R.id.next );
        wrong = findViewById ( R.id.addition_wrong );
        container = findViewById ( R.id.container );

        c1 = findViewById ( R.id.c1 );
        c2 = findViewById ( R.id.c2 );
        c3 = findViewById ( R.id.c3 );
        c4 = findViewById ( R.id.c4 );
        c5 = findViewById ( R.id.c5 );

        wrong.setVisibility ( View.INVISIBLE );

        master ();
        final Animation animation = AnimationUtils.loadAnimation ( getApplicationContext (),R.anim.button );

        View.OnClickListener onClickListener = new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                mediaPlayer1.start ();
                switch (v.getId ()){
                    case R.id.option_one:
                        c1.startAnimation ( animation );
                        dissable ();
                        if (String.valueOf ( index ).equals ( option1.getText ().toString () )){
                            right ();
                        }
                        else {
                           wrong();
                        }
                        break;
                    case R.id.option_two:
                        c2.startAnimation ( animation );
                        dissable ();
                        if (String.valueOf ( index ).equals ( option2.getText ().toString () )){
                            right ();
                        }
                        else {
                           wrong ();
                        }
                        break;
                    case R.id.option_three:
                        c3.startAnimation ( animation );
                        dissable ();
                        if (String.valueOf ( index ).equals ( option3.getText ().toString () )){
                            right ();
                        }
                        else {
                            wrong();
                        }
                        break;
                    case R.id.retry:
                        c4.startAnimation ( animation );
                        enabled ();
                        wrong.setVisibility ( View.INVISIBLE );
                        container.setVisibility ( View.VISIBLE );
                        break;
                    case R.id.next:
                        c5.startAnimation ( animation );
                        mediaPlayer2.stop ();
                        enabled ();
                        master ();
                        break;
                }
            }
        };
        option1.setOnClickListener ( onClickListener );
        option2.setOnClickListener ( onClickListener );
        option3.setOnClickListener ( onClickListener );
        retry.setOnClickListener ( onClickListener );
        next.setOnClickListener ( onClickListener );
        Button back = findViewById ( R.id.back );
        back.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                exit ();
            }
        } );

    }

    @Override
    protected void onPause() {
        super.onPause ();
        overridePendingTransition ( android.R.anim.fade_in,android.R.anim.fade_out );
        if (mediaPlayer2 != null){
            mediaPlayer2.pause ();
        }
    }

    @Override
    protected void onResume() {
        super.onResume ();
        if (mediaPlayer2 != null){
            mediaPlayer2.start ();
        }
        full ();
    }

    @Override
    public void onBackPressed() {
        exit ();
    }

    private void exit(){
        mediaPlayer.start ();
        final AlertDialog.Builder builder = new AlertDialog.Builder ( CountingActivity.this,R.style.CustomDialog );
        View view = LayoutInflater.from ( CountingActivity.this ).inflate ( R.layout.alphabet_write_dialog,null,false );
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

    private void valueSetter(){
        selected.clear ();
        List<String> firstNum = new ArrayList<> (  );

        for (int i = 1;i<100;i++){
            firstNum.add ( String.valueOf ( i ) );
        }

        Collections.shuffle ( firstNum );
        int order = 0;
        order = (int) (Math.random ()*98);
        selected.add ( firstNum.get ( order ) );
        firstNum.remove ( order );
        order = (int) (Math.random ()*97);
        selected.add ( firstNum.get ( order ));
        firstNum.remove ( order );
        order = (int) (Math.random ()*96);
        selected.add ( firstNum.get ( order ));
        firstNum.clear ();

    }

    private void recycler(){
        type = (int) ( Math.random () * 9 );
        recyclerView1 = findViewById ( R.id.recycler_write_one );
        recyclerView2 = findViewById ( R.id.recycler_write_two );
        recyclerView3 = findViewById ( R.id.recycler_write_three );
        recyclerView4 = findViewById ( R.id.recycler_write_four );
        recyclerView5 = findViewById ( R.id.recycler_write_five );
        recyclerView6 = findViewById ( R.id.recycler_write_six );
        recyclerView7 = findViewById ( R.id.recycler_write_seven );
        recyclerView8 = findViewById ( R.id.recycler_write_eight );
        recyclerView9 = findViewById ( R.id.recycler_write_nine );
        recyclerView10 = findViewById ( R.id.recycler_write_ten );

        recyclerViewList.clear ();

        recyclerViewList.add ( recyclerView1 );
        recyclerViewList.add ( recyclerView2 );
        recyclerViewList.add ( recyclerView3 );
        recyclerViewList.add ( recyclerView4 );
        recyclerViewList.add ( recyclerView5 );
        recyclerViewList.add ( recyclerView6 );
        recyclerViewList.add ( recyclerView7 );
        recyclerViewList.add ( recyclerView8 );
        recyclerViewList.add ( recyclerView9 );
        recyclerViewList.add ( recyclerView10 );
    }
    private void recylcerVisibility(int num){
        if (num == 10){
            for (RecyclerView recyclerView:recyclerViewList){
                recyclerView.setVisibility ( View.VISIBLE );
            }
        }
        else if (num != 0){
            for (int i = 0;i<=num;i++){
                recyclerViewList.get ( i ).setVisibility ( View.VISIBLE );
            }
        }
        else {
            recyclerViewList.get ( 0 ).setVisibility ( View.VISIBLE );
        }
    }
    private void recyclerInvisible(){
        for (RecyclerView recyclerView:recyclerViewList){
            recyclerView.setVisibility ( View.INVISIBLE );
        }
    }
    private void setcounting(int count){
        recycler ();
        wrong.setVisibility ( View.INVISIBLE );
        container.setVisibility ( View.VISIBLE );
        for (int i = 0;i<((int)(count/10));i++){
            recyclerViewList.get ( i ).setHasFixedSize ( true );
            recyclerViewList.get ( i ).setLayoutManager ( new GridLayoutManager ( getApplicationContext (),10 ) );
            recyclerViewList.get ( i ).setAdapter ( new WriteAdapter ( getApplicationContext (),10,type ) );
            recyclerViewList.get ( i ).getAdapter ().notifyDataSetChanged ();
        }
        if (((int)(count)/10) != 10){
            recyclerViewList.get ( ((int)(count)/10) ).setHasFixedSize ( true );
            recyclerViewList.get ( ((int)(count)/10) ).setLayoutManager ( new GridLayoutManager ( getApplicationContext (),10 ) );
            recyclerViewList.get ( ((int)(count)/10) ).setAdapter ( new WriteAdapter ( getApplicationContext (),((count)-(((int)(count)/10)*10)),type ) );
        }
        recyclerInvisible ();
        recylcerVisibility ( ((int)(count)/10) );
    }
    private void wrong(){
        mediaPlayer3.start ();
        retry.setEnabled ( true );
        next.setEnabled ( false );
        container.setVisibility ( View.INVISIBLE );
        wrong.setVisibility ( View.VISIBLE );
        wrong.setImageResource ( R.drawable.boom );
        ((GifDrawable)wrong.getDrawable()).start ();
        ((GifDrawable)wrong.getDrawable()).reset ();
        new Handler (  ).postDelayed ( new Runnable () {
            @Override
            public void run() {
                ((GifDrawable)wrong.getDrawable()).stop();
            }
        },((GifDrawable)wrong.getDrawable()).getDuration ()-300);
    }
    private void right(){
        mediaPlayer2 = new MediaPlayer ().create(getApplicationContext (),R.raw.beat);
        mediaPlayer2.setLooping ( true );
        mediaPlayer2.start ();
        retry.setEnabled ( false );
        container.setVisibility ( View.INVISIBLE );
        wrong.setVisibility ( View.VISIBLE );
        wrong.setImageResource ( R.drawable.dancer );
        next.setEnabled ( true );
    }
    private void dissable(){
        option3.setEnabled ( false );
        option2.setEnabled ( false );
        option1.setEnabled ( false );
    }
    private void enabled(){
        option1.setEnabled ( true );
        option2.setEnabled ( true );
        option3.setEnabled ( true );
    }
    private void master(){
        recyclerViewList = new ArrayList<> (  );
        recyclerViewList.clear ();

        valueSetter ();

        index = Integer.parseInt (selected.get (0));
        setcounting ( index );
        Collections.shuffle ( selected );
        option1.setText ( String.valueOf ( selected.get ( 0 ) ) );
        option2.setText ( String.valueOf ( selected.get ( 1 ) ) );
        option3.setText ( String.valueOf ( selected.get ( 2 ) ) );
        selected.clear ();
        retry.setEnabled ( false );
        next.setEnabled ( false );
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
