package com.example.taskfour.Adapters;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.taskfour.AnswerActivity;
import com.example.taskfour.R;
import com.example.taskfour.UserAccount.Profile;
import com.example.taskfour.UserAccount.Question;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.MyViewHolder> {

    List<Question> questionList;

    public AnswerAdapter( List<Question> questionList ) {
        this.questionList = questionList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder( @NonNull ViewGroup parent , int viewType ) {
        View view = LayoutInflater.from ( parent.getContext () ).inflate ( R.layout.answer_item,parent,false );
        return new MyViewHolder ( view );
    }

    @Override
    public void onBindViewHolder( @NonNull final MyViewHolder holder , final int position ) {
        holder.name.setText ( String.valueOf ( questionList.get ( position ).getName () ) );
        holder.dateTime.setText ( String.valueOf ( questionList.get ( position ).getDate ()+"\n"+questionList.get ( position ).getTime () ) );
        if (questionList.get ( position ).getType ().equals ( "text" )){
            holder.question.setVisibility ( View.VISIBLE );
            holder.answer_image.setVisibility ( View.GONE );
            holder.question.setText ( String.valueOf ( questionList.get ( position ).getMessage () ) );
        }
        else if (questionList.get ( position ).getType ().equals ( "jpg" )){
            holder.question.setVisibility ( View.GONE );
            holder.answer_image.setVisibility ( View.VISIBLE );
            Glide.with ( holder.itemView.getContext () ).load ( questionList.get ( position ).getMessage ().toString () ).into ( holder.answer_image );
        }
        else if (questionList.get ( position ).getType ().equals ( "pdf" ) || questionList.get ( position ).getType ().equals ( "docx" )){
            holder.question.setVisibility ( View.GONE );
            holder.answer_image.setVisibility ( View.VISIBLE );
            if (questionList.get ( position ).getType ().equals ( "pdf" )){
                Glide.with ( holder.itemView.getContext () ).load ( R.drawable.pdficon ).into ( holder.answer_image );
            }
            else {
                Glide.with ( holder.itemView.getContext () ).load ( R.drawable.docxicon ).into ( holder.answer_image );
            }
        }
        try {
            Glide.with ( holder.itemView.getContext () ).load ( Uri.parse ( questionList.get ( position ).getImage () ) ).into ( holder.userImage );
        }
        catch (NullPointerException e){
            Glide.with ( holder.itemView.getContext () ).load ( R.drawable.usernull ).into ( holder.userImage );
        }

        holder.answerLayout.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                if (questionList.get ( position ).getType ().equals ( "jpg" )){
                    Intent intent = new Intent ( Intent.ACTION_VIEW, Uri.parse ( questionList.get ( position ).getMessage () ) );
                    holder.itemView.getContext ().startActivity ( intent );
                }
                else if (questionList.get ( position ).getType ().equals ( "pdf" ) || questionList.get ( position ).getType ().equals ( "docx" )){
                    Intent intent = new Intent ( Intent.ACTION_VIEW, Uri.parse ( questionList.get ( position ).getMessage () ) );
                    holder.itemView.getContext ().startActivity ( intent );
                }

            }
        } );
    }

    @Override
    public int getItemCount() {
        return questionList.size ();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,dateTime,question;
        ImageView userImage,answer_image;
        Button answerButton;
        LinearLayout answerLayout;
        DatabaseReference profRef;
        public MyViewHolder( @NonNull final View itemView ) {
            super ( itemView );
            name = itemView.findViewById ( R.id.user_name );
            dateTime = itemView.findViewById ( R.id.time_uploaded );
            question = itemView.findViewById ( R.id.user_question );
            userImage = itemView.findViewById ( R.id.user_image );
            answer_image = itemView.findViewById ( R.id.answer_image );
            answerButton = itemView.findViewById ( R.id.answer );
            answerLayout = itemView.findViewById ( R.id.question_layout );

            final MediaPlayer mediaPlayer1 = new MediaPlayer ().create(itemView.getContext (),R.raw.rubberone);
            final MediaPlayer mediaPlayer2 = new MediaPlayer ().create(itemView.getContext (),R.raw.negative);

            userImage.setOnClickListener ( new View.OnClickListener () {
                @Override
                public void onClick( View v ) {
                    mediaPlayer2.start ();
                    profRef = FirebaseDatabase.getInstance ().getReference ().child ( "Profile" ).child ( questionList.get ( getAdapterPosition () ).getId () );
                    profRef.addListenerForSingleValueEvent ( new ValueEventListener () {
                        @Override
                        public void onDataChange( @NonNull DataSnapshot dataSnapshot ) {
                            Profile profile = dataSnapshot.getValue (Profile.class);
                            final AlertDialog.Builder builder = new AlertDialog.Builder ( itemView.getContext (),R.style.CustomDialog );
                            View view = LayoutInflater.from ( itemView.getContext () ).inflate ( R.layout.view_profile_dialog,null,false );

                            Button close = view.findViewById ( R.id.info_close );
                            TextView infoText = view.findViewById ( R.id.user_text );
                            ImageView image = view.findViewById ( R.id.image );

                            try {
                                Glide.with ( itemView.getContext () ).load ( profile.getImage () ).into ( image );
                            }
                            catch (NullPointerException e){
                                Glide.with ( itemView.getContext () ).load ( R.drawable.usernull ).into ( image );
                            }
                            infoText.setText ( "User Name : "+profile.getProfileName ()+"\n\n"+"Age : "+profile.getAge ()+"\n\n"+"Interest : "+profile.getInterest ()+"\n\n"+"School : "+profile.getSchool () );

                            final AlertDialog alertDialog = builder.create ();
                            alertDialog.setView ( view );

                            close.setOnClickListener ( new View.OnClickListener () {
                                @Override
                                public void onClick( View v ) {
                                    mediaPlayer1.start ();
                                    alertDialog.cancel ();
                                }
                            } );

                            alertDialog.setCanceledOnTouchOutside ( false );
                            alertDialog.show ();
                        }

                        @Override
                        public void onCancelled( @NonNull DatabaseError databaseError ) {

                        }
                    } );
                }
            } );
        }
    }
}

