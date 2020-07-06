package com.example.taskfour;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taskfour.Adapters.WriteAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pl.droidsonroids.gif.GifDrawable;

public class AdditionActivity extends AppCompatActivity {

    private static int first = 0;
    private static int second = 0;
    private MediaPlayer mediaPlayer,mediaPlayer1,mediaPlayer2,mediaPlayer3;

    private TextView firstNum,secondNum,resultName;
    private EditText answer;
    private Button submit,retry,next;
    private ImageView wrong;
    private static int index = 0;

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
    private CardView c1;

    @Override
    protected void onCreate( final Bundle savedInstanceState ) {
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
        setContentView ( R.layout.activity_addition );
        mediaPlayer = new MediaPlayer ().create(getApplicationContext (),R.raw.negative);
        mediaPlayer1 = new MediaPlayer ().create(getApplicationContext (),R.raw.rubberone);
        mediaPlayer3 = new MediaPlayer ().create(getApplicationContext (),R.raw.explosion);
        mediaPlayer2 = new MediaPlayer ().create(getApplicationContext (),R.raw.sucess);

        valueSetter ();

        firstNum = findViewById ( R.id.addition_first_num );
        secondNum = findViewById ( R.id.addition_second_num );
        answer = findViewById ( R.id.addition_answer );
        submit = findViewById ( R.id.submit );
        retry = findViewById ( R.id.retry );
        next = findViewById ( R.id.next );
        wrong = findViewById ( R.id.addition_wrong );
        resultName = findViewById ( R.id.result_name );
        container = findViewById ( R.id.container );

        recyclerViewList = new ArrayList<> (  );

        wrong.setVisibility ( View.INVISIBLE );
        retry.setVisibility ( View.INVISIBLE );
        next.setVisibility ( View.INVISIBLE );
        firstNum.setText ( String.valueOf ( first ) );
        secondNum.setText ( String.valueOf ( second ) );
        container.setVisibility ( View.INVISIBLE );

        c1 = findViewById ( R.id.c3 );
        final Animation animation = AnimationUtils.loadAnimation ( getApplicationContext (),R.anim.button );

        submit.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                c1.startAnimation ( animation );
                mediaPlayer1.start ();
                if (answer.getText ().toString ().equals ( String.valueOf ( first + second ) )){

                    recycler ();
                    mediaPlayer2.start ();

                    visibility ( 3 );
                    wrong.setVisibility ( View.INVISIBLE );
                    container.setVisibility ( View.VISIBLE );
                    answer.setEnabled ( false );
                    resultName.setText ( "Correct" );
                    for (int i = 0;i<((int)(first+second)/10);i++){
                        recyclerViewList.get ( i ).setHasFixedSize ( true );
                        recyclerViewList.get ( i ).setLayoutManager ( new GridLayoutManager ( getApplicationContext (),10 ) );
                        recyclerViewList.get ( i ).setAdapter ( new WriteAdapter ( getApplicationContext (),10,index ) );
                        recyclerViewList.get ( i ).getAdapter ().notifyDataSetChanged ();
                    }
                    if (((int)(first+second)/10) != 10){
                        recyclerViewList.get ( ((int)(first+second)/10) ).setHasFixedSize ( true );
                        recyclerViewList.get ( ((int)(first+second)/10) ).setLayoutManager ( new GridLayoutManager ( getApplicationContext (),10 ) );
                        recyclerViewList.get ( ((int)(first+second)/10) ).setAdapter ( new WriteAdapter ( getApplicationContext (),((first+second)-(((int)(first+second)/10)*10)),index ) );
                    }
                    recyclerInvisible ();
                    recylcerVisibility ( ((int)(first+second)/10) );
                }
                else {
                    mediaPlayer3.start ();
                    wrong.setVisibility ( View.VISIBLE );
                    ((GifDrawable)wrong.getDrawable()).start ();
                    ((GifDrawable)wrong.getDrawable()).reset ();
                    resultName.setText ( "Wrong" );
                    new Handler (  ).postDelayed ( new Runnable () {
                        @Override
                        public void run() {
                            ((GifDrawable)wrong.getDrawable()).stop();
                        }
                    },((GifDrawable)wrong.getDrawable()).getDuration ()-300);
                    visibility ( 2 );
                    answer.setEnabled ( false );
                }

                down ();

            }
        } );

        retry.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                c1.startAnimation ( animation );
                mediaPlayer1.start ();
                resultName.setText ( "Result" );
                container.setVisibility ( View.INVISIBLE );
                answer.setEnabled ( true );
                answer.setText ( "" );
                wrong.setVisibility ( View.INVISIBLE );
               visibility ( 1 );
            }
        } );

        next.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                c1.startAnimation ( animation );
                mediaPlayer1.start ();
                resultName.setText ( "Result" );
                visibility ( 1 );
                container.setVisibility ( View.INVISIBLE );
                valueSetter ();
                firstNum.setText ( String.valueOf ( first ) );
                secondNum.setText ( String.valueOf ( second ) );
                wrong.setVisibility ( View.INVISIBLE );
                answer.setText ( "" );
                answer.setEnabled ( true );
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

    @Override
    public void onBackPressed() {
        exit ();
    }

    private void exit(){
        down ();
        mediaPlayer.start ();
        final AlertDialog.Builder builder = new AlertDialog.Builder ( AdditionActivity.this,R.style.CustomDialog );
        View view = LayoutInflater.from ( AdditionActivity.this ).inflate ( R.layout.alphabet_write_dialog,null,false );
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
        List<Integer> firstNum = new ArrayList<> (  );
        List<Integer> secondNum = new ArrayList<> (  );
        first = 0;
        second = 0;
        for (int i = 1;i<100;i++){
            firstNum.add ( i );
        }
        Collections.shuffle ( firstNum );
        first = firstNum.get ( (int) (Math.random ()*98) );

        for (int i:firstNum){
            if (i+first <= 100){
                secondNum.add ( i );
            }
        }

        Collections.shuffle ( secondNum );
        second = secondNum.get ( (int) (Math.random ()*(secondNum.size ()-1)) );
    }

    private void visibility(int id){
        if (id == 1){
            submit.setVisibility ( View.VISIBLE );
            retry.setVisibility ( View.INVISIBLE );
            next.setVisibility ( View.INVISIBLE );
        }
        else if (id == 2){
            submit.setVisibility ( View.INVISIBLE );
            retry.setVisibility ( View.VISIBLE );
            next.setVisibility ( View.INVISIBLE );
        }
        else if (id == 3) {
            submit.setVisibility ( View.INVISIBLE );
            retry.setVisibility ( View.INVISIBLE );
            next.setVisibility ( View.VISIBLE );
        }
    }

    private void recycler(){
        index = (int) ( Math.random () * 9 );
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
    private void down(){
        try {

            View view = (AdditionActivity.this).getWindow().getCurrentFocus();

            if (view != null && view.getWindowToken() != null) {

                IBinder binder = view.getWindowToken();

                InputMethodManager imm = (InputMethodManager) (AdditionActivity.this).getSystemService( Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(binder, 0);

            }

        } catch (NullPointerException e) {

            e.printStackTrace();

        }
    }

    @Override
    protected void onResume() {
        full ();
        super.onResume ();
    }

    @Override
    protected void onPause() {
        super.onPause ();
        overridePendingTransition ( android.R.anim.fade_in,android.R.anim.fade_out );
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
