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
public class PaintingFragments extends Fragment {

    private Button b1,b2,b3;
    private MediaPlayer mediaPlayer;
    private RelativeLayout c1,c2,c3;

    public PaintingFragments() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState ) {
        // Inflate the layout for this fragment
        View view = inflater.inflate ( R.layout.fragment_painting_fragments, container, false );

        b1 = view.findViewById ( R.id.painting_button );
        b2 = view.findViewById ( R.id.coloring_button );
        b3 = view.findViewById ( R.id.pattern_button );

        c1 = view.findViewById ( R.id.c1 );
        c2 = view.findViewById ( R.id.c2 );
        c3 = view.findViewById ( R.id.c3 );

        mediaPlayer = new MediaPlayer ().create(getActivity (),R.raw.rubberone);

        View.OnClickListener onClickListener = new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                mediaPlayer.start ();
                switch (v.getId ()){
                    case R.id.painting_button:
                        c1.startAnimation ( AnimationUtils.loadAnimation ( getActivity (),R.anim.transparantbut ) );
                        startActivity ( new Intent ( getActivity (),PaintingOneActivity.class ) );
                        break;
                    case R.id.coloring_button:
                        c2.startAnimation ( AnimationUtils.loadAnimation ( getActivity (),R.anim.transparantbut ) );
                        startActivity ( new Intent ( getActivity (),ColouringListActivity.class ) );
                        break;
                    case R.id.pattern_button:
                        c3.startAnimation ( AnimationUtils.loadAnimation ( getActivity (),R.anim.transparantbut ) );
                        startActivity ( new Intent ( getActivity (),PaintingThreeActivity.class ) );
                        break;
                }
            }
        };

        b1.setOnClickListener ( onClickListener );
        b2.setOnClickListener ( onClickListener );
        b3.setOnClickListener ( onClickListener );

        return view;
    }
}
