package com.example.taskfour.Adapters;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.taskfour.ChatActivity;
import com.example.taskfour.R;
import com.example.taskfour.UserAccount.Profile;
import com.example.taskfour.UserAccount.Question;
import com.example.taskfour.AnswerActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.MyViewHolder> {

    List<Question> questionList;

    public QuestionAdapter( List<Question> questionList ) {
        this.questionList = questionList;
    }

    @NonNull
    @Override
    public QuestionAdapter.MyViewHolder onCreateViewHolder( @NonNull ViewGroup parent , int viewType ) {
        View view = LayoutInflater.from ( parent.getContext () ).inflate ( R.layout.question_item,parent,false );
        return new MyViewHolder ( view );
    }

    @Override
    public void onBindViewHolder( @NonNull final QuestionAdapter.MyViewHolder holder , final int position ) {
        holder.name.setText ( String.valueOf ( questionList.get ( position ).getName () ) );
        holder.dateTime.setText ( String.valueOf ( questionList.get ( position ).getDate ()+"\n"+questionList.get ( position ).getTime () ) );
        holder.question.setText ( String.valueOf ( questionList.get ( position ).getMessage () ) );

        try {
            Glide.with ( holder.itemView.getContext () ).load ( Uri.parse ( questionList.get ( position ).getImage () ) ).into ( holder.userImage );
        }
        catch (NullPointerException e){
            Glide.with ( holder.itemView.getContext () ).load ( R.drawable.usernull ).into ( holder.userImage );
        }
    }

    @Override
    public int getItemCount() {
        return questionList.size ();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,dateTime,question;
        ImageView userImage;
        Button answerButton;
        DatabaseReference profRef;
        public MyViewHolder( @NonNull final View itemView ) {
            super ( itemView );
            name = itemView.findViewById ( R.id.user_name );
            dateTime = itemView.findViewById ( R.id.time_uploaded );
            question = itemView.findViewById ( R.id.user_question );
            userImage = itemView.findViewById ( R.id.user_image );
            answerButton = itemView.findViewById ( R.id.answer );
            final MediaPlayer mediaPlayer1 = new MediaPlayer ().create(itemView.getContext (),R.raw.rubberone);
            final MediaPlayer mediaPlayer2 = new MediaPlayer ().create(itemView.getContext (),R.raw.negative);
            answerButton.setOnClickListener ( new View.OnClickListener () {
                @Override
                public void onClick( View v ) {
                    mediaPlayer1.start ();
                    answerButton.startAnimation ( AnimationUtils.loadAnimation ( itemView.getContext (),R.anim.transparantbut ) );
                    Intent intent = new Intent ( itemView.getContext (), AnswerActivity.class );
                    intent.putExtra ( "name",questionList.get ( getAdapterPosition () ).getName () );
                    intent.putExtra ( "date",questionList.get ( getAdapterPosition () ).getDate () );
                    intent.putExtra ( "time",questionList.get ( getAdapterPosition () ).getTime () );
                    intent.putExtra ( "image",questionList.get ( getAdapterPosition () ).getImage () );
                    intent.putExtra ( "question",questionList.get ( getAdapterPosition () ).getMessage () );
                    intent.putExtra ( "position",String.valueOf ( getAdapterPosition ()+1 ) );
                    intent.setFlags ( Intent.FLAG_ACTIVITY_NEW_TASK );
                    itemView.getContext ().startActivity ( intent );
                }
            } );
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
                                    alertDialog.cancel ();
                                    mediaPlayer1.start ();
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
