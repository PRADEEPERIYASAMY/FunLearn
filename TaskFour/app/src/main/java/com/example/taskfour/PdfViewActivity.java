package com.example.taskfour;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnDrawListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.github.barteksc.pdfviewer.listener.OnRenderListener;
import com.github.barteksc.pdfviewer.listener.OnTapListener;
import com.krishna.fileloader.FileLoader;
import com.krishna.fileloader.listener.FileRequestListener;
import com.krishna.fileloader.pojo.FileResponse;
import com.krishna.fileloader.request.FileLoadRequest;

import java.io.File;

public class PdfViewActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 1000;
    private PDFView pdfView;
    private String uri;
    private MediaPlayer mediaPlayer,mediaPlayer1;

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
        setContentView ( R.layout.activity_pdf_view );

        mediaPlayer = new MediaPlayer ().create(getApplicationContext (),R.raw.negative);
        mediaPlayer1 = new MediaPlayer ().create(getApplicationContext (),R.raw.rubberone);

        uri =  getIntent ().getStringExtra ( "url" );
        pdfView = findViewById ( R.id.pdf_view );

        if (ActivityCompat.checkSelfPermission ( getApplicationContext (), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ){
            requestPermissions ( new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},PERMISSION_REQUEST_CODE );
        }

        FileLoader.with ( PdfViewActivity.this ).load ( uri )
                .fromDirectory ( "PdfFiles",FileLoader.DIR_EXTERNAL_PUBLIC )
                .asFile ( new FileRequestListener<File> () {
                    @Override
                    public void onLoad( FileLoadRequest request , FileResponse<File> response ) {

                        File file = response.getBody ();

                        pdfView.fromFile ( file )
                                .password ( null )
                                .defaultPage ( 0 )
                                .enableSwipe ( true )
                                .swipeHorizontal ( false )
                                .spacing ( 10 )
                                .enableDoubletap ( true )
                                .onDraw ( new OnDrawListener () {
                                    @Override
                                    public void onLayerDrawn( Canvas canvas , float pageWidth , float pageHeight , int displayedPage ) {

                                    }
                                } ).onDrawAll ( new OnDrawListener () {
                            @Override
                            public void onLayerDrawn( Canvas canvas , float pageWidth , float pageHeight , int displayedPage ) {

                            }
                        } )
                                .onPageError ( new OnPageErrorListener () {
                                    @Override
                                    public void onPageError( int page , Throwable t ) {
                                        View view = LayoutInflater.from ( PdfViewActivity.this ).inflate ( R.layout.toast,null,false );
                                        TextView textView = view.findViewById ( R.id.toast_text );
                                        textView.setText ( "Page Error" );
                                        Toast toast = new Toast ( getApplicationContext () );
                                        toast.setView ( view );
                                        toast.show ();
                                    }
                                } )
                                .onPageChange ( new OnPageChangeListener () {
                                    @Override
                                    public void onPageChanged( int page , int pageCount ) {

                                    }
                                } )
                                .onTap ( new OnTapListener () {
                                    @Override
                                    public boolean onTap( MotionEvent e ) {
                                        return true;
                                    }
                                } )
                                .onRender ( new OnRenderListener () {
                                    @Override
                                    public void onInitiallyRendered( int nbPages , float pageWidth , float pageHeight ) {
                                        pdfView.fitToWidth ();
                                    }
                                } )
                                .enableAnnotationRendering ( true )
                                .invalidPageColor ( Color.WHITE )
                                .load ();
                    }

                    @Override
                    public void onError( FileLoadRequest request , Throwable t ) {
                        View view = LayoutInflater.from ( PdfViewActivity.this ).inflate ( R.layout.toast,null,false );
                        TextView textView = view.findViewById ( R.id.toast_text );
                        textView.setText ( "Failed" );
                        Toast toast = new Toast ( getApplicationContext () );
                        toast.setView ( view );
                        toast.show ();
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

    private void exit(){
        mediaPlayer.start ();
        final AlertDialog.Builder builder = new AlertDialog.Builder ( PdfViewActivity.this,R.style.CustomDialog );
        View view = LayoutInflater.from ( PdfViewActivity.this ).inflate ( R.layout.alphabet_write_dialog,null,false );
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

    @Override
    public void onRequestPermissionsResult( int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults ) {

        super.onRequestPermissionsResult ( requestCode, permissions, grantResults );

        if (PERMISSION_REQUEST_CODE == requestCode && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            View view = LayoutInflater.from ( PdfViewActivity.this ).inflate ( R.layout.toast,null,false );
            TextView textView = view.findViewById ( R.id.toast_text );
            ImageView imageView = view.findViewById ( R.id.toast_image );
            textView.setText ( "Permission Granted" );
            Toast toast = new Toast ( getApplicationContext () );
            toast.setView ( view );
            toast.show ();
        } else {
            View view = LayoutInflater.from ( PdfViewActivity.this ).inflate ( R.layout.toast,null,false );
            TextView textView = view.findViewById ( R.id.toast_text );
            ImageView imageView = view.findViewById ( R.id.toast_image );
            textView.setText ( "Permission Denied" );
            Toast toast = new Toast ( getApplicationContext () );
            toast.setView ( view );
            toast.show ();
        }
    }

    @Override
    public void onBackPressed() {
        exit ();
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
    protected void onPause() {
        super.onPause ();
        Main2Activity.mediaPlayer.pause ();
        overridePendingTransition ( android.R.anim.fade_in,android.R.anim.fade_out );
    }

    @Override
    protected void onResume() {
        super.onResume ();
        Main2Activity.mediaPlayer.start ();
        full ();
    }
}