package com.example.taskfour.Adapters;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskfour.AlphabetInfoActivity;
import com.example.taskfour.AlphabetItemActivity;
import com.example.taskfour.R;
import com.example.taskfour.alphabets.ListOfAlphabets;

public class AlphabetAdapters extends RecyclerView.Adapter<AlphabetAdapters.MyViewHolder> {

    Context context;
    MediaPlayer mediaPlayer;

    public AlphabetAdapters( Context context ) {
        this.context = context;
    }

    @NonNull
    @Override
    public AlphabetAdapters.MyViewHolder onCreateViewHolder( @NonNull ViewGroup parent, int viewType ) {

        View itemView = LayoutInflater.from ( context ).inflate ( R.layout.alphabet_item,parent,false);
        return new MyViewHolder ( itemView );
    }

    @Override
    public void onBindViewHolder( @NonNull AlphabetAdapters.MyViewHolder holder, int position ) {
        holder.alphabetCaps.setText ( String.valueOf ( ListOfAlphabets.alphabets[position] ).toUpperCase () );
        holder.alphabetSmall.setText ( String.valueOf ( ListOfAlphabets.alphabets[position] ).toLowerCase () );
        holder.alphabetCount.setText ( String.valueOf ( position+1 ) );
    }

    @Override
    public int getItemCount() {
        return 26;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

        TextView alphabetCaps,alphabetSmall,alphabetCount;

        public MyViewHolder( @NonNull View itemView ) {
            super ( itemView );
            alphabetCaps = itemView.findViewById ( R.id.alphabet_caps );
            alphabetSmall = itemView.findViewById ( R.id.alphabet_small );
            alphabetCount = itemView.findViewById ( R.id.alphabet_count );

            itemView.setOnClickListener (this );
            mediaPlayer = new MediaPlayer ().create(context,R.raw.rubberone);
        }

        @Override
        public void onClick( View v ) {
            mediaPlayer.start ();
            itemView.startAnimation ( AnimationUtils.loadAnimation ( context,R.anim.transparantbut ) );
            Intent intent = new Intent ( context, AlphabetInfoActivity.class );
            intent.putExtra ( "id",getAdapterPosition () );
            intent.setFlags ( Intent.FLAG_ACTIVITY_NEW_TASK );
            context.startActivity ( intent );
        }
    }
}
