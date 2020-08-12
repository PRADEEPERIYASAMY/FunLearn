package com.example.taskfour;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taskfour.UserAccount.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SignInActivity extends AppCompatActivity {

    private MaterialEditText userEmailId,userPassword;
    private MaterialEditText newUserName,newUserPassword,newUserEmail;
    private Button signInButton,signUpButton,create,cancel;
    private FirebaseAuth mAuth;
    private DatabaseReference rootRef;
    private String userEmailValue,userPasswordValue;
    private static final int PERMISSION_REQUEST_CODE = 1000;
    private MediaPlayer mediaPlayer1,mediaPlayer;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        requestWindowFeature( Window.FEATURE_NO_TITLE);
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        setContentView ( R.layout.activity_sign_in );
        mediaPlayer = new MediaPlayer ().create(getApplicationContext (),R.raw.negative);
        mediaPlayer1 = new MediaPlayer ().create(getApplicationContext (),R.raw.rubberone);

        if (ActivityCompat.checkSelfPermission ( getApplicationContext (), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ){
            requestPermissions ( new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},PERMISSION_REQUEST_CODE );
        }
        if (ActivityCompat.checkSelfPermission(getApplicationContext (), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions( new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }

        mAuth = FirebaseAuth.getInstance ();
        rootRef = FirebaseDatabase.getInstance ().getReference ().child ( "Users" );

        userEmailId = findViewById ( R.id.user_email );
        userPassword = findViewById ( R.id.user_password );
        signInButton = findViewById ( R.id.sign_in );
        signUpButton = findViewById ( R.id.sign_up );

        signInButton.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                mediaPlayer1.start ();
                userEmailValue = userEmailId.getText ().toString ();
                userPasswordValue = userPassword.getText ().toString ();

                rootRef.addListenerForSingleValueEvent ( new ValueEventListener () {
                    @Override
                    public void onDataChange( @NonNull DataSnapshot dataSnapshot ) {
                        if ( TextUtils.isEmpty ( userEmailValue ) || TextUtils.isEmpty ( userPasswordValue ) ){
                            if (TextUtils.isEmpty ( userEmailValue )){
                                View view = LayoutInflater.from ( SignInActivity.this ).inflate ( R.layout.toast,null,false );
                                TextView textView = view.findViewById ( R.id.toast_text );
                                textView.setText ( "Fill username" );
                                Toast toast = new Toast ( getApplicationContext () );
                                toast.setView ( view );
                                toast.show ();
                            }
                            else {
                                View view = LayoutInflater.from ( SignInActivity.this ).inflate ( R.layout.toast,null,false );
                                TextView textView = view.findViewById ( R.id.toast_text );
                                textView.setText ( "Fill password" );
                                Toast toast = new Toast ( getApplicationContext () );
                                toast.setView ( view );
                                toast.show ();
                            }
                        }
                        else {
                            mAuth.signInWithEmailAndPassword ( userEmailValue, userPasswordValue )
                                    .addOnCompleteListener ( new OnCompleteListener<AuthResult> () {
                                @Override
                                public void onComplete( @NonNull Task<AuthResult> task ) {
                                    if (task.isSuccessful ()){
                                        Intent intent = new Intent ( getApplicationContext (),Main2Activity.class );
                                        intent.setFlags ( Intent.FLAG_ACTIVITY_CLEAR_TOP );
                                        startActivity ( intent );
                                        finish ();
                                    }
                                    else {
                                        View view = LayoutInflater.from ( SignInActivity.this ).inflate ( R.layout.toast,null,false );
                                        TextView textView = view.findViewById ( R.id.toast_text );
                                        textView.setText ( "Not a user" );
                                        Toast toast = new Toast ( getApplicationContext () );
                                        toast.setView ( view );
                                        toast.show ();
                                    }
                                }
                            } );
                        }
                    }

                    @Override
                    public void onCancelled( @NonNull DatabaseError databaseError ) {

                    }
                } );

            }
        } );

        signUpButton.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick( View v ) {
                mediaPlayer.start ();
                AlertDialog.Builder registerForm = new AlertDialog.Builder ( SignInActivity.this,R.style.CustomDialog );
                View view = LayoutInflater.from ( SignInActivity.this ).inflate ( R.layout.login_dialog,null,false );
                newUserName = view.findViewById ( R.id.new_user_name );
                newUserPassword = view.findViewById ( R.id.new_user_password );
                newUserEmail = view.findViewById ( R.id.new_user_email );
                create = view.findViewById ( R.id.yes );
                cancel = view.findViewById ( R.id.no );
                final AlertDialog alertDialog = registerForm.create ();
                alertDialog.setView ( view );

                cancel.setOnClickListener ( new View.OnClickListener () {
                    @Override
                    public void onClick( View v ) {
                        mediaPlayer1.start ();
                        alertDialog.dismiss ();
                    }
                } );

                create.setOnClickListener ( new View.OnClickListener () {
                    @Override
                    public void onClick( View v ) {
                        mediaPlayer1.start ();
                        alertDialog.dismiss ();
                        final String newUserNameValue,newUserPasswordValue,newUserEmailValue;
                        newUserNameValue = newUserName.getText ().toString ();
                        newUserPasswordValue = newUserPassword.getText ().toString ();
                        newUserEmailValue = newUserEmail.getText ().toString ();

                        final Users newUser = new Users ( newUserNameValue,newUserPasswordValue,newUserEmailValue );

                        if (TextUtils.isEmpty ( newUserNameValue ) || TextUtils.isEmpty ( newUserPasswordValue ) || TextUtils.isEmpty ( newUserEmailValue )){
                            if (TextUtils.isEmpty ( newUserNameValue )){
                                View view = LayoutInflater.from ( SignInActivity.this ).inflate ( R.layout.toast,null,false );
                                TextView textView = view.findViewById ( R.id.toast_text );
                                textView.setText ( "Fill username" );
                                Toast toast = new Toast ( getApplicationContext () );
                                toast.setView ( view );
                                toast.show ();
                            }
                            else if (TextUtils.isEmpty ( newUserPasswordValue )){
                                View view = LayoutInflater.from ( SignInActivity.this ).inflate ( R.layout.toast,null,false );
                                TextView textView = view.findViewById ( R.id.toast_text );
                                textView.setText ( "Fill password" );
                                Toast toast = new Toast ( getApplicationContext () );
                                toast.setView ( view );
                                toast.show ();
                            }
                            else {
                                View view = LayoutInflater.from ( SignInActivity.this ).inflate ( R.layout.toast,null,false );
                                TextView textView = view.findViewById ( R.id.toast_text );
                                textView.setText ( "Fill email - id" );
                                Toast toast = new Toast ( getApplicationContext () );
                                toast.setView ( view );
                                toast.show ();
                            }
                        }
                        else {
                            mAuth.createUserWithEmailAndPassword ( newUserEmailValue,newUserPasswordValue )
                                    .addOnCompleteListener ( new OnCompleteListener<AuthResult> () {
                                        @Override
                                        public void onComplete( @NonNull Task<AuthResult> task ) {
                                            if (task.isSuccessful ()){
                                                rootRef.addListenerForSingleValueEvent ( new ValueEventListener () {
                                                    @Override
                                                    public void onDataChange( @NonNull DataSnapshot dataSnapshot ) {
                                                        if (dataSnapshot.child ( mAuth.getCurrentUser ().getUid () ).exists ()){
                                                            View view = LayoutInflater.from ( SignInActivity.this ).inflate ( R.layout.toast,null,false );
                                                            TextView textView = view.findViewById ( R.id.toast_text );
                                                            textView.setText ( "Already a user" );
                                                            Toast toast = new Toast ( getApplicationContext () );
                                                            toast.setView ( view );
                                                            toast.show ();
                                                        }
                                                        else {
                                                            rootRef.child ( mAuth.getCurrentUser ().getUid () ).setValue ( newUser ).addOnCompleteListener ( new OnCompleteListener<Void> () {
                                                                @Override
                                                                public void onComplete( @NonNull Task<Void> task ) {
                                                                    if (task.isSuccessful ()){
                                                                        View view = LayoutInflater.from ( SignInActivity.this ).inflate ( R.layout.toast,null,false );
                                                                        TextView textView = view.findViewById ( R.id.toast_text );
                                                                        textView.setText ( "Signed up" );
                                                                        Toast toast = new Toast ( getApplicationContext () );
                                                                        toast.setView ( view );
                                                                        toast.show ();
                                                                        alertDialog.dismiss ();
                                                                    }
                                                                    else {
                                                                        View view = LayoutInflater.from ( SignInActivity.this ).inflate ( R.layout.toast,null,false );
                                                                        TextView textView = view.findViewById ( R.id.toast_text );
                                                                        textView.setText ( "Failed" );
                                                                        Toast toast = new Toast ( getApplicationContext () );
                                                                        toast.setView ( view );
                                                                        toast.show ();
                                                                    }
                                                                }
                                                            } );
                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled( @NonNull DatabaseError databaseError ) {

                                                    }
                                                } );
                                            }
                                            else {
                                                View view = LayoutInflater.from ( SignInActivity.this ).inflate ( R.layout.toast,null,false );
                                                TextView textView = view.findViewById ( R.id.toast_text );
                                                textView.setText ( "Failed" );
                                                Toast toast = new Toast ( getApplicationContext () );
                                                toast.setView ( view );
                                                toast.show ();
                                            }
                                        }
                                    } );
                        }
                    }
                } );

                alertDialog.setCanceledOnTouchOutside ( false );
                alertDialog.show ();
            }
        } );

    }
    @Override
    public void onRequestPermissionsResult( int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults ) {

        super.onRequestPermissionsResult ( requestCode, permissions, grantResults );

        if (PERMISSION_REQUEST_CODE == requestCode && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            View view = LayoutInflater.from ( SignInActivity.this ).inflate ( R.layout.toast,null,false );
            TextView textView = view.findViewById ( R.id.toast_text );
            ImageView imageView = view.findViewById ( R.id.toast_image );
            textView.setText ( "Permission Granted" );
            Toast toast = new Toast ( getApplicationContext () );
            toast.setView ( view );
            toast.show ();
        } else {
            View view = LayoutInflater.from ( SignInActivity.this ).inflate ( R.layout.toast,null,false );
            TextView textView = view.findViewById ( R.id.toast_text );
            ImageView imageView = view.findViewById ( R.id.toast_image );
            textView.setText ( "Permission Denied" );
            Toast toast = new Toast ( getApplicationContext () );
            toast.setView ( view );
            toast.show ();
        }
    }
}