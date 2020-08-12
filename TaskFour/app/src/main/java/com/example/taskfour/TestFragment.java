package com.example.taskfour;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taskfour.UserAccount.Rank;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class TestFragment extends Fragment {

    private Button button1,button2,button3,button4;
    private DatabaseReference quizRef,testRef;
    private MediaPlayer mediaPlayer,mediaPlayer1;
    private RelativeLayout c1,c2,c3,c4;

    public TestFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView( LayoutInflater inflater , ViewGroup container ,
                              Bundle savedInstanceState ) {
        View view = inflater.inflate ( R.layout.fragment_test , container , false );

        quizRef = FirebaseDatabase.getInstance ().getReference ().child ( "Rank" ).child ( FirebaseAuth.getInstance ().getCurrentUser ().getUid () );
        testRef = FirebaseDatabase.getInstance ().getReference ().child ( "Quiz" );

        button1 = view.findViewById ( R.id.test_button_one );
        button2 = view.findViewById ( R.id.test_button_two );
        button3 = view.findViewById ( R.id.test_button_three );
        button4 = view.findViewById ( R.id.test_button_four );

        c1 = view.findViewById ( R.id.c1 );
        c2 = view.findViewById ( R.id.c2 );
        c3 = view.findViewById ( R.id.c3 );
        c4 = view.findViewById ( R.id.c4 );

        mediaPlayer = new MediaPlayer ().create(getActivity (),R.raw.rubberone);
        mediaPlayer1 = new MediaPlayer ().create(getActivity (),R.raw.negative);

        View.OnClickListener onClickListener = new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                mediaPlayer.start ();
                final Intent intent = new Intent ( getContext (),QuizActivity.class );
                switch (v.getId ()){
                    case R.id.test_button_one:
                        c1.startAnimation ( AnimationUtils.loadAnimation ( getActivity (),R.anim.transparantbut ) );
                        testRef.addListenerForSingleValueEvent ( new ValueEventListener () {
                            @Override
                            public void onDataChange( @NonNull final DataSnapshot dataSnapshot1 ) {
                                final String testId = dataSnapshot1.child ( "Id" ).child ( "English" ).getValue ().toString ();
                                quizRef.addListenerForSingleValueEvent ( new ValueEventListener () {
                                    @Override
                                    public void onDataChange( @NonNull DataSnapshot dataSnapshot2 ) {
                                        if (!dataSnapshot2.child ( testId ).exists () || TextUtils.equals ( dataSnapshot2.child ( testId ).child ( "state" ).getValue ().toString (),"take" )){
                                            HashMap<String,Object> rank = new HashMap<> (  );
                                            rank.put ( "state","take" );
                                            rank.put ( "wrong","0" );
                                            rank.put ( "correct","0" );
                                            rank.put ( "left","0" );
                                            rank.put ( "date","0" );
                                            rank.put ( "time","0" );
                                            quizRef.child ( testId ).setValue ( rank );
                                            intent.putExtra ( "quiz",1 );
                                            getContext ().startActivity ( intent );
                                        }
                                        else if (TextUtils.equals ( dataSnapshot2.child ( testId ).child ( "state" ).getValue ().toString (),"done" )){
                                            mediaPlayer1.start ();
                                            Rank rank =  dataSnapshot2.child ( testId ).getValue (Rank.class);
                                            final AlertDialog.Builder builder = new AlertDialog.Builder ( getActivity (),R.style.CustomDialog );
                                            View view = LayoutInflater.from ( getActivity () ).inflate ( R.layout.about_dialog,null,false );
                                            Button close = view.findViewById ( R.id.info_close );
                                            TextView infoText = view.findViewById ( R.id.info_text );
                                            infoText.setText ( "Sorry kid the test is completed by you at \n"+rank.getDate ()+"-"+rank.getTime ()+"."+"\n\nYour Result:\n\n"+"Correct : "+rank.getCorrect ()+"\n"+"Wrong : "+rank.getWrong ()+"\n"+"Left : "+rank.getLeft () );
                                            final AlertDialog alertDialog = builder.create ();
                                            alertDialog.setView ( view );

                                            close.setOnClickListener ( new View.OnClickListener () {
                                                @Override
                                                public void onClick( View v ) {
                                                    mediaPlayer.start ();
                                                    alertDialog.cancel ();
                                                }
                                            } );

                                            alertDialog.setCanceledOnTouchOutside ( false );
                                            alertDialog.show ();
                                        }
                                    }

                                    @Override
                                    public void onCancelled( @NonNull DatabaseError databaseError ) {

                                    }
                                } );
                            }

                            @Override
                            public void onCancelled( @NonNull DatabaseError databaseError ) {

                            }
                        } );
                        break;
                    case R.id.test_button_two:
                        c2.startAnimation ( AnimationUtils.loadAnimation ( getActivity (),R.anim.transparantbut ) );
                        testRef.addListenerForSingleValueEvent ( new ValueEventListener () {
                            @Override
                            public void onDataChange( @NonNull final DataSnapshot dataSnapshot1 ) {
                                final String testId = dataSnapshot1.child ( "Id" ).child ( "Maths" ).getValue ().toString ();
                                quizRef.addListenerForSingleValueEvent ( new ValueEventListener () {
                                    @Override
                                    public void onDataChange( @NonNull DataSnapshot dataSnapshot2 ) {
                                        if (!dataSnapshot2.child ( testId ).exists () || TextUtils.equals ( dataSnapshot2.child ( testId ).child ( "state" ).getValue ().toString (),"take" )){
                                            HashMap<String,Object> rank = new HashMap<> (  );
                                            rank.put ( "state","take" );
                                            rank.put ( "wrong","0" );
                                            rank.put ( "correct","0" );
                                            rank.put ( "left","0" );
                                            rank.put ( "date","0" );
                                            rank.put ( "time","0" );
                                            quizRef.child ( testId ).setValue ( rank );
                                            intent.putExtra ( "quiz",2 );
                                            getContext ().startActivity ( intent );
                                        }
                                        else if (TextUtils.equals ( dataSnapshot2.child ( testId ).child ( "state" ).getValue ().toString (),"done" )){
                                            mediaPlayer1.start ();
                                            Rank rank =  dataSnapshot2.child ( testId ).getValue (Rank.class);
                                            final AlertDialog.Builder builder = new AlertDialog.Builder ( getActivity (),R.style.CustomDialog );
                                            View view = LayoutInflater.from ( getActivity () ).inflate ( R.layout.about_dialog,null,false );
                                            Button close = view.findViewById ( R.id.info_close );
                                            TextView infoText = view.findViewById ( R.id.info_text );
                                            infoText.setText ( "Sorry kid the test is completed by you at \n"+rank.getDate ()+"-"+rank.getTime ()+"."+"\n\nYour Result:\n\n"+"Correct : "+rank.getCorrect ()+"\n"+"Wrong : "+rank.getWrong ()+"\n"+"Left : "+rank.getLeft () );
                                            final AlertDialog alertDialog = builder.create ();
                                            alertDialog.setView ( view );

                                            close.setOnClickListener ( new View.OnClickListener () {
                                                @Override
                                                public void onClick( View v ) {
                                                    mediaPlayer.start ();
                                                    alertDialog.cancel ();
                                                }
                                            } );

                                            alertDialog.setCanceledOnTouchOutside ( false );
                                            alertDialog.show ();
                                        }
                                    }

                                    @Override
                                    public void onCancelled( @NonNull DatabaseError databaseError ) {

                                    }
                                } );
                            }

                            @Override
                            public void onCancelled( @NonNull DatabaseError databaseError ) {

                            }
                        } );
                        break;
                    case R.id.test_button_three:
                        c3.startAnimation ( AnimationUtils.loadAnimation ( getActivity (),R.anim.transparantbut ) );
                        testRef.addListenerForSingleValueEvent ( new ValueEventListener () {
                            @Override
                            public void onDataChange( @NonNull final DataSnapshot dataSnapshot1 ) {
                                final String testId = dataSnapshot1.child ( "Id" ).child ( "Science" ).getValue ().toString ();
                                quizRef.addListenerForSingleValueEvent ( new ValueEventListener () {
                                    @Override
                                    public void onDataChange( @NonNull DataSnapshot dataSnapshot2 ) {
                                        if (!dataSnapshot2.child ( testId ).exists () || TextUtils.equals ( dataSnapshot2.child ( testId ).child ( "state" ).getValue ().toString (),"take" )){
                                            HashMap<String,Object> rank = new HashMap<> (  );
                                            rank.put ( "state","take" );
                                            rank.put ( "wrong","0" );
                                            rank.put ( "correct","0" );
                                            rank.put ( "left","0" );
                                            rank.put ( "date","0" );
                                            rank.put ( "time","0" );
                                            quizRef.child ( testId ).setValue ( rank );
                                            intent.putExtra ( "quiz",3 );
                                            getContext ().startActivity ( intent );
                                        }
                                        else if (TextUtils.equals ( dataSnapshot2.child ( testId ).child ( "state" ).getValue ().toString (),"done" )){
                                            mediaPlayer1.start ();
                                            Rank rank =  dataSnapshot2.child ( testId ).getValue (Rank.class);
                                            final AlertDialog.Builder builder = new AlertDialog.Builder ( getActivity (),R.style.CustomDialog );
                                            View view = LayoutInflater.from ( getActivity () ).inflate ( R.layout.about_dialog,null,false );
                                            Button close = view.findViewById ( R.id.info_close );
                                            TextView infoText = view.findViewById ( R.id.info_text );
                                            infoText.setText ( "Sorry kid the test is completed by you at \n"+rank.getDate ()+"-"+rank.getTime ()+"."+"\n\nYour Result:\n\n"+"Correct : "+rank.getCorrect ()+"\n"+"Wrong : "+rank.getWrong ()+"\n"+"Left : "+rank.getLeft () );
                                            final AlertDialog alertDialog = builder.create ();
                                            alertDialog.setView ( view );

                                            close.setOnClickListener ( new View.OnClickListener () {
                                                @Override
                                                public void onClick( View v ) {
                                                    mediaPlayer.start ();
                                                    alertDialog.cancel ();
                                                }
                                            } );

                                            alertDialog.setCanceledOnTouchOutside ( false );
                                            alertDialog.show ();
                                        }
                                    }

                                    @Override
                                    public void onCancelled( @NonNull DatabaseError databaseError ) {

                                    }
                                } );
                            }

                            @Override
                            public void onCancelled( @NonNull DatabaseError databaseError ) {

                            }
                        } );
                        break;
                    case R.id.test_button_four:
                        c4.startAnimation ( AnimationUtils.loadAnimation ( getActivity (),R.anim.transparantbut ) );
                        testRef.addListenerForSingleValueEvent ( new ValueEventListener () {
                            @Override
                            public void onDataChange( @NonNull final DataSnapshot dataSnapshot1 ) {
                                final String testId = dataSnapshot1.child ( "Id" ).child ( "Social" ).getValue ().toString ();
                                quizRef.addListenerForSingleValueEvent ( new ValueEventListener () {
                                    @Override
                                    public void onDataChange( @NonNull DataSnapshot dataSnapshot2 ) {
                                        if (!dataSnapshot2.child ( testId ).exists () || TextUtils.equals ( dataSnapshot2.child ( testId ).child ( "state" ).getValue ().toString (),"take" )){
                                            HashMap<String,Object> rank = new HashMap<> (  );
                                            rank.put ( "state","take" );
                                            rank.put ( "wrong","0" );
                                            rank.put ( "correct","0" );
                                            rank.put ( "left","0" );
                                            rank.put ( "date","0" );
                                            rank.put ( "time","0" );
                                            quizRef.child ( testId ).setValue ( rank );
                                            intent.putExtra ( "quiz",4 );
                                            getContext ().startActivity ( intent );
                                        }
                                        else if (TextUtils.equals ( dataSnapshot2.child ( testId ).child ( "state" ).getValue ().toString (),"done" )){
                                            mediaPlayer1.start ();
                                            Rank rank =  dataSnapshot2.child ( testId ).getValue (Rank.class);
                                            final AlertDialog.Builder builder = new AlertDialog.Builder ( getActivity (),R.style.CustomDialog );
                                            View view = LayoutInflater.from ( getActivity () ).inflate ( R.layout.about_dialog,null,false );
                                            Button close = view.findViewById ( R.id.info_close );
                                            TextView infoText = view.findViewById ( R.id.info_text );
                                            infoText.setText ( "Sorry kid the test is completed by you at \n"+rank.getDate ()+"-"+rank.getTime ()+"."+"\n\nYour Result:\n\n"+"Correct : "+rank.getCorrect ()+"\n"+"Wrong : "+rank.getWrong ()+"\n"+"Left : "+rank.getLeft () );
                                            final AlertDialog alertDialog = builder.create ();
                                            alertDialog.setView ( view );

                                            close.setOnClickListener ( new View.OnClickListener () {
                                                @Override
                                                public void onClick( View v ) {
                                                    mediaPlayer.start ();
                                                    alertDialog.cancel ();
                                                }
                                            } );

                                            alertDialog.setCanceledOnTouchOutside ( false );
                                            alertDialog.show ();
                                        }
                                    }

                                    @Override
                                    public void onCancelled( @NonNull DatabaseError databaseError ) {

                                    }
                                } );
                            }

                            @Override
                            public void onCancelled( @NonNull DatabaseError databaseError ) {

                            }
                        } );
                        break;
                }
            }
        };

        button1.setOnClickListener ( onClickListener );
        button2.setOnClickListener ( onClickListener );
        button3.setOnClickListener ( onClickListener );
        button4.setOnClickListener ( onClickListener );

        return view;
    }
}