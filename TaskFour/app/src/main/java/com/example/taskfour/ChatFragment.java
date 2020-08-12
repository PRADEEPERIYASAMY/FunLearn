package com.example.taskfour;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.taskfour.UserAccount.Profile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment {

    private Button button1,button2;
    private MediaPlayer mediaPlayer;
    private RelativeLayout c1,c2;

    public ChatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView( final LayoutInflater inflater , ViewGroup container ,
                              Bundle savedInstanceState ) {
        // Inflate the layout for this fragment
        View view = inflater.inflate ( R.layout.fragment_chat , container , false );

        button1 = view.findViewById ( R.id.chat_button );
        button2 = view.findViewById ( R.id.profile_button);

        c1 = view.findViewById ( R.id.c1 );
        c2 = view.findViewById ( R.id.c2 );

        mediaPlayer = new MediaPlayer ().create(getActivity (),R.raw.rubberone);

        View.OnClickListener onClickListener = new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                mediaPlayer.start ();
                switch (v.getId ()){
                    case R.id.chat_button:
                        c1.startAnimation ( AnimationUtils.loadAnimation ( getActivity (),R.anim.transparantbut ) );
                        DatabaseReference profRef;
                        profRef = FirebaseDatabase.getInstance ().getReference ().child ( "Profile" ).child ( FirebaseAuth.getInstance ().getCurrentUser ().getUid () );
                        profRef.addListenerForSingleValueEvent ( new ValueEventListener () {
                            @Override
                            public void onDataChange( @NonNull DataSnapshot dataSnapshot ) {
                                if (dataSnapshot.exists ()){
                                    Intent intent1 = new Intent ( getContext (),ChatActivity.class );
                                    getContext ().startActivity ( intent1 );
                                }
                                else {
                                    final AlertDialog.Builder builder = new AlertDialog.Builder ( getContext () ,R.style.CustomDialog );
                                    View view = LayoutInflater.from ( getContext () ).inflate ( R.layout.view_profile_dialog,null,false );

                                    Button close = view.findViewById ( R.id.info_close );
                                    TextView infoText = view.findViewById ( R.id.user_text );
                                    ImageView image = view.findViewById ( R.id.image );
                                    image.setImageResource ( R.drawable.good );
                                    infoText.setText ( "First complete profile inorder to access the community chat. Purpose of profile is to show you to the world, originality of information is user choice." );
                                    final AlertDialog alertDialog = builder.create ();
                                    alertDialog.setView ( view );

                                    close.setOnClickListener ( new View.OnClickListener () {
                                        @Override
                                        public void onClick( View v ) {
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
                        break;
                    case R.id.profile_button:
                        c2.startAnimation ( AnimationUtils.loadAnimation ( getActivity (),R.anim.transparantbut ) );
                        Intent intent3 = new Intent ( getContext (),ProfileActivity.class );
                        getContext ().startActivity ( intent3 );
                        break;
                }
            }
        };

        button1.setOnClickListener ( onClickListener );
        button2.setOnClickListener ( onClickListener );

        return view;
    }
}