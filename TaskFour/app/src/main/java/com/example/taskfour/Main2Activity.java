package com.example.taskfour;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

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
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Main2Activity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private Button buttonOne,buttonTwo,buttonThree,buttonFour,buttonFive,buttonSix,buttonSeven,buttonEight,logOut;
    private TextView label;
    public static MediaPlayer mediaPlayer;
    private MediaPlayer mediaPlayer2;
    private CardView c1,c2,c3,c4,c5,c6,c7,c8;
    private static int clicked = 0;
    private FirebaseAuth mAuth;

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
        setContentView ( R.layout.activity_main2 );

        mAuth = FirebaseAuth.getInstance ();

        mediaPlayer = new MediaPlayer ().create ( getApplicationContext (),R.raw.bgm);
        mediaPlayer.start ();
        mediaPlayer.setLooping ( true );
        mediaPlayer.setVolume ( 0.4f,0.4f );
        mediaPlayer2 = new MediaPlayer ().create(getApplicationContext (),R.raw.rubberone);

        Toolbar toolbar = findViewById ( R.id.toolbar );
        setSupportActionBar ( toolbar );
        drawerLayout = findViewById ( R.id.drawer_layout );
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle ( this,drawerLayout,toolbar,R.string.open,R.string.close );
        drawerLayout.addDrawerListener ( toggle );
        toggle.syncState ();

        logOut = findViewById ( R.id.log_out );

        buttonOne = findViewById ( R.id.but_one );
        buttonTwo = findViewById ( R.id.but_two );
        buttonThree = findViewById ( R.id.but_three );
        buttonFour = findViewById ( R.id.but_four );
        buttonFive = findViewById ( R.id.but_five );
        buttonSix = findViewById ( R.id.but_six );
        buttonSeven = findViewById ( R.id.but_seven );
        buttonEight = findViewById ( R.id.but_eight );
        c1 = findViewById ( R.id.card1 );
        c2 = findViewById ( R.id.card2 );
        c3 = findViewById ( R.id.card3 );
        c4 = findViewById ( R.id.card4 );
        c5 = findViewById ( R.id.card5 );
        c6 = findViewById ( R.id.card6 );
        c7 = findViewById ( R.id.card7 );
        c8 = findViewById ( R.id.card8 );

        label = findViewById ( R.id.label );
        label.setText ( "Alphabets         " );

        View.OnClickListener onClickListener = new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                mediaPlayer2.start ();
                Fragment fragment = null;
                switch (v.getId ()){
                    case R.id.but_one:
                        clicked = 0;
                        c1.startAnimation ( AnimationUtils.loadAnimation ( getApplicationContext (),R.anim.button ) );
                        fragment = new AlphabetsFragments ();
                        label.setText ( "Alphabets       " );
                        break;
                    case R.id.but_two:
                        clicked = 0;
                        c2.startAnimation ( AnimationUtils.loadAnimation ( getApplicationContext (),R.anim.button ) );
                        fragment = new NumberFragments ();
                        label.setText ( "Numbers      " );
                        break;
                    case R.id.but_three:
                        clicked = 1;
                        c3.startAnimation ( AnimationUtils.loadAnimation ( getApplicationContext (),R.anim.button ) );
                        fragment = new PaintingFragments ();
                        label.setText ( "Drawings      " );
                        break;
                    case R.id.but_four:
                        clicked = 0;
                        c4.startAnimation ( AnimationUtils.loadAnimation ( getApplicationContext (),R.anim.button ) );
                        fragment = new GameFragment ();
                        label.setText ( "Games      " );
                        break;
                    case R.id.but_five:
                        clicked = 0;
                        c5.startAnimation ( AnimationUtils.loadAnimation ( getApplicationContext (),R.anim.button ) );
                        fragment = new TutorialFragment ();
                        label.setText ( "Tutorial       " );
                        break;
                    case R.id.but_six:
                        clicked = 0;
                        c6.startAnimation ( AnimationUtils.loadAnimation ( getApplicationContext (),R.anim.button ) );
                        fragment = new EbookFragment ();
                        label.setText ( "E-Books      " );
                        break;
                    case R.id.but_seven:
                        clicked = 0;
                        c7.startAnimation ( AnimationUtils.loadAnimation ( getApplicationContext (),R.anim.button ) );
                        fragment = new TestFragment ();
                        label.setText ( "Test      " );
                        break;
                    case R.id.but_eight:
                        clicked = 0;
                        c8.startAnimation ( AnimationUtils.loadAnimation ( getApplicationContext (),R.anim.button ) );
                        fragment = new ChatFragment ();
                        label.setText ( "Chat      " );
                        break;
                }

                assert fragment != null;
                getSupportFragmentManager ().beginTransaction ().replace ( R.id.fragment_container,fragment ).commit ();
                drawerLayout.closeDrawer ( GravityCompat.START );
            }
        };

        if(savedInstanceState == null && clicked == 0){
            getSupportFragmentManager ().beginTransaction ().replace ( R.id.fragment_container,new AlphabetsFragments () ).commit ();
            label.setText ( "Alphabets       " );
        }
        else if (clicked == 1){
            getSupportFragmentManager ().beginTransaction ().replace ( R.id.fragment_container,new PaintingFragments () ).commit ();
            label.setText ( "Drawings      " );
        }

        buttonOne.setOnClickListener ( onClickListener );
        buttonTwo.setOnClickListener ( onClickListener );
        buttonThree.setOnClickListener ( onClickListener );
        buttonFour.setOnClickListener ( onClickListener );
        buttonFive.setOnClickListener ( onClickListener );
        buttonSix.setOnClickListener ( onClickListener );
        buttonSeven.setOnClickListener ( onClickListener );
        buttonEight.setOnClickListener ( onClickListener );

        logOut.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                mediaPlayer2.start ();
                mAuth.signOut ();
                Intent intent = new Intent ( getApplicationContext (),SignInActivity.class );
                intent.setFlags ( Intent.FLAG_ACTIVITY_CLEAR_TOP );
                startActivity ( intent );
                finish ();
            }
        } );
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen ( GravityCompat.START )){
            drawerLayout.closeDrawer ( GravityCompat.START );
        }
        else {
            exit ();
        }
    }

    private void exit(){
        final AlertDialog.Builder builder = new AlertDialog.Builder ( Main2Activity.this,R.style.CustomDialog );
        View view = LayoutInflater.from ( Main2Activity.this ).inflate ( R.layout.alphabet_write_dialog,null,false );
        Button yes = view.findViewById ( R.id.yes );
        Button no = view.findViewById ( R.id.no );
        TextView firstText = view.findViewById ( R.id.firstText );
        TextView secondText = view.findViewById ( R.id.secontText );
        ImageView dialogImage = view.findViewById ( R.id.dialog_image );

        firstText.setText ( "Oh noooo !" );
        secondText.setText ( "Wanna move out?" );
        dialogImage.setImageResource ( R.drawable.oh );
        final AlertDialog alertDialog = builder.create ();
        alertDialog.setView ( view );

        no.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                full ();
                alertDialog.cancel ();
            }
        } );

        yes.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                finish ();
            }
        } );
        alertDialog.setCanceledOnTouchOutside ( false );
        alertDialog.show ();
    }

    @Override
    protected void onPause() {
        super.onPause ();
        mediaPlayer.pause ();
        overridePendingTransition ( android.R.anim.fade_in,android.R.anim.fade_out );
    }

    @Override
    protected void onResume() {
        super.onResume ();
        mediaPlayer.start ();
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
