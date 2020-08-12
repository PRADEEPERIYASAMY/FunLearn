package com.example.taskfour;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taskfour.Database.Level;
import com.example.taskfour.GameOnePlay;
import com.example.taskfour.R;

import java.util.ArrayList;
import java.util.List;

public class GameOneActivity extends AppCompatActivity {

    private Button button1,button2,button3,button4,button5,button6;
    private ImageView s11,s12,s13,s21,s22,s23,s31,s32,s33,s41,s42,s43,s51,s52,s53,s61,s62,s63,l1,l2,l3,l4,l5,l6;
    private List<ImageView> p1 = new ArrayList<> (  );
    private List<ImageView> p2 = new ArrayList<> (  );
    private List<ImageView> p3 = new ArrayList<> (  );
    private List<ImageView> p4 = new ArrayList<> (  );
    private List<ImageView> p5 = new ArrayList<> (  );
    private List<ImageView> p6 = new ArrayList<> (  );
    private List<String > values = new ArrayList<> (  );
    private TextView t1,t2,t3,t4,t5,t6;
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
        setContentView ( R.layout.activity_game_one );

        Main2Activity.mediaPlayer.start ();
        mediaPlayer = new MediaPlayer ().create(getApplicationContext (),R.raw.rubberone);
        mediaPlayer1 = new MediaPlayer ().create(getApplicationContext (),R.raw.negative);

        values.clear ();
        p1.clear ();
        p2.clear ();
        p3.clear ();
        p4.clear ();
        p5.clear ();
        p6.clear ();

        button1 = findViewById ( R.id.g1b1 );
        button2 = findViewById ( R.id.g1b2 );
        button3 = findViewById ( R.id.g1b3 );
        button4 = findViewById ( R.id.g1b4 );
        button5 = findViewById ( R.id.g1b5 );
        button6 = findViewById ( R.id.g1b6 );

        imageview ();

        final Level level = new Level ( getApplicationContext () );
        final Cursor cursor = level.FetchData ();

        for (int i = 0; i < 6; i++) {
            cursor.moveToPosition ( i );
            values.add ( cursor.getString ( 1 ) );
        }

        starSetter(p1,Integer.parseInt ( values.get ( 0 )) );
        starSetter(p2,Integer.parseInt ( values.get ( 1 )) );
        starSetter(p3,Integer.parseInt ( values.get ( 2 )) );
        starSetter(p4,Integer.parseInt ( values.get ( 3 )) );
        starSetter(p5,Integer.parseInt ( values.get ( 4 )) );
        starSetter(p6,Integer.parseInt ( values.get ( 5 )) );

        locksetter(values);
        final Animation animation = AnimationUtils.loadAnimation ( getApplicationContext (),R.anim.button );

        View.OnClickListener onClickListener = new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                mediaPlayer.start ();
                Intent intent = new Intent ( getApplicationContext (), GameOnePlay.class );
                int id = 0;
                int drawable = 0;
                v.startAnimation ( animation );
                switch (v.getId ()){
                    case R.id.g1b1:
                        id = 1;
                        drawable = R.drawable.landone;
                        break;
                    case R.id.g1b2:
                        id = 2;
                        drawable = R.drawable.landtwo;
                        break;
                    case R.id.g1b3:
                        id = 3;
                        drawable = R.drawable.landthree;
                        break;
                    case R.id.g1b4:
                        id = 4;
                        drawable = R.drawable.landfour;
                        break;
                    case R.id.g1b5:
                        id = 5;
                        drawable = R.drawable.landfive;
                        break;
                    case R.id.g1b6:
                        id = 6;
                        drawable = R.drawable.landsix;
                        break;
                }

                if ( id - 1 != 0 && Integer.parseInt ( values.get ( id -2 ) ) < 2){
                    final AlertDialog.Builder builder = new AlertDialog.Builder ( GameOneActivity.this,R.style.CustomDialog );
                    View view = LayoutInflater.from ( GameOneActivity.this ).inflate ( R.layout.info_dialog,null,false );
                    Button yes = view.findViewById ( R.id.yes );
                    final AlertDialog alertDialog = builder.create ();
                    alertDialog.setView ( view );

                    yes.setOnClickListener ( new View.OnClickListener () {
                        @Override
                        public void onClick( View v ) {
                            alertDialog.dismiss ();
                        }
                    } );

                    alertDialog.setCanceledOnTouchOutside ( false );
                    alertDialog.show ();
                }
                else {
                    intent.putExtra ( "id",id );
                    intent.putExtra ( "drawable",drawable );
                    startActivity ( intent );
                    finish ();
                }
            }
        };

        button1.setOnClickListener ( onClickListener );
        button2.setOnClickListener ( onClickListener );
        button3.setOnClickListener ( onClickListener );
        button4.setOnClickListener ( onClickListener );
        button5.setOnClickListener ( onClickListener );
        button6.setOnClickListener ( onClickListener );

        final Button info = findViewById ( R.id.info );
        info.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                mediaPlayer.start ();
                final AlertDialog.Builder builder = new AlertDialog.Builder ( GameOneActivity.this,R.style.CustomDialog );
                View view = LayoutInflater.from ( GameOneActivity.this ).inflate ( R.layout.about_dialog,null,false );

                Button close = view.findViewById ( R.id.info_close );
                TextView infoText = view.findViewById ( R.id.info_text );

                infoText.setText ( "Users are allowed to choose the if they have minimum two stars in the previous levels except level one." );

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

    private void locksetter( List<String> values ) {
        lockassign(l1,t1,1);
        lockassign(l2,t2,2);
        lockassign(l3,t3,3);
        lockassign(l4,t4,4);
        lockassign(l5,t5,5);
        lockassign(l6,t6,6);
    }

    private void lockassign( ImageView l ,TextView t,int i ) {

        if (i - 1 != 0 && Integer.parseInt ( values.get ( i -2 ) ) < 2){
            l.setImageResource ( R.drawable.lock );
            t.setVisibility ( View.INVISIBLE );
        }
        else {
            l.setImageResource ( R.drawable.trophy );
            t.setVisibility ( View.VISIBLE );
        }
    }


    private void starSetter( List<ImageView> p , int star ) {
        if (star == 1){
            p.get ( 0 ).setImageResource ( R.drawable.star );
            p.get ( 1 ).setImageResource ( R.drawable.starwhite );
            p.get ( 2 ).setImageResource ( R.drawable.starwhite );
        }
        else if (star == 2){
            p.get ( 0 ).setImageResource ( R.drawable.star );
            p.get ( 1 ).setImageResource ( R.drawable.star );
            p.get ( 2 ).setImageResource ( R.drawable.starwhite );
        }
        else if (star == 3){
            p.get ( 0 ).setImageResource ( R.drawable.star );
            p.get ( 1 ).setImageResource ( R.drawable.star );
            p.get ( 2 ).setImageResource ( R.drawable.star );
        }
        else {
            p.get ( 0 ).setImageResource ( R.drawable.starwhite );
            p.get ( 1 ).setImageResource ( R.drawable.starwhite );
            p.get ( 2 ).setImageResource ( R.drawable.starwhite );
        }
    }

    private void imageview(){
        s11 = findViewById ( R.id.s11 );
        s12 = findViewById ( R.id.s12 );
        s13 = findViewById ( R.id.s13 );
        p1.add ( s11 );
        p1.add ( s12 );
        p1.add ( s13 );

        s21 = findViewById ( R.id.s21 );
        s22 = findViewById ( R.id.s22 );
        s23 = findViewById ( R.id.s23 );
        p2.add ( s21 );
        p2.add ( s22 );
        p2.add ( s23 );

        s31 = findViewById ( R.id.s31 );
        s32 = findViewById ( R.id.s32 );
        s33 = findViewById ( R.id.s33 );
        p3.add ( s31 );
        p3.add ( s32 );
        p3.add ( s33 );

        s41 = findViewById ( R.id.s41 );
        s42 = findViewById ( R.id.s42 );
        s43 = findViewById ( R.id.s43 );
        p4.add ( s41 );
        p4.add ( s42 );
        p4.add ( s43 );

        s51 = findViewById ( R.id.s51 );
        s52 = findViewById ( R.id.s52 );
        s53 = findViewById ( R.id.s53 );
        p5.add ( s51 );
        p5.add ( s52 );
        p5.add ( s53 );

        s61 = findViewById ( R.id.s61 );
        s62 = findViewById ( R.id.s62 );
        s63 = findViewById ( R.id.s63 );
        p6.add ( s61 );
        p6.add ( s62 );
        p6.add ( s63 );

        l1 = findViewById ( R.id.l1 );
        l2 = findViewById ( R.id.l2 );
        l3 = findViewById ( R.id.l3 );
        l4 = findViewById ( R.id.l4 );
        l5 = findViewById ( R.id.l5 );
        l6 = findViewById ( R.id.l6 );

        t1 = findViewById ( R.id.t1 );
        t2 = findViewById ( R.id.t2 );
        t3 = findViewById ( R.id.t3 );
        t4 = findViewById ( R.id.t4 );
        t5 = findViewById ( R.id.t5 );
        t6 = findViewById ( R.id.t6 );

        Button back = findViewById ( R.id.back );
        back.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                mediaPlayer1.start ();
                finish ();
            }
        } );

    }
    @Override
    protected void onPause() {
        super.onPause ();
        overridePendingTransition ( android.R.anim.fade_in,android.R.anim.fade_out );
        Main2Activity.mediaPlayer.pause ();
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
