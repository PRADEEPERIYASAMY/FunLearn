package com.example.taskfour;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.taskfour.UserAccount.Profile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;

public class ProfileActivity extends AppCompatActivity {

    private ImageView profileImage;
    private MaterialEditText profileName,profileAge,profileInterest,profileSchool;
    private Button profileUpdate;
    private DatabaseReference profRef;
    private FirebaseAuth mAuth;
    private static String url = null;
    private MediaPlayer mediaPlayer,mediaPlayer1;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        requestWindowFeature( Window.FEATURE_NO_TITLE);
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        final View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        setContentView ( R.layout.activity_profile );
        mediaPlayer = new MediaPlayer ().create(getApplicationContext (),R.raw.negative);
        mediaPlayer1 = new MediaPlayer ().create(getApplicationContext (),R.raw.rubberone);
        mAuth = FirebaseAuth.getInstance ();
        profRef = FirebaseDatabase.getInstance ().getReference ().child ( "Profile" ).child ( mAuth.getCurrentUser ().getUid () );

        profileImage = findViewById ( R.id.profile_image );
        profileName = findViewById ( R.id.profile_name );
        profileAge = findViewById ( R.id.profile_age );
        profileInterest = findViewById ( R.id.profile_interest );
        profileSchool = findViewById ( R.id.profile_school_name );
        profileUpdate = findViewById ( R.id.update_profile_button );

        profileImage.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                Intent intent = new Intent (  );
                intent.setAction ( Intent.ACTION_GET_CONTENT );
                intent.setType("image/*");
                startActivityForResult ( intent ,1001 );
            }
        } );

        RetrieveUserInfo ();

        profileUpdate.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                profRef.addListenerForSingleValueEvent ( new ValueEventListener () {
                    @Override
                    public void onDataChange( @NonNull DataSnapshot dataSnapshot ) {
                        HashMap<String, Object> profile = new HashMap<>();
                        profile.put ("ProfileName", profileName.getText ().toString () );
                        profile.put ("Age", profileAge.getText ().toString () );
                        profile.put ("Interest", profileInterest.getText ().toString () );
                        profile.put ("School", profileSchool.getText ().toString () );
                        if (url != null){
                            profile.put ("Image", String.valueOf ( url ) );
                        }
                        profRef.updateChildren ( profile ).addOnCompleteListener ( new OnCompleteListener<Void> () {
                            @Override
                            public void onComplete( @NonNull Task<Void> task ) {
                                if (task.isSuccessful ()){
                                    View view = LayoutInflater.from ( ProfileActivity.this ).inflate ( R.layout.toast,null,false );
                                    TextView textView = view.findViewById ( R.id.toast_text );
                                    textView.setText ( "Updated" );
                                    Toast toast = new Toast ( getApplicationContext () );
                                    toast.setView ( view );
                                    toast.show ();
                                    RetrieveUserInfo ();
                                }
                            }
                        } );
                    }

                    @Override
                    public void onCancelled( @NonNull DatabaseError databaseError ) {

                    }
                } );
            }
        } );

        final Button info = findViewById ( R.id.info );
        info.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                mediaPlayer.start ();
                final AlertDialog.Builder builder = new AlertDialog.Builder ( ProfileActivity.this,R.style.CustomDialog );
                View view = LayoutInflater.from ( ProfileActivity.this ).inflate ( R.layout.about_dialog,null,false );

                Button close = view.findViewById ( R.id.info_close );
                TextView infoText = view.findViewById ( R.id.info_text );

                infoText.setText ( "User are requested to fill the profile because it will be exposed to other users of the app in chat box." );

                final AlertDialog alertDialog = builder.create ();
                alertDialog.setView ( view );

                close.setOnClickListener ( new View.OnClickListener () {
                    @Override
                    public void onClick( View v ) {
                        mediaPlayer1.start ();
                        alertDialog.cancel ();
                        full ();
                    }
                } );

                alertDialog.setCanceledOnTouchOutside ( false );
                alertDialog.show ();
            }
        } );

        Button back = findViewById ( R.id.back );
        back.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                exit ();
            }
        } );

    }

    private void RetrieveUserInfo()
    {
        profRef.addValueEventListener(new ValueEventListener () {
            @Override
            public void onDataChange( DataSnapshot dataSnapshot)
            {
                if ((dataSnapshot.exists())  &&  (dataSnapshot.hasChild("Image")))
                {
                    profileName.setText ( dataSnapshot.child ( "ProfileName" ).getValue ().toString () );
                    profileAge.setText ( dataSnapshot.child ( "Age" ).getValue ().toString () );
                    profileInterest.setText ( dataSnapshot.child ( "Interest" ).getValue ().toString () );
                    profileSchool.setText ( dataSnapshot.child ( "School" ).getValue ().toString () );
                    Glide.with ( ProfileActivity.this ).load ( Uri.parse ( dataSnapshot.child ( "Image" ).getValue ().toString () ) ).into ( profileImage );
                }
                else if (dataSnapshot.exists ())
                {
                   profileName.setText ( dataSnapshot.child ( "ProfileName" ).getValue ().toString () );
                   profileAge.setText ( dataSnapshot.child ( "Age" ).getValue ().toString () );
                   profileInterest.setText ( dataSnapshot.child ( "Interest" ).getValue ().toString () );
                   profileSchool.setText ( dataSnapshot.child ( "School" ).getValue ().toString () );
                }

            }

            @Override
            public void onCancelled( DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onActivityResult( int requestCode , int resultCode , @Nullable Intent data ) {
        super.onActivityResult ( requestCode , resultCode , data );
        if (requestCode == 1001 && resultCode == RESULT_OK && data != null){
            Uri profileUrl = data.getData ();
            CropImage.activity(profileUrl)
                    .setGuidelines( CropImageView.Guidelines.ON)
                    .setAspectRatio(1, 1)
                    .start(this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE ){
            CropImage.ActivityResult profileUrl = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK){
                profileImage.setImageURI ( profileUrl.getUri () );
                StorageReference storageReference = FirebaseStorage.getInstance ().getReference ();
                storageReference.child ( "profileImage" ).child ( mAuth.getCurrentUser ().getUid ()+".jpg" )
                        .putFile ( profileUrl.getUri () )
                        .addOnCompleteListener ( new OnCompleteListener<UploadTask.TaskSnapshot> () {
                            @Override
                            public void onComplete( @NonNull Task<UploadTask.TaskSnapshot> task ) {
                                try {
                                    Task<Uri> result = task.getResult ().getMetadata().getReference().getDownloadUrl();
                                    result.addOnSuccessListener(new OnSuccessListener<Uri> () {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            url = uri.toString();
                                        }
                                    });
                                }
                                catch (Exception e){
                                    e.printStackTrace ();
                                }
                            }
                        } );
            }
        }
    }

    private void full(){
        final View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }
    @Override
    protected void onPause() {
        super.onPause ();
        Main2Activity.mediaPlayer.pause ();
        overridePendingTransition ( android.R.anim.fade_in,android.R.anim.fade_out );
    }

    @Override
    protected void onResume() {
        super.onResume ();
        Main2Activity.mediaPlayer.start ();
        full ();
    }

    @Override
    public void onBackPressed() {
        exit ();
    }

    private void exit(){
        mediaPlayer.start ();
        final AlertDialog.Builder builder = new AlertDialog.Builder ( ProfileActivity.this,R.style.CustomDialog );
        View view = LayoutInflater.from ( ProfileActivity.this ).inflate ( R.layout.alphabet_write_dialog,null,false );
        Button yes = view.findViewById ( R.id.yes );
        Button no = view.findViewById ( R.id.no );
        TextView firstText = view.findViewById ( R.id.firstText );
        TextView secondText = view.findViewById ( R.id.secontText );
        ImageView dialogImage = view.findViewById ( R.id.dialog_image );

        firstText.setText ( "Oh noooo !" );
        secondText.setText ( "Do you wanna exit ?" );
        dialogImage.setImageResource ( R.drawable.oh );
        final AlertDialog alertDialog = builder.create ();
        alertDialog.setView ( view );

        no.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                mediaPlayer1.start ();
                alertDialog.cancel ();
                full ();
            }
        } );

        yes.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                mediaPlayer1.start ();
                finish ();
            }
        } );
        alertDialog.setCanceledOnTouchOutside ( false );
        alertDialog.show ();
    }
}