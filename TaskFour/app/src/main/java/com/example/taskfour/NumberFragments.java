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

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class NumberFragments extends Fragment {

    private Button addition,subtraction,multiplication,division,count,write,info,find;
    private MediaPlayer mediaPlayer;
    private RelativeLayout c1,c2,c3,c4,c5,c6,c7,c8;

    public NumberFragments() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView( LayoutInflater inflater, final ViewGroup container,
                              Bundle savedInstanceState ) {
        // Inflate the layout for this fragment
        View view = inflater.inflate ( R.layout.fragment_number_fragments, container, false );

        addition = view.findViewById ( R.id.addition_fragment_button );
        subtraction = view.findViewById ( R.id.subtraction_fragment_button );
        multiplication = view.findViewById ( R.id.multiplication_fragment_button );
        division = view.findViewById ( R.id.division_fragment_button );
        count = view.findViewById ( R.id.count_fragment_button );
        write = view.findViewById ( R.id.write_fragment_button );
        info = view.findViewById ( R.id.info_fragment_button );
        find = view.findViewById ( R.id.find_fragment_button );
        mediaPlayer = new MediaPlayer ().create(getActivity (),R.raw.rubberone);

        c1 = view.findViewById ( R.id.c1 );
        c2 = view.findViewById ( R.id.c2 );
        c3 = view.findViewById ( R.id.c3 );
        c4 = view.findViewById ( R.id.c4 );
        c5 = view.findViewById ( R.id.c5 );
        c6 = view.findViewById ( R.id.c6 );
        c7 = view.findViewById ( R.id.c7 );
        c8 = view.findViewById ( R.id.c8 );

        View.OnClickListener onClickListener = new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                mediaPlayer.start ();
                switch (v.getId ()){
                    case R.id.info_fragment_button:
                        c1.startAnimation ( AnimationUtils.loadAnimation ( getActivity (),R.anim.transparantbut ) );
                        startActivity ( new Intent ( getActivity (),NumberInfoActivity.class ) );
                        break;
                    case R.id.write_fragment_button:
                        c2.startAnimation ( AnimationUtils.loadAnimation ( getActivity (),R.anim.transparantbut ) );
                        startActivity ( new Intent ( getActivity (),NumberWriteActivity.class ) );
                        break;
                    case R.id.count_fragment_button:
                        c3.startAnimation ( AnimationUtils.loadAnimation ( getActivity (),R.anim.transparantbut ) );
                        startActivity ( new Intent ( getActivity (),CountingActivity.class ) );
                        break;
                    case R.id.addition_fragment_button:
                        c4.startAnimation ( AnimationUtils.loadAnimation ( getActivity (),R.anim.transparantbut ) );
                        startActivity ( new Intent ( getActivity (),AdditionActivity.class ) );
                        break;
                    case R.id.subtraction_fragment_button:
                        c5.startAnimation ( AnimationUtils.loadAnimation ( getActivity (),R.anim.transparantbut ) );
                        startActivity ( new Intent ( getActivity (),SubtractionActivity.class ) );
                        break;
                    case R.id.multiplication_fragment_button:
                        c6.startAnimation ( AnimationUtils.loadAnimation ( getActivity (),R.anim.transparantbut ) );
                        startActivity ( new Intent ( getActivity (),MultiplicationActivity.class ) );
                        break;
                    case R.id.division_fragment_button:
                        c7.startAnimation ( AnimationUtils.loadAnimation ( getActivity (),R.anim.transparantbut ) );
                        startActivity ( new Intent ( getActivity (),DivisionActivity.class ) );
                        break;
                    case R.id.find_fragment_button:
                        c8.startAnimation ( AnimationUtils.loadAnimation ( getActivity (),R.anim.transparantbut ) );
                        startActivity ( new Intent ( getActivity (),NumberFillup.class ) );
                        break;
                }
            }
        };

        addition.setOnClickListener ( onClickListener );
        subtraction.setOnClickListener ( onClickListener );
        multiplication.setOnClickListener ( onClickListener );
        division.setOnClickListener ( onClickListener );
        count.setOnClickListener ( onClickListener );
        write.setOnClickListener ( onClickListener );
        info.setOnClickListener ( onClickListener );
        find.setOnClickListener ( onClickListener );

        return view;
    }
}
