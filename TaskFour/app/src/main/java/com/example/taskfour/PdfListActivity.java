package com.example.taskfour;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taskfour.Adapters.PdfAdapter;
import com.example.taskfour.Quiz.Pdf;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PdfListActivity extends AppCompatActivity {

    private DatabaseReference pdfRef;
    private String type;
    private List<String> name = new ArrayList<> (  );
    private List<String> url = new ArrayList<> (  );
    private List<String> image = new ArrayList<> (  );
    private MediaPlayer mediaPlayer,mediaPlayer1;


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
        setContentView ( R.layout.activity_pdf_list );
        mediaPlayer = new MediaPlayer ().create(getApplicationContext (),R.raw.negative);
        mediaPlayer1 = new MediaPlayer ().create(getApplicationContext (),R.raw.rubberone);

        name.clear ();
        url.clear ();
        image.clear ();

        type = getIntent ().getStringExtra ( "type" );

        if (type.equals ( "1" )){
            pdfRef = FirebaseDatabase.getInstance ().getReference ().child ( "Book" ).child ( "Story" );
        }
        else if (type.equals ( "2" )){
            pdfRef = FirebaseDatabase.getInstance ().getReference ().child ( "Book" ).child ( "Alphabets" );
        }
        else if (type.equals ( "3" )){
            pdfRef = FirebaseDatabase.getInstance ().getReference ().child ( "Book" ).child ( "Comic" );
        }
        else if (type.equals ( "4" )){
            pdfRef = FirebaseDatabase.getInstance ().getReference ().child ( "Book" ).child ( "Numbers" );
        }

        pdfRef.addListenerForSingleValueEvent ( new ValueEventListener () {
            @Override
            public void onDataChange( @NonNull DataSnapshot dataSnapshot ) {
                for (int i = 0; i < dataSnapshot.getChildrenCount (); i++) {
                    Pdf pdf = dataSnapshot.child ( "P"+String.valueOf ( i+1 ) ).getValue ( Pdf.class );
                    name.add ( pdf.getName () );
                    url.add ( pdf.getUrl () );
                    image.add ( pdf.getImage () );
                }
                PdfAdapter pdfAdapter = new PdfAdapter (name,url,image);
                RecyclerView pdfRecycler = findViewById ( R.id.pdf_recycler );
                pdfRecycler.setHasFixedSize ( true );
                pdfRecycler.setLayoutManager ( new LinearLayoutManager ( PdfListActivity.this ) );
                pdfRecycler.setAdapter ( pdfAdapter );
            }

            @Override
            public void onCancelled( @NonNull DatabaseError databaseError ) {

            }
        } );

        final Button info = findViewById ( R.id.info );
        info.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                mediaPlayer1.start ();
                final AlertDialog.Builder builder = new AlertDialog.Builder ( PdfListActivity.this,R.style.CustomDialog );
                View view = LayoutInflater.from ( PdfListActivity.this ).inflate ( R.layout.about_dialog,null,false );

                Button close = view.findViewById ( R.id.info_close );
                TextView infoText = view.findViewById ( R.id.info_text );

                infoText.setText ( "In this page e-books to read and download are provide based on the topic selected, the users are allowed to content by selecting it." );

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

        Button back = findViewById ( R.id.back );
        back.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                mediaPlayer.start ();
                finish ();
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