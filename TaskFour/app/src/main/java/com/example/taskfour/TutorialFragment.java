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
public class TutorialFragment extends Fragment {

    private Button button1,button2,button3;
    private MediaPlayer mediaPlayer;
    private RelativeLayout c1,c2,c3;

    public TutorialFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView( LayoutInflater inflater , ViewGroup container ,
                              Bundle savedInstanceState ) {
        // Inflate the layout for this fragment
        View view = inflater.inflate ( R.layout.fragment_tutorial , container , false );
        button1 = view.findViewById ( R.id.alphabet_tutorial_button );
        button2 = view.findViewById ( R.id.drawing_tutorial_button );
        button3 = view.findViewById ( R.id.numbers_tutorial_button );

        c1 = view.findViewById ( R.id.c1 );
        c2 = view.findViewById ( R.id.c2 );
        c3 = view.findViewById ( R.id.c3 );

        mediaPlayer = new MediaPlayer ().create(getActivity (),R.raw.rubberone);

        View.OnClickListener onClickListener = new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                mediaPlayer.start ();
                Intent intent = new Intent ( getContext (),TutorialActivity.class );
                switch (v.getId ()){
                    case R.id.alphabet_tutorial_button:
                        c1.startAnimation ( AnimationUtils.loadAnimation ( getActivity (),R.anim.transparantbut ) );
                        intent.putExtra ( "tut",1 );
                        break;
                    case R.id.drawing_tutorial_button:
                        c2.startAnimation ( AnimationUtils.loadAnimation ( getActivity (),R.anim.transparantbut ) );
                        intent.putExtra ( "tut",2 );
                        break;
                    case R.id.numbers_tutorial_button:
                        c3.startAnimation ( AnimationUtils.loadAnimation ( getActivity (),R.anim.transparantbut ) );
                        intent.putExtra ( "tut",3 );
                        break;
                }
                getContext ().startActivity ( intent );
            }
        };

        button1.setOnClickListener ( onClickListener );
        button2.setOnClickListener ( onClickListener );
        button3.setOnClickListener ( onClickListener );

        return view;
    }
}