package com.example.taskfour;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taskfour.Widget.PaintView;
import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

public class PaintingOneActivity extends AppCompatActivity {

    private static final int REQUEST_PERMISSION = 1001;
    private static final int PERMISSION_REQUEST_CODE = 1000;
    private static final int REQUEST_GALARY = 1002;
    private PaintView paintView;
    private Button brush,eraser,imageFile,color,layer,undo,home,share,download,exit;
    private static int brushSize = 12,eraserSize = 12;
    private static int brushColor,backgroundColor;
    public static Uri file = null;
    private MediaPlayer mediaPlayer,mediaPlayer1;
    private CardView c1,c2,c3,c4,c5,c6,c7,c8,c9,c10;

    @RequiresApi(api = Build.VERSION_CODES.M)
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
        setContentView ( R.layout.activity_painting_one );
        mediaPlayer = new MediaPlayer ().create(getApplicationContext (),R.raw.negative);
        mediaPlayer1 = new MediaPlayer ().create(getApplicationContext (),R.raw.rubberone);

        Intent intent = getIntent ();
        if (intent != null){
            file = intent.getData ();
        }

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        if (ActivityCompat.checkSelfPermission ( getApplicationContext (), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ){
            requestPermissions ( new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},PERMISSION_REQUEST_CODE );
        }

        paintView = findViewById ( R.id.drawing );

        brush = findViewById ( R.id.brush );
        eraser = findViewById ( R.id.eraser );
        imageFile = findViewById ( R.id.image_file );
        color = findViewById ( R.id.color );
        layer = findViewById ( R.id.layer );
        undo = findViewById ( R.id.undo );
        home = findViewById ( R.id.home );
        share = findViewById ( R.id.share );
        download = findViewById ( R.id.download );
        exit = findViewById ( R.id.back );

        c1 = findViewById ( R.id.c1 );
        c2 = findViewById ( R.id.c2 );
        c3 = findViewById ( R.id.c3 );
        c4 = findViewById ( R.id.c4 );
        c5 = findViewById ( R.id.c5 );
        c6 = findViewById ( R.id.c6 );
        c7 = findViewById ( R.id.c7 );
        c8 = findViewById ( R.id.c8 );
        c9 = findViewById ( R.id.c9 );
        c10 = findViewById ( R.id.c10 );

        final Animation animation = AnimationUtils.loadAnimation ( getApplicationContext (),R.anim.button );

        download.setOnClickListener ( new View.OnClickListener () {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick( View v ) {
                c2.startAnimation ( animation );
                mediaPlayer1.start ();
                save ( );
            }
        } );

        brush.setOnClickListener ( new View.OnClickListener () {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick( View v ) {
                c5.startAnimation ( animation );
                mediaPlayer1.start ();
                paintView.disableEraser ();
                AlertDialog.Builder builder = new AlertDialog.Builder ( PaintingOneActivity.this);
                View view = LayoutInflater.from (PaintingOneActivity.this).inflate ( R.layout.dialog,null,false );
                TextView toolsSelected = view.findViewById ( R.id.status_tools_selected );
                final TextView statusSize = view.findViewById ( R.id.status_size );
                ImageView ivTools = view.findViewById ( R.id.iv_tools );
                SeekBar seekBar = view.findViewById ( R.id.seekbar_size );
                seekBar.setMax ( 99 );

                toolsSelected.setText ( "Brush Size" );
                ivTools.setImageResource ( R.drawable.paintbrush );
                statusSize.setText ( "Selected Size :"+String.valueOf ( brushSize ) );
                seekBar.setProgress ( brushSize,true );

                seekBar.setOnSeekBarChangeListener ( new SeekBar.OnSeekBarChangeListener () {
                    @Override
                    public void onProgressChanged( SeekBar seekBar, int progress, boolean fromUser ) {
                        brushSize = progress+1;
                        statusSize.setText ( "Selected Size :"+String.valueOf ( brushSize ) );
                        paintView.setSizeEraser ( brushSize);
                    }

                    @Override
                    public void onStartTrackingTouch( SeekBar seekBar ) {

                    }

                    @Override
                    public void onStopTrackingTouch( SeekBar seekBar ) {

                    }
                } );

                builder.setPositiveButton ( "Ok", new DialogInterface.OnClickListener () {
                    @Override
                    public void onClick( DialogInterface dialog, int which ) {
                        dialog.dismiss ();
                        full ();
                    }
                } );

                builder.setView ( view );
                builder.show ();
            }
        } );

        eraser.setOnClickListener ( new View.OnClickListener () {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick( View v ) {
                c6.startAnimation ( animation );
                mediaPlayer1.start ();
                paintView.enableEraser ();
                AlertDialog.Builder builder = new AlertDialog.Builder ( PaintingOneActivity.this);
                View view = LayoutInflater.from (PaintingOneActivity.this).inflate ( R.layout.dialog,null,false );
                TextView toolsSelected = view.findViewById ( R.id.status_tools_selected );
                final TextView statusSize = view.findViewById ( R.id.status_size );
                ImageView ivTools = view.findViewById ( R.id.iv_tools );
                SeekBar seekBar = view.findViewById ( R.id.seekbar_size );
                seekBar.setMax ( 99 );
                toolsSelected.setText ( "Eraser Size" );
                ivTools.setImageResource ( R.drawable.eraser );
                statusSize.setText ( "Selected Size :"+String.valueOf ( eraserSize ) );
                seekBar.setProgress ( eraserSize,true );

                seekBar.setOnSeekBarChangeListener ( new SeekBar.OnSeekBarChangeListener () {
                    @Override
                    public void onProgressChanged( SeekBar seekBar, int progress, boolean fromUser ) {
                        eraserSize = progress+1;
                        statusSize.setText ( "Selected Size :"+String.valueOf ( eraserSize ) );
                        paintView.setSizeEraser ( eraserSize );
                    }

                    @Override
                    public void onStartTrackingTouch( SeekBar seekBar ) {

                    }

                    @Override
                    public void onStopTrackingTouch( SeekBar seekBar ) {

                    }
                } );

                builder.setPositiveButton ( "Ok", new DialogInterface.OnClickListener () {
                    @Override
                    public void onClick( DialogInterface dialog, int which ) {
                        dialog.dismiss ();
                        full ();
                    }
                } );

                builder.setView ( view );
                builder.show ();
            }
        } );

        color.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                c8.startAnimation ( animation );
                mediaPlayer1.start ();
                int color = brushColor;
                ColorPickerDialogBuilder
                        .with ( PaintingOneActivity.this )
                        .setTitle ( "Choose Color" )
                        .initialColor ( color )
                        .wheelType ( ColorPickerView.WHEEL_TYPE.FLOWER )
                        .density ( 12 )
                        .setPositiveButton ( "Ok", new ColorPickerClickListener () {
                            @Override
                            public void onClick( DialogInterface d, int lastSelectedColor, Integer[] allColors ) {
                                brushColor = lastSelectedColor;
                                paintView.setBrushColor ( brushColor );
                                full ();
                            }
                        } ).setNegativeButton ( "Cancel", new DialogInterface.OnClickListener () {
                    @Override
                    public void onClick( DialogInterface dialog, int which ) {
                        full ();
                    }
                } ).build ().show ();
            }
        } );

        layer.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                c9.startAnimation ( animation );
                mediaPlayer1.start ();
                int color = backgroundColor;
                ColorPickerDialogBuilder
                        .with ( PaintingOneActivity.this )
                        .setTitle ( "Choose Color" )
                        .initialColor ( color )
                        .wheelType ( ColorPickerView.WHEEL_TYPE.FLOWER )
                        .density ( 12 )
                        .setPositiveButton ( "Ok", new ColorPickerClickListener () {
                            @Override
                            public void onClick( DialogInterface d, int lastSelectedColor, Integer[] allColors ) {
                                backgroundColor = lastSelectedColor;
                                paintView.setColorBackground ( backgroundColor );
                                full ();
                            }
                        } ).setNegativeButton ( "Cancel", new DialogInterface.OnClickListener () {
                    @Override
                    public void onClick( DialogInterface dialog, int which ) {
                        full ();
                    }
                } ).build ().show ();
            }
        } );

        imageFile.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                c7.startAnimation ( animation );
                mediaPlayer1.start ();
                Intent intent1 = new Intent ( PaintingOneActivity.this,ListFiles.class );
                startActivity ( intent1 );
            }
        } );

        undo.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                c10.startAnimation ( animation );
                mediaPlayer1.start ();
                paintView.returnLastAction ();
            }
        } );
        exit.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                c4.startAnimation ( animation );
                mediaPlayer1.start ();
                paintView.clear ( paintView.getBitmap () );
                paintView.setColorBackground ( Color.WHITE );
            }
        } );

        share.setOnClickListener ( new View.OnClickListener () {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick( View v ) {
                c3.startAnimation ( animation );
                mediaPlayer1.start ();
                if (ActivityCompat.checkSelfPermission ( getApplicationContext (), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ){
                    Toast.makeText ( getApplicationContext (),"you should grant permission",Toast.LENGTH_LONG ).show ();
                    requestPermissions ( new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},PERMISSION_REQUEST_CODE );
                    return;
                }
                else {
                    Intent intent = new Intent( Intent.ACTION_SEND);
                    intent.setType("*/*");
                    intent.putExtra( Intent.EXTRA_STREAM, getlocalBitmapUri(paintView.getBitmap ()));
                    intent.putExtra ( Intent.EXTRA_TEXT,"My Paint Work" );
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(Intent.createChooser(intent, "Share"));
                }
            }
        } );

        home.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                c1.startAnimation ( animation );
                mediaPlayer1.start ();
                exit ();
            }
        } );

    }

    @Override
    public void onBackPressed() {
        exit ();
    }

    private void exit(){
        mediaPlayer.start ();
        final AlertDialog.Builder builder = new AlertDialog.Builder ( PaintingOneActivity.this,R.style.CustomDialog );
        View view = LayoutInflater.from ( PaintingOneActivity.this ).inflate ( R.layout.alphabet_write_dialog,null,false );
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
                Intent intent = new Intent ( getApplicationContext (),Main2Activity.class );
                startActivity ( intent );
            }
        } );
        alertDialog.setCanceledOnTouchOutside ( false );
        alertDialog.show ();
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

    private void full(){
        final View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
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
        Bitmap bitmap = paintView.getBitmap ();
        String fileName = UUID.randomUUID ()+".png";

        File folder = new File ( getExternalFilesDir ( Environment.DIRECTORY_PICTURES )+File.separator+"Paint");

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
    private Uri getlocalBitmapUri(Bitmap bitmap) {
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
