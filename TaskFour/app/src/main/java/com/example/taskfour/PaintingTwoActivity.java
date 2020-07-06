package com.example.taskfour;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taskfour.Pattern.Patterns;
import com.example.taskfour.Widget.ColorView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PaintingTwoActivity extends AppCompatActivity {

    private ColorView colorView;
    public static int imageid = 0;
    private static final int REQUEST_PERMISSION = 1001;
    private static final int PERMISSION_REQUEST_CODE = 1000;
    private static final int REQUEST_GALARY = 1002;
    private Button c1,c2,c3,c4,c5,c6,c7,c8,c9,c10,undo,file,download,share,back,home;
    private CardView ca1,ca2,ca3,ca4,ca5,ca6,ca7,ca8,ca9,ca10;
    private CardView caa1,caa2,caa3,caa4,caa5,caa6;
    private List<Button> buttonList = new ArrayList<> (  );
    public static int selectedColor = Patterns.white;
    public static Uri fileId = null;
    private MediaPlayer mediaPlayer,mediaPlayer1,mediaPlayer2;
    public static MediaPlayer mediaPlayer3;

    @RequiresApi(api = Build.VERSION_CODES.M)
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
        setContentView ( R.layout.activity_painting_two );

        imageid = getIntent ().getIntExtra ( "imageid",0 );
        final Intent intent = getIntent ();
        if (intent != null){
            fileId = intent.getData ();
        }

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        if (ActivityCompat.checkSelfPermission ( getApplicationContext (), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ){
            requestPermissions ( new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},PERMISSION_REQUEST_CODE );
        }

        mediaPlayer3 = new MediaPlayer ().create(getApplicationContext (),R.raw.colorfill);
        colorView = findViewById ( R.id.color_view );

        mediaPlayer = new MediaPlayer ().create(getApplicationContext (),R.raw.rubberone);
        mediaPlayer1 = new MediaPlayer ().create(getApplicationContext (),R.raw.negative);
        mediaPlayer2 = new MediaPlayer ().create(getApplicationContext (),R.raw.bubble);

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

        undo = findViewById ( R.id.undo );
        file = findViewById ( R.id.showFile );
        download = findViewById ( R.id.download );
        share = findViewById ( R.id.share );
        home = findViewById ( R.id.home );
        back = findViewById ( R.id.back );

        caa1 = findViewById ( R.id.ca1 );
        caa2 = findViewById ( R.id.ca2 );
        caa3 = findViewById ( R.id.ca3 );
        caa4 = findViewById ( R.id.ca4 );
        caa5 = findViewById ( R.id.ca5 );
        caa6 = findViewById ( R.id.ca6 );

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

        View.OnClickListener onClickListener = new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                mediaPlayer2.start ();
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

        undo.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                mediaPlayer.start ();
                caa6.startAnimation ( AnimationUtils.loadAnimation ( getApplicationContext (),R.anim.button ) );
                colorView.returnLastAction ();
            }
        } );

        file.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                mediaPlayer.start ();
                caa5.startAnimation ( AnimationUtils.loadAnimation ( getApplicationContext (),R.anim.button ) );
                Intent intent = new Intent ( getApplicationContext (),ListColoredActivity.class );
                startActivity ( intent );
            }
        } );

        download.setOnClickListener ( new View.OnClickListener () {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick( View v ) {
                mediaPlayer.start ();
                caa2.startAnimation ( AnimationUtils.loadAnimation ( getApplicationContext (),R.anim.button ) );
                save ( );
            }
        } );

        share.setOnClickListener ( new View.OnClickListener () {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick( View v ) {
                mediaPlayer.start ();
                caa3.startAnimation ( AnimationUtils.loadAnimation ( getApplicationContext (),R.anim.button ) );
                if (ActivityCompat.checkSelfPermission ( getApplicationContext (), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ){
                    Toast.makeText ( getApplicationContext (),"you should grant permission",Toast.LENGTH_LONG ).show ();
                    requestPermissions ( new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},PERMISSION_REQUEST_CODE );
                    return;
                }
                else {
                    Intent intent = new Intent( Intent.ACTION_SEND);
                    intent.setType("*/*");
                    intent.putExtra( Intent.EXTRA_STREAM, getlocalBitmapUri(colorView.getBitmap ()));
                    intent.putExtra ( Intent.EXTRA_TEXT,"My Paint Work" );
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(Intent.createChooser(intent, "Share"));
                }
            }
        } );

        home.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                mediaPlayer.start ();
                caa1.startAnimation ( AnimationUtils.loadAnimation ( getApplicationContext (),R.anim.button ) );
                exit ();
            }
        } );

        back.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                mediaPlayer.start ();
                caa4.startAnimation ( AnimationUtils.loadAnimation ( getApplicationContext (),R.anim.button ) );
                finish ();
                Intent intent1 = new Intent ( getApplicationContext (),PaintingTwoActivity.class );
                intent.putExtra ( "imageid",imageid );
                startActivity ( intent );
            }
        } );

    }

    @Override
    public void onBackPressed() {
        exit ();
    }

    private void exit(){
        mediaPlayer1.start ();
        final AlertDialog.Builder builder = new AlertDialog.Builder ( PaintingTwoActivity.this,R.style.CustomDialog );
        View view = LayoutInflater.from ( PaintingTwoActivity.this ).inflate ( R.layout.alphabet_write_dialog,null,false );
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
                mediaPlayer.start ();
                full ();
                alertDialog.cancel ();
            }
        } );

        yes.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                mediaPlayer.start ();
                finish ();
                Intent intent = new Intent ( getApplicationContext (),ColouringListActivity.class );
                startActivity ( intent );
            }
        } );
        alertDialog.setCanceledOnTouchOutside ( false );
        alertDialog.show ();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void save( ) {
        if (ActivityCompat.checkSelfPermission ( getApplicationContext (), Manifest.permission.WRITE_EXTERNAL_STORAGE ) != PackageManager.PERMISSION_DENIED){
            requestPermissions ( new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_PERMISSION );
        }
        else {
            saveBitmap();
        }
    }

    private void saveBitmap() {
        Bitmap bitmap = colorView.getBitmap ();
        String fileName = UUID.randomUUID ()+".png";

        File folder = new File ( getExternalFilesDir ( Environment.DIRECTORY_PICTURES )+File.separator+"Coloured");

        if (!folder.exists ()){
            folder.mkdir ();
        }

        try {
            FileOutputStream fileOutputStream = new FileOutputStream ( folder+File.separator+fileName );
            bitmap.compress ( Bitmap.CompressFormat.PNG,100,fileOutputStream );
            Toast.makeText ( this,"Picture Saved",Toast.LENGTH_SHORT ).show ();
        } catch (FileNotFoundException e) {
            e.printStackTrace ();
        }

    }

    @Override
    public void onRequestPermissionsResult( int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults ) {

        super.onRequestPermissionsResult ( requestCode, permissions, grantResults );

        switch (requestCode) {
            case PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText ( getApplicationContext () , "Permission Granted" , Toast.LENGTH_LONG ).show ();
                } else {
                    Toast.makeText ( getApplicationContext () , "Permission Denied" , Toast.LENGTH_LONG ).show ();
                }
                break;
            }
            case REQUEST_PERMISSION:
                saveBitmap ();
                break;
            case REQUEST_GALARY:
                getImage ();
        }
    }

    private void getImage() {

    }

    private Uri getlocalBitmapUri( Bitmap bitmap) {
        Uri bmuri = null;
        try {
            File file = new File(Environment.getExternalStorageDirectory() + File.separator + "image.png");
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress( Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.close();
            bmuri = Uri.fromFile(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        return bmuri;
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
