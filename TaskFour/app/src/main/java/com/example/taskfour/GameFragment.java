package com.example.taskfour;

import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.taskfour.Database.Level;


/**
 * A simple {@link Fragment} subclass.
 */
public class GameFragment extends Fragment {

    private Button game1,game2,game3,game5;
    private MediaPlayer mediaPlayer;
    private RelativeLayout c1,c2,c3,c4;

    public GameFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState ) {
        // Inflate the layout for this fragment
        View view = inflater.inflate ( R.layout.fragment_game, container, false );
        game1 = view.findViewById ( R.id.game_one_button );
        game2 = view.findViewById ( R.id.game_two_button );
        game3 = view.findViewById ( R.id.game_three_button );
        game5 = view.findViewById ( R.id.game_five_button );
        c1 = view.findViewById ( R.id.c1 );
        c2 = view.findViewById ( R.id.c2 );
        c3 = view.findViewById ( R.id.c3 );
        c4 = view.findViewById ( R.id.c4 );

        mediaPlayer = new MediaPlayer ().create(getActivity (),R.raw.rubberone);

        Level level = new Level ( getActivity () );
        Cursor cursor = level.FetchData ();

        if (cursor.getCount () == 0){

            level.Insert ( "0" );
            level.Insert ( "0" );
            level.Insert ( "0" );
            level.Insert ( "0" );
            level.Insert ( "0" );
            level.Insert ( "0" );
        }

        View.OnClickListener onClickListener = new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                mediaPlayer.start ();
                switch (v.getId ()){
                    case R.id.game_one_button:
                        c1.startAnimation ( AnimationUtils.loadAnimation ( getActivity (),R.anim.transparantbut ) );
                        startActivity ( new Intent ( getActivity (), GameOneActivity.class ));
                        break;
                    case R.id.game_two_button:
                        c2.startAnimation ( AnimationUtils.loadAnimation ( getActivity (),R.anim.transparantbut ) );
                        startActivity ( new Intent ( getActivity (), GameTwoActivity.class ));
                        break;
                    case R.id.game_three_button:
                        c3.startAnimation ( AnimationUtils.loadAnimation ( getActivity (),R.anim.transparantbut ) );
                        startActivity ( new Intent ( getActivity (), GameThreeActivity.class ));
                        break;
                    case R.id.game_five_button:
                        c4.startAnimation ( AnimationUtils.loadAnimation ( getActivity (),R.anim.transparantbut ) );
                        startActivity ( new Intent ( getActivity (),GameFiveActivity.class ) );
                        break;
                }
            }
        };

        game1.setOnClickListener ( onClickListener );
        game2.setOnClickListener ( onClickListener );
        game3.setOnClickListener ( onClickListener );
        game5.setOnClickListener ( onClickListener );

        return view;
    }
}
