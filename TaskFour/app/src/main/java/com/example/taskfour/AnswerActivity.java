package com.example.taskfour;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.taskfour.Adapters.AnswerAdapter;
import com.example.taskfour.UserAccount.Profile;
import com.example.taskfour.UserAccount.Question;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class AnswerActivity extends AppCompatActivity {

    private EditText answerInput;
    private String name,date,time,image,question,position;
    private DatabaseReference chatRef,userRef;
    private FirebaseAuth mAuth;
    private List<Question> answerList = new ArrayList<> (  );
    private AnswerAdapter answerAdapter;
    private RecyclerView answerRecycler;
    private ImageButton sendMessage,sendFile;
    private String userName,userImage;
    private String Checker;
    private Uri fileUri;
    private MediaPlayer mediaPlayer,mediaPlayer1;
    private TextView user_name,user_dateTime,user_question;
    private ImageView user_Image;

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
        setContentView ( R.layout.activity_answer );

        mediaPlayer = new MediaPlayer ().create(getApplicationContext (),R.raw.negative);
        mediaPlayer1 = new MediaPlayer ().create(getApplicationContext (),R.raw.rubberone);
        answerList.clear ();
        name = getIntent ().getStringExtra ( "name" );
        date = getIntent ().getStringExtra ( "date" );
        time = getIntent ().getStringExtra ( "time" );
        image = getIntent ().getStringExtra ( "image" );
        question = getIntent ().getStringExtra ( "question" );
        position = getIntent ().getStringExtra ( "position" );
        answerInput = findViewById ( R.id.input_message );
        answerRecycler = findViewById ( R.id.answer_recycler );
        sendMessage = findViewById ( R.id.send_message_btn );
        sendFile = findViewById ( R.id.send_files_btn );

        user_name = findViewById ( R.id.user_name );
        user_dateTime = findViewById ( R.id.time_uploaded );
        user_question = findViewById ( R.id.user_question );
        user_Image = findViewById ( R.id.user_image );

        user_name.setText ( name );
        user_dateTime.setText ( date+"\n"+time );
        user_question.setText ( question );
        Glide.with ( getApplicationContext () ).load ( image ).into ( user_Image );

        chatRef = FirebaseDatabase.getInstance ().getReference ().child ( "Chat" ).child ( "Answer" ).child ( "Q"+position );
        answerAdapter = new AnswerAdapter ( answerList );
        answerRecycler.setLayoutManager ( new LinearLayoutManager ( getApplicationContext () ) );
        answerRecycler.setAdapter ( answerAdapter );

        mAuth = FirebaseAuth.getInstance ();
        userRef = FirebaseDatabase.getInstance ().getReference ().child ( "Profile" ).child ( mAuth.getCurrentUser ().getUid () );
        userRef.addListenerForSingleValueEvent ( new ValueEventListener () {
            @Override
            public void onDataChange( @NonNull DataSnapshot dataSnapshot ) {
                if (dataSnapshot.exists ()){
                    Profile profile = dataSnapshot.getValue ( Profile.class );
                    userName = profile.getProfileName ();
                    userImage = profile.getImage ();
                }
            }

            @Override
            public void onCancelled( @NonNull DatabaseError databaseError ) {

            }
        } );

        sendFile.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                mediaPlayer.start ();
                final AlertDialog.Builder builder = new AlertDialog.Builder ( AnswerActivity.this,R.style.CustomDialog );
                View view = LayoutInflater.from ( AnswerActivity.this ).inflate ( R.layout.choice_dialog,null,false );

                Button image = view.findViewById ( R.id.image );
                Button pdf = view.findViewById ( R.id.pdf );
                Button docx = view.findViewById ( R.id.docx );

                final AlertDialog alertDialog = builder.create ();
                alertDialog.setView ( view );

                image.setOnClickListener ( new View.OnClickListener () {
                    @Override
                    public void onClick( View v ) {
                        mediaPlayer1.start ();
                        alertDialog.cancel ();
                        full ();
                        Checker = "jpg";
                        Intent intent = new Intent (  );
                        intent.setAction ( Intent.ACTION_GET_CONTENT );
                        intent.setType ( "image/*" );
                        startActivityForResult ( Intent.createChooser ( intent,"select Image" ),438 );
                    }
                } );

                pdf.setOnClickListener ( new View.OnClickListener () {
                    @Override
                    public void onClick( View v ) {
                        mediaPlayer1.start ();
                        alertDialog.cancel ();
                        full ();
                        Checker = "pdf";
                        Intent intent = new Intent (  );
                        intent.setAction ( Intent.ACTION_GET_CONTENT );
                        intent.setType ( "application/pdf" );
                        startActivityForResult ( Intent.createChooser ( intent,"select pdf file" ),438 );
                    }
                } );

                docx.setOnClickListener ( new View.OnClickListener () {
                    @Override
                    public void onClick( View v ) {
                        mediaPlayer1.start ();
                        alertDialog.cancel ();
                        full ();
                        Checker = "docx";
                        Intent intent = new Intent (  );
                        intent.setAction ( Intent.ACTION_GET_CONTENT );
                        intent.setType ( "application/msword" );
                        startActivityForResult ( Intent.createChooser ( intent,"select ms word file" ),438 );
                    }
                } );

                alertDialog.setCanceledOnTouchOutside ( true );
                alertDialog.show ();
            }
        } );

        sendMessage.setOnClickListener ( new View.OnClickListener () {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick( View v ) {
                mediaPlayer1.start ();
                if (TextUtils.isEmpty ( answerInput.getText ().toString () )){
                    View view = LayoutInflater.from ( AnswerActivity.this ).inflate ( R.layout.toast,null,false );
                    TextView textView = view.findViewById ( R.id.toast_text );
                    textView.setText ( "Enter answer" );
                    Toast toast = new Toast ( getApplicationContext () );
                    toast.setView ( view );
                    toast.show ();
                }
                else {
                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
                    date = currentDate.format(calendar.getTime());
                    SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm a");
                    time = currentTime.format(calendar.getTime());
                    final HashMap<String,Object> post = new HashMap<> (  );

                    post.put ( "Message",answerInput.getText ().toString () );
                    post.put ( "Name",userName );
                    post.put ( "Image",userImage );
                    post.put ( "Date",date );
                    post.put ( "Time",time );
                    post.put ( "Code",String.valueOf ( Instant.now().toEpochMilli() ) );
                    post.put ( "Type","text" );
                    post.put ( "Id",mAuth.getCurrentUser ().getUid () );

                    chatRef.addListenerForSingleValueEvent ( new ValueEventListener () {
                        @Override
                        public void onDataChange( @NonNull DataSnapshot dataSnapshot ) {
                            String aNo = String.valueOf ( dataSnapshot.getChildrenCount () +1 );
                            chatRef.child ( "A"+aNo ).setValue ( post )
                                    .addOnCompleteListener ( new OnCompleteListener<Void> () {
                                        @Override
                                        public void onComplete( @NonNull Task<Void> task ) {
                                            if (task.isSuccessful ()){
                                                View view = LayoutInflater.from ( AnswerActivity.this ).inflate ( R.layout.toast,null,false );
                                                TextView textView = view.findViewById ( R.id.toast_text );
                                                textView.setText ( "Uploaded" );
                                                Toast toast = new Toast ( getApplicationContext () );
                                                toast.setView ( view );
                                                toast.show ();
                                            }
                                            else {
                                                View view = LayoutInflater.from ( AnswerActivity.this ).inflate ( R.layout.toast,null,false );
                                                TextView textView = view.findViewById ( R.id.toast_text );
                                                textView.setText ( "Failed" );
                                                Toast toast = new Toast ( getApplicationContext () );
                                                toast.setView ( view );
                                                toast.show ();
                                            }
                                        }
                                    } );
                        }

                        @Override
                        public void onCancelled( @NonNull DatabaseError databaseError ) {

                        }
                    } );
                }
            }
        } );

        final Button info = findViewById ( R.id.info );
        info.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                mediaPlayer.start ();
                final AlertDialog.Builder builder = new AlertDialog.Builder ( AnswerActivity.this,R.style.CustomDialog );
                View view = LayoutInflater.from ( AnswerActivity.this ).inflate ( R.layout.about_dialog,null,false );

                Button close = view.findViewById ( R.id.info_close );
                TextView infoText = view.findViewById ( R.id.info_text );

                infoText.setText ( "This page shows the previous answers for the question selected and also users are allowed to add answers. If user touches the any profile image the corresponding user's profile will be shown" );

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

        getAnswer ();

        Button back = findViewById ( R.id.back );
        back.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                finish ();
                mediaPlayer.start ();
            }
        } );

        user_Image.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                DatabaseReference profRef;
                mediaPlayer.start ();
                profRef = FirebaseDatabase.getInstance ().getReference ().child ( "Profile" ).child ( ChatActivity.questionList.get ( Integer.parseInt ( position ) ).getId () );
                profRef.addListenerForSingleValueEvent ( new ValueEventListener () {
                    @Override
                    public void onDataChange( @NonNull DataSnapshot dataSnapshot ) {
                        Profile profile = dataSnapshot.getValue (Profile.class);
                        final AlertDialog.Builder builder = new AlertDialog.Builder ( AnswerActivity.this,R.style.CustomDialog );
                        View view = LayoutInflater.from ( AnswerActivity.this ).inflate ( R.layout.view_profile_dialog,null,false );

                        Button close = view.findViewById ( R.id.info_close );
                        TextView infoText = view.findViewById ( R.id.user_text );
                        ImageView image = view.findViewById ( R.id.image );

                        try {
                            Glide.with ( AnswerActivity.this ).load ( profile.getImage () ).into ( image );
                        }
                        catch (NullPointerException e){
                            Glide.with ( AnswerActivity.this ).load ( R.drawable.usernull ).into ( image );
                        }
                        infoText.setText ( "User Name : "+profile.getProfileName ()+"\n\n"+"Age : "+profile.getAge ()+"\n\n"+"Interest : "+profile.getInterest ()+"\n\n"+"School : "+profile.getSchool () );

                        final AlertDialog alertDialog = builder.create ();
                        alertDialog.setView ( view );

                        close.setOnClickListener ( new View.OnClickListener () {
                            @Override
                            public void onClick( View v ) {
                                alertDialog.cancel ();
                                mediaPlayer1.start ();
                            }
                        } );

                        alertDialog.setCanceledOnTouchOutside ( false );
                        alertDialog.show ();
                    }

                    @Override
                    public void onCancelled( @NonNull DatabaseError databaseError ) {

                    }
                } );
            }
        } );

    }

    private void getAnswer(){
        answerList.clear ();

        chatRef.orderByChild ( "Code" ).addChildEventListener(new ChildEventListener () {
            @Override
            public void onChildAdded( DataSnapshot dataSnapshot, String s)
            {
                Question question = dataSnapshot.getValue(Question.class);

                answerList.add(question);
                answerAdapter.notifyDataSetChanged ();
                answerRecycler.smoothScrollToPosition(answerRecycler.getAdapter().getItemCount());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled( DatabaseError databaseError) {

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onActivityResult( int requestCode , int resultCode , @Nullable Intent data ) {
        super.onActivityResult ( requestCode , resultCode , data );
        if (requestCode == 438 && resultCode==RESULT_OK && data!= null && data.getData ()!= null){

            fileUri = data.getData ();
            final StorageReference filePath = FirebaseStorage.getInstance ().getReference ().child ( "chat").child ( String.valueOf ( Instant.now().toEpochMilli() )+"."+Checker );
            filePath.putFile ( fileUri ).addOnCompleteListener ( new OnCompleteListener<UploadTask.TaskSnapshot> () {
                @Override
                public void onComplete( @NonNull Task<UploadTask.TaskSnapshot> task ) {
                    if (task.isSuccessful ()){
                        Task<Uri> result = task.getResult ().getMetadata().getReference().getDownloadUrl();
                        result.addOnSuccessListener(new OnSuccessListener<Uri> () {
                            @Override
                            public void onSuccess(Uri uri) {

                                Calendar calendar = Calendar.getInstance();
                                SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
                                date = currentDate.format(calendar.getTime());
                                SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm a");
                                time = currentTime.format(calendar.getTime());

                                final HashMap<String,Object> post = new HashMap ();
                                post.put ( "Message",uri.toString () );
                                post.put ( "Name",userName );
                                post.put ( "Image",userImage );
                                post.put ( "Date",date );
                                post.put ( "Time",time );
                                post.put ( "Code",String.valueOf ( Instant.now().toEpochMilli() ) );
                                post.put ( "Type",Checker );
                                post.put ( "Id",mAuth.getCurrentUser ().getUid () );

                                chatRef.addListenerForSingleValueEvent ( new ValueEventListener () {
                                    @Override
                                    public void onDataChange( @NonNull DataSnapshot dataSnapshot ) {
                                        String aNo = String.valueOf ( dataSnapshot.getChildrenCount () +1 );
                                        chatRef.child ( "A"+aNo ).setValue ( post )
                                                .addOnCompleteListener ( new OnCompleteListener<Void> () {
                                                    @Override
                                                    public void onComplete( @NonNull Task<Void> task ) {
                                                        if (task.isSuccessful ()){
                                                            View view = LayoutInflater.from ( AnswerActivity.this ).inflate ( R.layout.toast,null,false );
                                                            TextView textView = view.findViewById ( R.id.toast_text );
                                                            textView.setText ( "Uploaded" );
                                                            Toast toast = new Toast ( getApplicationContext () );
                                                            toast.setView ( view );
                                                            toast.show ();
                                                        }
                                                        else {
                                                            View view = LayoutInflater.from ( AnswerActivity.this ).inflate ( R.layout.toast,null,false );
                                                            TextView textView = view.findViewById ( R.id.toast_text );
                                                            textView.setText ( "Failed" );
                                                            Toast toast = new Toast ( getApplicationContext () );
                                                            toast.setView ( view );
                                                            toast.show ();
                                                        }
                                                    }
                                                } );
                                    }

                                    @Override
                                    public void onCancelled( @NonNull DatabaseError databaseError ) {

                                    }
                                } );
                            }
                        });

                    }
                }
            } ).addOnFailureListener ( new OnFailureListener () {
                @Override
                public void onFailure( @NonNull Exception e ) {
                    View view = LayoutInflater.from ( AnswerActivity.this ).inflate ( R.layout.toast,null,false );
                    TextView textView = view.findViewById ( R.id.toast_text );
                    textView.setText ( "Error" );
                    Toast toast = new Toast ( getApplicationContext () );
                    toast.setView ( view );
                    toast.show ();
                }
            } ).addOnProgressListener ( new OnProgressListener<UploadTask.TaskSnapshot> () {
                @Override
                public void onProgress( UploadTask.TaskSnapshot taskSnapshot ) {
                    double p = (100.0*taskSnapshot.getBytesTransferred ())/taskSnapshot.getTotalByteCount ();
                }
            } );
        }
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
        full ();
        super.onResume ();
    }

    @Override
    protected void onPause() {
        super.onPause ();
        overridePendingTransition ( android.R.anim.fade_in,android.R.anim.fade_out );
    }
}
