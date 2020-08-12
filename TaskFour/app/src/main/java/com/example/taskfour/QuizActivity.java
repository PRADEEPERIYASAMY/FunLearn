package com.example.taskfour;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taskfour.Quiz.QuizMaster;
import com.example.taskfour.Tutorial.ClassRoom;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private DatabaseReference quizRef;
    private List<String> answerList = new ArrayList<> (  );
    private List<String> optionsList = new ArrayList<> (  );
    private List<String> questionList = new ArrayList<> (  );
    private int quiz;
    private static int questionNo;
    private TextView question;
    private Button optionOne,optionTwo,optionThree,optionFour,next;
    private CardView s1,s2,s3,s4,blue,red,green;
    private ProgressBar progressBar;
    private QuizMaster quizMaster;
    private MediaPlayer mediaPlayer,mediaPlayer1,mediaPlayer2;
    private int wrong = 0,right = 0;

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
        setContentView ( R.layout.activity_quiz );
        answerList.clear ();
        optionsList.clear ();
        questionList.clear ();

        quiz = getIntent ().getIntExtra ( "quiz",1 );

        question = findViewById ( R.id.question );
        optionOne = findViewById ( R.id.option_one );
        optionTwo = findViewById ( R.id.option_two );
        optionThree = findViewById ( R.id.option_three );
        optionFour = findViewById ( R.id.option_four );
        next = findViewById ( R.id.next );
        progressBar = findViewById ( R.id.progressBar );

        s1 = findViewById ( R.id.s1 );
        s2 = findViewById ( R.id.s2 );
        s3 = findViewById ( R.id.s3 );
        s4 = findViewById ( R.id.s4 );
        blue = findViewById ( R.id.blue );
        red = findViewById ( R.id.red );
        green = findViewById ( R.id.green );

        mediaPlayer = new MediaPlayer ().create(getApplicationContext (),R.raw.tictac);
        mediaPlayer1 = new MediaPlayer ().create(getApplicationContext (),R.raw.rubberone);
        mediaPlayer2 = new MediaPlayer ().create(getApplicationContext (),R.raw.negative);

        quizRef = FirebaseDatabase.getInstance ().getReference ().child ( "Quiz" );

        next.setText ( "Next" );

        colorSetter ();
        progressBar.setMax ( 10 );
        questionNo = 0;

        next.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                mediaPlayer1.start ();
                progressBar.setProgress ( questionNo+1 );
                if (questionNo<=9 && (next.getText ().toString ().equals ( "Next" ) || next.getText ().toString ().equals ( "Result"))){
                    questionNo +=1;
                    if (questionNo <9){
                        question.setText ( questionList.get ( questionNo ) );
                        optionOne.setText ( optionsList.get ( questionNo ).split ( "," )[0] );
                        optionTwo.setText ( optionsList.get ( questionNo ).split ( "," )[1] );
                        optionThree.setText ( optionsList.get ( questionNo ).split ( "," )[2] );
                        optionFour.setText ( optionsList.get ( questionNo ).split ( "," )[3] );
                    }
                    colorSetter ();
                    enable ();
                }
                else {
                    if (s1.getCardBackgroundColor () == blue.getCardBackgroundColor ()){
                        answerCheck ( optionOne );
                    }
                    else if (s2.getCardBackgroundColor () == blue.getCardBackgroundColor ()){
                        answerCheck ( optionTwo );
                    }
                    else if (s3.getCardBackgroundColor () == blue.getCardBackgroundColor ()){
                        answerCheck ( optionThree );
                    }
                    else if (s4.getCardBackgroundColor () == blue.getCardBackgroundColor ()){
                        answerCheck ( optionFour );
                    }
                    if (questionNo < 9){
                        next.setText ( "Next" );
                    }
                    else {
                        next.setText ( "Result" );
                    }
                    disable ();
                }
                if (questionNo == 10){
                    mark ();
                    result ();
                }
            }
        } );

        View.OnClickListener onClickListener = new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                mediaPlayer1.start ();
                next.setText ( "Verify" );
                colorSetter ();
                switch (v.getId ()){
                    case R.id.option_one:
                        s1.setCardBackgroundColor ( Color.parseColor ( "#4000A4FF" ) );
                        break;
                    case R.id.option_two:
                        s2.setCardBackgroundColor ( Color.parseColor ( "#4000A4FF" ) );
                        break;
                    case R.id.option_three:
                        s3.setCardBackgroundColor ( Color.parseColor ( "#4000A4FF" ) );
                        break;
                    case R.id.option_four:
                        s4.setCardBackgroundColor ( Color.parseColor ( "#4000A4FF" ) );
                        break;
                }
            }
        };
        optionOne.setOnClickListener ( onClickListener );
        optionTwo.setOnClickListener ( onClickListener );
        optionThree.setOnClickListener ( onClickListener );
        optionFour.setOnClickListener ( onClickListener );

        final AlertDialog.Builder builder2 = new AlertDialog.Builder ( QuizActivity.this,R.style.CustomDialog );
        View view2 = LayoutInflater.from ( QuizActivity.this ).inflate ( R.layout.alphabet_write_dialog,null,false );
        Button yes2 = view2.findViewById ( R.id.yes );
        Button no2 = view2.findViewById ( R.id.no );
        TextView firstText2 = view2.findViewById ( R.id.firstText );
        TextView secondText2 = view2.findViewById ( R.id.secontText );
        ImageView dialogImage2 = view2.findViewById ( R.id.dialog_image );

        firstText2.setText ( "Hello Kid" );
        secondText2.setText ( "Do you wanna start quiz?" );

        dialogImage2.setImageResource ( R.drawable.omg );
        final AlertDialog alertDialog2 = builder2.create ();
        alertDialog2.setView ( view2 );
        mediaPlayer2.start ();

        no2.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                mediaPlayer1.start ();
                alertDialog2.cancel ();
                finish ();
            }
        } );

        yes2.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                mediaPlayer1.start ();
                quizRef.addListenerForSingleValueEvent ( new ValueEventListener () {
                    @Override
                    public void onDataChange( @NonNull DataSnapshot dataSnapshot ) {
                        if (quiz == 1){
                            for (int i = 0; i < dataSnapshot.child ( "English" ).getChildrenCount () ; i++) {
                                quizMaster = dataSnapshot.child ( "English" ).child ( "Q"+String.valueOf ( i+1 ) ).getValue ( QuizMaster.class );
                                answerList.add ( quizMaster.getAnswer () );
                                optionsList.add ( quizMaster.getOptions () );
                                questionList.add ( quizMaster.getQuestion () );
                            }
                        }
                        else if (quiz == 2){
                            for (int i = 0; i < dataSnapshot.child ( "Maths" ).getChildrenCount () ; i++) {
                                quizMaster = dataSnapshot.child ( "Maths" ).child ( "Q"+String.valueOf ( i+1 ) ).getValue ( QuizMaster.class );
                                answerList.add ( quizMaster.getAnswer () );
                                optionsList.add ( quizMaster.getOptions () );
                                questionList.add ( quizMaster.getQuestion () );
                            }
                        }
                        else if (quiz == 3){
                            for (int i = 0; i < dataSnapshot.child ( "Science" ).getChildrenCount () ; i++) {
                                quizMaster = dataSnapshot.child ( "Science" ).child ( "Q"+String.valueOf ( i+1 ) ).getValue ( QuizMaster.class );
                                answerList.add ( quizMaster.getAnswer () );
                                optionsList.add ( quizMaster.getOptions () );
                                questionList.add ( quizMaster.getQuestion () );
                            }
                        }
                        else if (quiz == 4){
                            for (int i = 0; i < dataSnapshot.child ( "Social" ).getChildrenCount () ; i++) {
                                quizMaster = dataSnapshot.child ( "Social" ).child ( "Q"+String.valueOf ( i+1 ) ).getValue ( QuizMaster.class );
                                answerList.add ( quizMaster.getAnswer () );
                                optionsList.add ( quizMaster.getOptions () );
                                questionList.add ( quizMaster.getQuestion () );
                            }
                        }

                        questionNo = 0;
                        question.setText ( questionList.get ( 0 ) );
                        optionOne.setText ( optionsList.get ( 0 ).split ( "," )[0] );
                        optionTwo.setText ( optionsList.get ( 0 ).split ( "," )[1] );
                        optionThree.setText ( optionsList.get ( 0 ).split ( "," )[2] );
                        optionFour.setText ( optionsList.get ( 0 ).split ( "," )[3] );
                        alertDialog2.dismiss ();
                        full ();
                    }

                    @Override
                    public void onCancelled( @NonNull DatabaseError databaseError ) {

                    }
                } );
            }
        } );
        alertDialog2.setCanceledOnTouchOutside ( false );
        alertDialog2.show ();

        final Button info = findViewById ( R.id.info );
        info.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                mediaPlayer2.start ();
                final AlertDialog.Builder builder = new AlertDialog.Builder ( QuizActivity.this,R.style.CustomDialog );
                View view = LayoutInflater.from ( QuizActivity.this ).inflate ( R.layout.about_dialog,null,false );

                Button close = view.findViewById ( R.id.info_close );
                TextView infoText = view.findViewById ( R.id.info_text );

                infoText.setText ( "Just select the correct option based on the give question. The quiz can be attend one time, if user exit out of page the quiz will end and result will be calculated." );

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

        Button back = findViewById ( R.id.back );
        back.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                exit ();
            }
        } );
    }

    private void answerCheck(Button button){
        if (button.getText ().toString ().equals ( answerList.get ( questionNo ) )){
            if (button.getId () == R.id.option_one){
                s1.setCardBackgroundColor ( Color.parseColor ( "#4577FF00" ) );
            }
            else if (button.getId () == R.id.option_two){
                s2.setCardBackgroundColor ( Color.parseColor ( "#4577FF00" ) );
            }
            else if (button.getId () == R.id.option_three){
                s3.setCardBackgroundColor ( Color.parseColor ( "#4577FF00" ) );
            }
            else if (button.getId () == R.id.option_four){
                s4.setCardBackgroundColor ( Color.parseColor ( "#4577FF00" ) );
            }
            right++;
        }
        else {
            if (optionOne.getText ().toString ().equals ( answerList.get ( questionNo ) )){
                s1.setCardBackgroundColor ( Color.parseColor ( "#5BFF0000" ) );
            }
            else if (optionTwo.getText ().toString ().equals ( answerList.get ( questionNo ) )){
                s2.setCardBackgroundColor ( Color.parseColor ( "#5BFF0000" ) );
            }
            else if (optionThree.getText ().toString ().equals ( answerList.get ( questionNo ) )){
                s3.setCardBackgroundColor ( Color.parseColor ( "#5BFF0000" ) );
            }
            else if (optionFour.getText ().toString ().equals ( answerList.get ( questionNo ) )){
                s4.setCardBackgroundColor ( Color.parseColor ( "#5BFF0000" ) );
            }

            if (button.getId () == R.id.option_one){
                s1.setCardBackgroundColor ( Color.parseColor ( "#4577FF00" ) );
            }
            else if (button.getId () == R.id.option_two){
                s2.setCardBackgroundColor ( Color.parseColor ( "#4577FF00" ) );
            }
            else if (button.getId () == R.id.option_three){
                s3.setCardBackgroundColor ( Color.parseColor ( "#4577FF00" ) );
            }
            else if (button.getId () == R.id.option_four){
                s4.setCardBackgroundColor ( Color.parseColor ( "#4577FF00" ) );
            }

            wrong++;
        }
    }

    private void colorSetter(){
        s1.setCardBackgroundColor ( Color.WHITE );
        s2.setCardBackgroundColor ( Color.WHITE );
        s3.setCardBackgroundColor ( Color.WHITE );
        s4.setCardBackgroundColor ( Color.WHITE );
    }
    private void enable(){
        optionOne.setEnabled ( true );
        optionTwo.setEnabled ( true );
        optionThree.setEnabled ( true );
        optionFour.setEnabled ( true );
    }
    private void disable(){
        optionOne.setEnabled ( false );
        optionTwo.setEnabled ( false );
        optionThree.setEnabled ( false );
        optionFour.setEnabled ( false );
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

    private void mark(){
        final DatabaseReference quizRef = FirebaseDatabase.getInstance ().getReference ().child ( "Rank" ).child ( FirebaseAuth.getInstance ().getCurrentUser ().getUid () );
        DatabaseReference testRef = FirebaseDatabase.getInstance ().getReference ().child ( "Quiz" );

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        final String date = currentDate.format(calendar.getTime());
        SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm a");
        final String time = currentTime.format(calendar.getTime());

        testRef.addListenerForSingleValueEvent ( new ValueEventListener () {
            @Override
            public void onDataChange( @NonNull final DataSnapshot dataSnapshot1 ) {
                String testId = null;
                if (quiz == 1){
                    testId = dataSnapshot1.child ( "Id" ).child ( "English" ).getValue ().toString ();
                }
                else if (quiz == 2){
                    testId = dataSnapshot1.child ( "Id" ).child ( "Maths" ).getValue ().toString ();
                }
                else if (quiz == 3){
                    testId = dataSnapshot1.child ( "Id" ).child ( "Science" ).getValue ().toString ();
                }
                else if (quiz == 4){
                    testId = dataSnapshot1.child ( "Id" ).child ( "Social" ).getValue ().toString ();
                }
                HashMap<String,Object> rank = new HashMap<> (  );
                rank.put ( "state","done" );
                rank.put ( "wrong",String.valueOf ( wrong ) );
                rank.put ( "correct",String.valueOf ( right ) );
                rank.put ( "left",String.valueOf ( 10-right-wrong ) );
                rank.put ( "date",date );
                rank.put ( "time",time );
                quizRef.child ( testId ).setValue ( rank );
            }

            @Override
            public void onCancelled( @NonNull DatabaseError databaseError ) {

            }
        } );
    }
    private void result(){
        final AlertDialog.Builder builder = new AlertDialog.Builder ( QuizActivity.this,R.style.CustomDialog );
        View view = LayoutInflater.from ( QuizActivity.this ).inflate ( R.layout.info_dialog,null,false );
        TextView first = view.findViewById ( R.id.firstText );
        TextView second = view.findViewById ( R.id.secontText );
        ImageView imageView = view.findViewById ( R.id.dialog_image );
        imageView.setImageResource ( R.drawable.quizcompleted );

        first.setText ( "Quiz completed kid..." );
        second.setText ( "You got "+String.valueOf ( right )+" / 10 ." );

        Button yes = view.findViewById ( R.id.yes );
        final AlertDialog alertDialog = builder.create ();
        alertDialog.setView ( view );

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
    protected void onResume() {
        full ();
        super.onResume ();
    }

    @Override
    protected void onPause() {
        super.onPause ();
        overridePendingTransition ( android.R.anim.fade_in,android.R.anim.fade_out );
    }
    private void exit(){
        mediaPlayer2.start ();
        final AlertDialog.Builder builder = new AlertDialog.Builder ( QuizActivity.this,R.style.CustomDialog );
        View view = LayoutInflater.from ( QuizActivity.this ).inflate ( R.layout.alphabet_write_dialog,null,false );
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
    public void onBackPressed() {
        exit ();
    }
}