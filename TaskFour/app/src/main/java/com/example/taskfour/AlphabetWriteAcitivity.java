package com.example.taskfour;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

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
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.taskfour.Widget.PaintView;
import com.example.taskfour.alphabets.AlphabetImagesAndNames;
import com.example.taskfour.alphabets.ListOfAlphabets;
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

public class AlphabetWriteAcitivity extends AppCompatActivity {

    private PaintView paintView;
    private TextView task,resultName;
    private Button submit,clear,next,back;
    private List<String> alphabets = new ArrayList<> (  );
    private List<String> indexAlphabets = new ArrayList<> (  );
    private GifImageView gifImageView;
    private ImageView resultImage;
    private boolean block = false;
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
        setContentView ( R.layout.activity_alphabet_write_acitivity );
        paintView = findViewById ( R.id.paint_view );
        paintView.setColorBackground ( Color.rgb ( 176,222,243) );
        paintView.setSizeBrush ( 4 );
        paintView.setBrushColor ( Color.RED );
        gifImageView = findViewById ( R.id.gif );
        resultImage = findViewById ( R.id.result_image );
        resultName = findViewById ( R.id.result_name );
        gifImageView.setVisibility ( View.INVISIBLE );

        c1 = findViewById ( R.id.c1 );
        c2 = findViewById ( R.id.c2 );
        c3 = findViewById ( R.id.c3 );

        task = findViewById ( R.id.task );
        submit = findViewById ( R.id.submit );
        next = findViewById ( R.id.next );
        clear = findViewById ( R.id.clear );
        back = findViewById ( R.id.back );
        next.setEnabled ( false );

        for (int i = 0; i < 26; i++) {
            alphabets.add ( ListOfAlphabets.alphabetsUpper[i] );
            alphabets.add ( ListOfAlphabets.alphabets[i] );
            indexAlphabets.add ( ListOfAlphabets.alphabets[i] );
        }

        task.setText ( alphabets.get ( 0 ) );
        alphabets.remove ( 0 );

        mediaPlayer = new MediaPlayer ().create(getApplicationContext (),R.raw.rubberone);
        mediaPlayer1 = new MediaPlayer ().create(getApplicationContext (),R.raw.negative);
        mediaPlayer2 = new MediaPlayer ().create(getApplicationContext (),R.raw.sucess);
        mediaPlayer3 = new MediaPlayer ().create(getApplicationContext (),R.raw.explosion);

        View.OnClickListener onClickListener = new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                mediaPlayer.start ();
                switch (v.getId ()){
                    case R.id.submit:
                        c1.startAnimation ( AnimationUtils.loadAnimation ( getApplicationContext (),R.anim.button ) );
                        detect ();
                        break;
                    case R.id.next:
                        c2.startAnimation ( AnimationUtils.loadAnimation ( getApplicationContext (),R.anim.button ) );
                        submit.setEnabled ( true );
                        if (alphabets.size ()>0){
                            next.setEnabled ( false );
                            task.setText ( alphabets.get ( 0 ) );
                            alphabets.remove ( 0 );
                            paintView.clear ( paintView.getBitmap () );
                            paintView.setColorBackground ( Color.rgb ( 176,222,243) );
                            resultImage.setVisibility ( View.VISIBLE );
                            resultImage.setImageResource ( R.drawable.result );
                            gifImageView.setVisibility ( View.INVISIBLE );
                            resultName.setText ( "Result" );
                        }
                        if (alphabets.size () == 0 && !block)
                        {
                            block = true;
                        }
                        else if (alphabets.size () == 0 && block){
                            AlertDialog.Builder builder = new AlertDialog.Builder ( AlphabetWriteAcitivity.this,R.style.CustomDialog);
                            View view = LayoutInflater.from ( AlphabetWriteAcitivity.this ).inflate ( R.layout.alphabet_write_dialog,null,false );
                            Button yes = view.findViewById ( R.id.yes );
                            Button no = view.findViewById ( R.id.no );
                            View.OnClickListener onClickListener1 = new View.OnClickListener () {
                                @Override
                                public void onClick( View v ) {
                                    switch (v.getId ()){
                                        case R.id.yes:
                                            recreate ();
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
                final AlertDialog.Builder builder = new AlertDialog.Builder ( AlphabetWriteAcitivity.this,R.style.CustomDialog );
                View view = LayoutInflater.from ( AlphabetWriteAcitivity.this ).inflate ( R.layout.about_dialog,null,false );

                Button close = view.findViewById ( R.id.info_close );
                TextView infoText = view.findViewById ( R.id.info_text );

                infoText.setText ( "In this page writing space is provided for user to write the alphabet given to copy. If user write the alphabet properly positive result will be shown." );

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
        mediaPlayer1.start ();
        final AlertDialog.Builder builder = new AlertDialog.Builder ( AlphabetWriteAcitivity.this,R.style.CustomDialog );
        View view = LayoutInflater.from ( AlphabetWriteAcitivity.this ).inflate ( R.layout.alphabet_write_dialog,null,false );
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
                alertDialog.cancel ();
                full ();
            }
        } );

        yes.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                mediaPlayer.start ();
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
                    resultImage.setVisibility ( View.INVISIBLE );
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
                            mediaPlayer2.start ();
                            resultImage.setVisibility ( View.VISIBLE );
                            gifImageView.setVisibility ( View.INVISIBLE );
                            int index = indexAlphabets.indexOf ( task.getText ().toString ().toLowerCase () );
                            int random = (int) ( Math.random () * 19 );
                            resultName.setText ( String.valueOf ( AlphabetImagesAndNames.name[index][random] ) );
                            Glide.with ( getApplicationContext () ).load ( AlphabetImagesAndNames.images[index][random] ).into ( resultImage );
                            next.setEnabled ( true );
                            submit.setEnabled ( false );
                        }
                        else {
                            mediaPlayer3.start ();
                            resultImage.setVisibility ( View.INVISIBLE );
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
                View view = LayoutInflater.from ( AlphabetWriteAcitivity.this ).inflate ( R.layout.toast,null,false );
                TextView textView = view.findViewById ( R.id.toast_text );
                textView.setText ( "Detection Failed" );
                Toast toast = new Toast ( getApplicationContext () );
                toast.setView ( view );
                toast.show ();
            }
        } );
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
