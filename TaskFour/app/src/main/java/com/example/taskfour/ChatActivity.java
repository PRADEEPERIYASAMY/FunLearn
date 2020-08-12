package com.example.taskfour;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taskfour.Adapters.QuestionAdapter;
import com.example.taskfour.UserAccount.Profile;
import com.example.taskfour.UserAccount.Question;
import com.example.taskfour.UserAccount.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private Button askQuestionButton;
    private DatabaseReference chatRef,userRef;
    private FirebaseAuth mAuth;
    private String date,time;
    private String userName,userImage;
    public static List<Question> questionList = new ArrayList<> (  );
    private QuestionAdapter questionAdapter;
    private RecyclerView questionRecycler;
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
        setContentView ( R.layout.activity_chat );

        mediaPlayer = new MediaPlayer ().create(getApplicationContext (),R.raw.negative);
        mediaPlayer1 = new MediaPlayer ().create(getApplicationContext (),R.raw.rubberone);

        questionList.clear ();

        mAuth = FirebaseAuth.getInstance ();
        chatRef = FirebaseDatabase.getInstance ().getReference ().child ( "Chat" ).child ( "Question" );
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

        questionRecycler = findViewById ( R.id.question_list_recycler );
        questionAdapter = new QuestionAdapter (questionList);
        questionRecycler.setLayoutManager ( new LinearLayoutManager ( getApplicationContext () ) );
        questionRecycler.setAdapter ( questionAdapter );


        askQuestionButton = findViewById ( R.id.ask_question_button );
        askQuestionButton.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                mediaPlayer.start ();
                AlertDialog.Builder builder = new AlertDialog.Builder ( ChatActivity.this,R.style.CustomDialog );
                View view = LayoutInflater.from ( ChatActivity.this ).inflate ( R.layout.question_dialog,null );
                final MaterialEditText materialEditText = view.findViewById ( R.id.ask_question );
                Button post = view.findViewById ( R.id.post );
                Button cancel = view.findViewById ( R.id.cancel );
                builder.setView ( view );
                final AlertDialog alertDialog = builder.create ();
                cancel.setOnClickListener ( new View.OnClickListener () {
                    @Override
                    public void onClick( View v ) {
                        alertDialog.dismiss ();
                        mediaPlayer1.start ();
                    }
                } );
                post.setOnClickListener ( new View.OnClickListener () {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick( View v ) {
                        mediaPlayer1.start ();
                        if (TextUtils.isEmpty ( materialEditText.getText ().toString () )){
                            View view = LayoutInflater.from ( ChatActivity.this ).inflate ( R.layout.toast,null,false );
                            TextView textView = view.findViewById ( R.id.toast_text );
                            textView.setText ( "Enter Question" );
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

                            post.put ( "Message",materialEditText.getText ().toString () );
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
                                    String qNo = String.valueOf ( dataSnapshot.getChildrenCount () +1 );
                                    chatRef.child ( "Q"+qNo ).setValue ( post ).addOnCompleteListener ( new OnCompleteListener<Void> () {
                                        @Override
                                        public void onComplete( @NonNull Task<Void> task ) {
                                            if (task.isSuccessful ()){
                                                View view = LayoutInflater.from ( ChatActivity.this ).inflate ( R.layout.toast,null,false );
                                                TextView textView = view.findViewById ( R.id.toast_text );
                                                textView.setText ( "Uploaded" );
                                                Toast toast = new Toast ( getApplicationContext () );
                                                toast.setView ( view );
                                                toast.show ();
                                                alertDialog.dismiss ();
                                            }
                                            else {
                                                View view = LayoutInflater.from ( ChatActivity.this ).inflate ( R.layout.toast,null,false );
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
                alertDialog.setCanceledOnTouchOutside ( false );
                alertDialog.show ();
            }
        } );

        final Button info = findViewById ( R.id.info );
        info.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                mediaPlayer.start ();
                final AlertDialog.Builder builder = new AlertDialog.Builder ( ChatActivity.this,R.style.CustomDialog );
                View view = LayoutInflater.from ( ChatActivity.this ).inflate ( R.layout.about_dialog,null,false );

                Button close = view.findViewById ( R.id.info_close );
                TextView infoText = view.findViewById ( R.id.info_text );

                infoText.setText ( "The users can ask any question related to subject and any one can answer for questions previously asked. This chat box is common for all user accounts" );

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


        questionList.clear ();

        chatRef.orderByChild ( "Code" ).addChildEventListener(new ChildEventListener () {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s)
            {
                Question question = dataSnapshot.getValue(Question.class);

                questionList.add(question);
                questionAdapter.notifyDataSetChanged ();
                questionRecycler.smoothScrollToPosition(questionRecycler.getAdapter().getItemCount());
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
            public void onCancelled(DatabaseError databaseError) {

            }
        });

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
        final AlertDialog.Builder builder = new AlertDialog.Builder ( ChatActivity.this,R.style.CustomDialog );
        View view = LayoutInflater.from ( ChatActivity.this ).inflate ( R.layout.alphabet_write_dialog,null,false );
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