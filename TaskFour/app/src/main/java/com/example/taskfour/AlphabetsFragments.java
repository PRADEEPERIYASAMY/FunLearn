package com.example.taskfour;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class AlphabetsFragments extends Fragment {

    private Button alphabet_item_activity,alphabet_write_activity,alphabet_word_activity,alphabet_match_activity,alphabet_count_activity;
    private MediaPlayer mediaPlayer;
    private RelativeLayout c1,c2,c3,c4,c5;

    public AlphabetsFragments() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState ) {
        View view = inflater.inflate ( R.layout.fragment_alphabets_fragments, container, false );
        alphabet_item_activity = view.findViewById ( R.id.alphabet_item_activity_button );
        alphabet_write_activity = view.findViewById ( R.id.alphabet_write_activity_button );
        alphabet_word_activity = view.findViewById ( R.id.alphabet_word_activity_button );
        alphabet_match_activity = view.findViewById ( R.id.alphabet_match_activity_button );
        alphabet_count_activity = view.findViewById ( R.id.alphabet_count_activity_button );
        mediaPlayer = new MediaPlayer ().create (getActivity (),R.raw.rubberone);
        mediaPlayer.setVolume ( 5f,5f );
        c1 = view.findViewById ( R.id.c1 );
        c2 = view.findViewById ( R.id.c2 );
        c3 = view.findViewById ( R.id.c3 );
        c4 = view.findViewById ( R.id.c4 );
        c5 = view.findViewById ( R.id.c5 );

        View.OnClickListener onClickListener = new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                mediaPlayer.start ();
                switch (v.getId ()){
                    case R.id.alphabet_item_activity_button:
                        c1.startAnimation ( AnimationUtils.loadAnimation ( getContext (),R.anim.transparantbut ) );
                        startActivity ( new Intent ( getActivity (),AlphabetItemActivity.class ));
                        break;
                    case R.id.alphabet_write_activity_button:
                        c2.startAnimation ( AnimationUtils.loadAnimation ( getContext (),R.anim.transparantbut ) );
                        startActivity ( new Intent ( getActivity (),AlphabetWriteAcitivity.class ));
                        break;
                    case R.id.alphabet_word_activity_button:
                        c3.startAnimation ( AnimationUtils.loadAnimation ( getContext (),R.anim.transparantbut ) );
                        startActivity ( new Intent ( getActivity (),AlphabetWordsActivity.class ));
                        break;
                    case R.id.alphabet_match_activity_button:
                        c4.startAnimation ( AnimationUtils.loadAnimation ( getContext (),R.anim.transparantbut ) );
                        startActivity ( new Intent ( getActivity (),AlphabetMatchActivity.class ));
                        break;
                    case R.id.alphabet_count_activity_button:
                        c5.startAnimation ( AnimationUtils.loadAnimation ( getContext (),R.anim.transparantbut ) );
                        startActivity ( new Intent ( getActivity (),AlphabetCountActivity.class ));
                        break;
                }
            }
        };

        alphabet_item_activity.setOnClickListener ( onClickListener );
        alphabet_write_activity.setOnClickListener ( onClickListener );
        alphabet_word_activity.setOnClickListener ( onClickListener );
        alphabet_match_activity.setOnClickListener ( onClickListener );
        alphabet_count_activity.setOnClickListener ( onClickListener );

        return view;
    }
}
