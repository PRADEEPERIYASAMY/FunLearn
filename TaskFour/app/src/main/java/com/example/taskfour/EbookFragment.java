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
public class EbookFragment extends Fragment {

    private Button storyButton,alphabetsButton,comicButton,numbersButton;
    private MediaPlayer mediaPlayer;
    private RelativeLayout c1,c2,c3,c4;

    public EbookFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView( LayoutInflater inflater , ViewGroup container ,
                              Bundle savedInstanceState ) {
        View view = inflater.inflate ( R.layout.fragment_ebook , container , false );

        storyButton = view.findViewById ( R.id.ebook_button_story );
        alphabetsButton = view.findViewById ( R.id.ebook_button_alphabets );
        comicButton = view.findViewById ( R.id.ebook_button_comics );
        numbersButton = view.findViewById ( R.id.ebook_button_numbers );

        c1 = view.findViewById ( R.id.c1 );
        c2 = view.findViewById ( R.id.c2 );
        c3 = view.findViewById ( R.id.c3 );
        c4 = view.findViewById ( R.id.c4 );

        mediaPlayer = new MediaPlayer ().create(getActivity (),R.raw.rubberone);

        View.OnClickListener onClickListener = new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                mediaPlayer.start ();
                Intent intent = new Intent ( getContext (),PdfListActivity.class );
                switch (v.getId ()){
                    case R.id.ebook_button_story:
                        c1.startAnimation ( AnimationUtils.loadAnimation ( getActivity (),R.anim.transparantbut ) );
                        intent.putExtra ( "type","1" );
                        break;
                    case R.id.ebook_button_alphabets:
                        c2.startAnimation ( AnimationUtils.loadAnimation ( getActivity (),R.anim.transparantbut ) );
                        intent.putExtra ( "type","2" );
                        break;
                    case R.id.ebook_button_comics:
                        c3.startAnimation ( AnimationUtils.loadAnimation ( getActivity (),R.anim.transparantbut ) );
                        intent.putExtra ( "type","3" );
                        break;
                    case R.id.ebook_button_numbers:
                        c4.startAnimation ( AnimationUtils.loadAnimation ( getActivity (),R.anim.transparantbut ) );
                        intent.putExtra ( "type","4" );
                        break;
                }
                getContext ().startActivity ( intent );
            }
        };

        storyButton.setOnClickListener ( onClickListener );
        alphabetsButton.setOnClickListener ( onClickListener );
        comicButton.setOnClickListener ( onClickListener );
        numbersButton.setOnClickListener ( onClickListener );

        return view;
    }
}