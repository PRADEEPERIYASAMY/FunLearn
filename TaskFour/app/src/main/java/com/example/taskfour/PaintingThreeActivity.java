package com.example.taskfour;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.taskfour.Adapters.PatternAdapter;
import com.example.taskfour.Adapters.PatternWorkAdapter;
import com.example.taskfour.Pattern.Patterns;

import java.util.ArrayList;
import java.util.List;

public class PaintingThreeActivity extends AppCompatActivity {

    private RecyclerView original,work;
    private Button c1,c2,c3,c4,c5,c6,c7,c8,c9,c10,home,reset;
    private CardView ca1,ca2,ca3,ca4,ca5,ca6,ca7,ca8,ca9,ca10;
    private List<Button> buttonList = new ArrayList<> (  );
    public static int selectedColor = Patterns.white;
    public static int id ;
    private MediaPlayer mediaPlayer,mediaPlayer1,mediaPlayer2;
    private CardView caa1,caa2;

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
        setContentView ( R.layout.activity_painting_three );

        id = getIntent ().getIntExtra ( "id",0 );
        home = findViewById ( R.id.home );
        reset = findViewById ( R.id.reset );

        mediaPlayer = new MediaPlayer ().create(getApplicationContext (),R.raw.bubble);
        mediaPlayer1 = new MediaPlayer ().create(getApplicationContext (),R.raw.rubberone);
        mediaPlayer2 = new MediaPlayer ().create(getApplicationContext (),R.raw.negative);

        original = findViewById ( R.id.pattern_original );
        original.setHasFixedSize ( true );
        original.setLayoutManager ( new GridLayoutManager ( getApplicationContext (),5 ) );
        original.setAdapter ( new PatternAdapter ( getApplicationContext (), Patterns.pattern[id] ) );

        work = findViewById ( R.id.pattern_work );
        work.setHasFixedSize ( true );
        work.setLayoutManager ( new GridLayoutManager ( getApplicationContext (),5 ) );
        work.setAdapter ( new PatternWorkAdapter (PaintingThreeActivity.this) );

        buttonList.clear ();

        c1 = findViewById ( R.id.col1 );
        buttonList.add ( c1 );
        c2 = findViewById ( R.id.col2 );
        buttonList.add ( c2 );
        c3 = findViewById ( R.id.col3 );
        buttonList.add ( c3 );
        c4 = findViewById ( R.id.col4 );
        buttonList.add ( c4 );
        c5 = findViewById ( R.id.col5 );
        buttonList.add ( c5 );
        c6 = findViewById ( R.id.col6 );
        buttonList.add ( c6 );
        c7 = findViewById ( R.id.col7 );
        buttonList.add ( c7 );
        c8 = findViewById ( R.id.col8 );
        buttonList.add ( c8 );
        c9 = findViewById ( R.id.col9 );
        buttonList.add ( c9 );
        c10 = findViewById ( R.id.col10 );
        buttonList.add ( c10 );

        ca1 = findViewById ( R.id.c1 );
        ca2 = findViewById ( R.id.c2 );
        ca3 = findViewById ( R.id.c3 );
        ca4 = findViewById ( R.id.c4 );
        ca5 = findViewById ( R.id.c5 );
        ca6 = findViewById ( R.id.c6 );
        ca7 = findViewById ( R.id.c7 );
        ca8 = findViewById ( R.id.c8 );
        ca9 = findViewById ( R.id.c9 );
        ca10 = findViewById ( R.id.c10 );

        caa1 = findViewById ( R.id.caa1 );
        caa2 = findViewById ( R.id.caa2 );

        View.OnClickListener onClickListener = new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                mediaPlayer.start ();
                switch (v.getId ()){
                    case R.id.col1:
                        ca1.startAnimation ( AnimationUtils.loadAnimation ( getApplicationContext (),R.anim.click ) );
                        selectedColor = Patterns.white;
                        break;
                    case R.id.col2:
                        ca2.startAnimation ( AnimationUtils.loadAnimation ( getApplicationContext (),R.anim.click ) );
                        selectedColor = Patterns.orange;
                        break;
                    case R.id.col3:
                        ca3.startAnimation ( AnimationUtils.loadAnimation ( getApplicationContext (),R.anim.click ) );
                        selectedColor = Patterns.pink;
                        break;
                    case R.id.col4:
                        ca4.startAnimation ( AnimationUtils.loadAnimation ( getApplicationContext (),R.anim.click ) );
                        selectedColor = Patterns.lightBlue;
                        break;
                    case R.id.col5:
                        ca5.startAnimation ( AnimationUtils.loadAnimation ( getApplicationContext (),R.anim.click ) );
                        selectedColor = Patterns.brown;
                        break;
                    case R.id.col6:
                        ca6.startAnimation ( AnimationUtils.loadAnimation ( getApplicationContext (),R.anim.click ) );
                        selectedColor = Patterns.yellow;
                        break;
                    case R.id.col7:
                        ca7.startAnimation ( AnimationUtils.loadAnimation ( getApplicationContext (),R.anim.click ) );
                        selectedColor = Patterns.red;
                        break;
                    case R.id.col8:
                        ca8.startAnimation ( AnimationUtils.loadAnimation ( getApplicationContext (),R.anim.click ) );
                        selectedColor = Patterns.darkBlue;
                        break;
                    case R.id.col9:
                        ca9.startAnimation ( AnimationUtils.loadAnimation ( getApplicationContext (),R.anim.click ) );
                        selectedColor = Patterns.green;
                        break;
                    case R.id.col10:
                        ca10.startAnimation ( AnimationUtils.loadAnimation ( getApplicationContext (),R.anim.click ) );
                        selectedColor = Patterns.black;
                        break;
                }

            }
        };

        for (Button button : buttonList){
            button.setOnClickListener ( onClickListener );
        }

        home.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                mediaPlayer1.start ();
                caa1.startAnimation ( AnimationUtils.loadAnimation ( getApplicationContext (),R.anim.button ) );
                exit ();
            }
        } );
        reset.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                mediaPlayer1.start ();
                caa2.startAnimation ( AnimationUtils.loadAnimation ( getApplicationContext (),R.anim.button ) );
                finish ();
                Intent intent = new Intent ( getApplicationContext (),PaintingThreeActivity.class );
                intent.putExtra ( "id",id );
                startActivity ( intent );
            }
        } );

    }

    @Override
    public void onBackPressed() {
        exit ();
    }

    private void exit(){
        mediaPlayer2.start ();
        final AlertDialog.Builder builder = new AlertDialog.Builder ( PaintingThreeActivity.this,R.style.CustomDialog );
        View view = LayoutInflater.from ( PaintingThreeActivity.this ).inflate ( R.layout.alphabet_write_dialog,null,false );
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
                full ();
                alertDialog.cancel ();
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
}
