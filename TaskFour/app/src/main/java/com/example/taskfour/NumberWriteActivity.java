package com.example.taskfour;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taskfour.Adapters.WriteAdapter;
import com.example.taskfour.Numbers.NumberConverter;
import com.example.taskfour.Widget.PaintView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextDetector;

import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class NumberWriteActivity extends AppCompatActivity {

    private PaintView paintView;
    private TextView task,resultName;
    private Button submit,clear,next;
    private List<String> numbers = new ArrayList<> (  );
    private GifImageView gifImageView;
    private ImageView result;
    private boolean block = false;
    private static int type = 0;

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
    private LinearLayout container;
    private MediaPlayer mediaPlayer,mediaPlayer1,mediaPlayer2,mediaPlayer3;
    private CardView c1,c2,c3;

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
        setContentView ( R.layout.activity_number_write );

        mediaPlayer = new MediaPlayer ().create(getApplicationContext (),R.raw.negative);
        mediaPlayer1 = new MediaPlayer ().create(getApplicationContext (),R.raw.rubberone);
        mediaPlayer2 = new MediaPlayer ().create(getApplicationContext (),R.raw.sucess);
        mediaPlayer3 = new MediaPlayer ().create(getApplicationContext (),R.raw.explosion);

        paintView = findViewById ( R.id.paint_view );
        paintView.setColorBackground ( Color.rgb ( 176,222,243) );
        paintView.setSizeBrush ( 4 );
        paintView.setBrushColor ( Color.RED );
        gifImageView = findViewById ( R.id.gif );
        resultName = findViewById ( R.id.result_name );
        container = findViewById ( R.id.container );
        result = findViewById ( R.id.result_image );

        container.setVisibility ( View.INVISIBLE );
        gifImageView.setVisibility ( View.INVISIBLE );
        recyclerViewList = new ArrayList<> (  );

        c1 = findViewById ( R.id.c1 );
        c2 = findViewById ( R.id.c2 );
        c3 = findViewById ( R.id.c3 );

        task = findViewById ( R.id.task );
        submit = findViewById ( R.id.submit );
        next = findViewById ( R.id.next );
        clear = findViewById ( R.id.clear );
        next.setEnabled ( false );
        numbers.clear ();
        for (int i = 1; i <= 100; i++) {
            numbers.add ( String.valueOf ( i ) );
        }

        task.setText ( numbers.get ( 0 ) );
        numbers.remove ( 0 );

        View.OnClickListener onClickListener = new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                mediaPlayer1.start ();
                switch (v.getId ()){
                    case R.id.submit:
                        c1.startAnimation ( AnimationUtils.loadAnimation ( getApplicationContext (),R.anim.button ) );
                        result.setVisibility ( View.INVISIBLE );
                        detect ();
                        break;
                    case R.id.next:
                        c2.startAnimation ( AnimationUtils.loadAnimation ( getApplicationContext (),R.anim.button ) );
                        result.setVisibility ( View.VISIBLE );
                        submit.setEnabled ( true );
                        container.setVisibility ( View.INVISIBLE );
                        if (numbers.size ()>0){
                            next.setEnabled ( false );
                            task.setText ( numbers.get ( 0 ) );
                            numbers.remove ( 0 );
                            paintView.clear ( paintView.getBitmap () );
                            paintView.setColorBackground ( Color.rgb ( 176,222,243) );
                            gifImageView.setVisibility ( View.INVISIBLE );
                            resultName.setText ( "Result" );
                        }
                        if (numbers.size () == 0 && !block)
                        {
                            block = true;
                        }
                        else if (numbers.size () == 0 && block){
                            AlertDialog.Builder builder = new AlertDialog.Builder ( NumberWriteActivity.this,R.style.CustomDialog);
                            View view = LayoutInflater.from ( NumberWriteActivity.this ).inflate ( R.layout.alphabet_write_dialog,null,false );
                            Button yes = view.findViewById ( R.id.yes );
                            Button no = view.findViewById ( R.id.no );
                            View.OnClickListener onClickListener1 = new View.OnClickListener () {
                                @Override
                                public void onClick( View v ) {
                                    switch (v.getId ()){
                                        case R.id.yes:
                                            finish ();
                                            startActivity ( new Intent ( getApplicationContext (),NumberWriteActivity.class ) );
                                            break;
                                        case R.id.no:
                                            finish ();
                                            break;
                                    }
                                }
                            };
                            yes.setOnClickListener ( onClickListener1 );
                            no.setOnClickListener ( onClickListener1 );
                            builder.setView ( view ).show ();
                        }
                        break;
                    case R.id.clear:
                        c3.startAnimation ( AnimationUtils.loadAnimation ( getApplicationContext (),R.anim.button ) );
                        paintView.clear ( paintView.getBitmap () );
                        paintView.setColorBackground ( Color.rgb ( 176,222,243) );
                        break;
                }
            }
        };

        submit.setOnClickListener ( onClickListener );
        next.setOnClickListener ( onClickListener );
        clear.setOnClickListener ( onClickListener );
        Button back = findViewById ( R.id.back );
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
                mediaPlayer1.start ();
                final AlertDialog.Builder builder = new AlertDialog.Builder ( NumberWriteActivity.this,R.style.CustomDialog );
                View view = LayoutInflater.from ( NumberWriteActivity.this ).inflate ( R.layout.about_dialog,null,false );

                Button close = view.findViewById ( R.id.info_close );
                TextView infoText = view.findViewById ( R.id.info_text );

                infoText.setText ( "In this page writing space is provided for user to write the number given to copy. If user write the number properly positive result will be shown." );

                final AlertDialog alertDialog = builder.create ();
                alertDialog.setView ( view );

                close.setOnClickListener ( new View.OnClickListener () {
                    @Override
                    public void onClick( View v ) {
                        mediaPlayer.start ();
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
        final AlertDialog.Builder builder = new AlertDialog.Builder ( NumberWriteActivity.this,R.style.CustomDialog );
        View view = LayoutInflater.from ( NumberWriteActivity.this ).inflate ( R.layout.alphabet_write_dialog,null,false );
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

    private void detect() {
        FirebaseVisionImage firebaseVisionImage = FirebaseVisionImage.fromBitmap ( paintView.getBitmap () );
        FirebaseVisionTextDetector firebaseVisionTextDetector = FirebaseVision.getInstance ().getVisionTextDetector ();
        firebaseVisionTextDetector.detectInImage ( firebaseVisionImage ).addOnSuccessListener ( new OnSuccessListener<FirebaseVisionText> () {
            @Override
            public void onSuccess( FirebaseVisionText firebaseVisionText ) {
                List<FirebaseVisionText.Block> blockList = firebaseVisionText.getBlocks ();
                if (blockList.size () == 0) {
                    mediaPlayer3.start ();
                    container.setVisibility ( View.INVISIBLE );
                    gifImageView.setVisibility ( View.VISIBLE );
                    ((GifDrawable)gifImageView.getDrawable()).start ();
                    ((GifDrawable)gifImageView.getDrawable()).reset ();
                    resultName.setText ( "Wrong" );
                    new Handler (  ).postDelayed ( new Runnable () {
                        @Override
                        public void run() {
                            ((GifDrawable)gifImageView.getDrawable()).stop();
                        }
                    },((GifDrawable)gifImageView.getDrawable()).getDuration ()-300);
                } else {
                    for (FirebaseVisionText.Block block : firebaseVisionText.getBlocks ()) {
                        String string = block.getText ();
                        if (task.getText ().toString ().equals ( string )){
                            submit.setEnabled ( false );
                            mediaPlayer2.start ();
                            container.setVisibility ( View.VISIBLE );
                            setcounting ( Integer.parseInt ( task.getText ().toString () ) );
                            gifImageView.setVisibility ( View.INVISIBLE );
                            resultName.setText ( NumberConverter.numberNames[Integer.parseInt ( task.getText ().toString () )-1] );
                            next.setEnabled ( true );
                        }
                        else {
                            mediaPlayer3.start ();
                            container.setVisibility ( View.INVISIBLE );
                            gifImageView.setVisibility ( View.VISIBLE );
                            ((GifDrawable)gifImageView.getDrawable()).start ();
                            ((GifDrawable)gifImageView.getDrawable()).reset ();
                            resultName.setText ( "Wrong" );
                            new Handler (  ).postDelayed ( new Runnable () {
                                @Override
                                public void run() {
                                    ((GifDrawable)gifImageView.getDrawable()).stop();
                                }
                            },((GifDrawable)gifImageView.getDrawable()).getDuration ()-300);
                        }
                    }
                }
            }
        } ).addOnFailureListener ( new OnFailureListener () {
            @Override
            public void onFailure( @NonNull Exception e ) {
                View view = LayoutInflater.from ( NumberWriteActivity.this ).inflate ( R.layout.toast,null,false );
                TextView textView = view.findViewById ( R.id.toast_text );
                textView.setText ( "Detection Failed" );
                Toast toast = new Toast ( getApplicationContext () );
                toast.setView ( view );
                toast.show ();
            }
        } );
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
        gifImageView.setVisibility ( View.INVISIBLE );
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
