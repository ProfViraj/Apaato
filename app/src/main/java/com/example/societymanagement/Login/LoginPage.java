package com.example.societymanagement.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.societymanagement.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class LoginPage extends AppCompatActivity {

    AppCompatButton Login_Button;
    private EditText LoginEmail, LoginPassword;
    private FirebaseAuth mAuth;
    TextView createNew, forgotPassword;
    RelativeLayout anim_layout;

    Animation animation;

    @Override
    protected void onStart() {
        super.onStart();
        //Checking if the user is Logged IN********************************************************************
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null){
            if (currentUser.isEmailVerified()){
                //startActivity(new Intent(LoginPage.this, SelectUserType.class));
                finish();
            }
            else {
                Toast.makeText(this, "Email is not verified yet!", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        anim_layout = findViewById(R.id.anim_layout);
        animation= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        anim_layout.setAnimation(animation);

        LoginEmail = findViewById(R.id.login_email);
        LoginPassword = findViewById(R.id.login_password);
        Login_Button = findViewById(R.id.login);
        mAuth =FirebaseAuth.getInstance();
        createNew = findViewById(R.id.create);
        forgotPassword = findViewById(R.id.forgot_password);
        //Create new Account*****************************************************************************
        createNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginPage.this, Registration.class));
                finish();
            }
        });
        //Forget Password*****************************************************************************
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(LoginPage.this, ForgotPassword.class));
            }
        });
        //Login Button********************************************************************************

        Login_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email, password;
                email = LoginEmail.getText().toString();
                password = LoginPassword.getText().toString();
                //Checking if the values are empty ******************************************************************
                if (TextUtils.isEmpty(email)){
                    Toast.makeText(LoginPage.this, "Enter The Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    Toast.makeText(LoginPage.this, "Enter The Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                //Signing IN ***********************************************************************************************************************************************************
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginPage.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            if (Objects.requireNonNull(mAuth.getCurrentUser()).isEmailVerified()){
                                //Checking if the email is verified **************************************************************************
                                Toast.makeText(LoginPage.this, "login Successful", Toast.LENGTH_SHORT).show();
                                //startActivity(new Intent(LoginPage.this, SelectUserType.class));
                                finish();
                            } else {
                                //If email is not verified ***************************************************************************************
                                Toast.makeText(LoginPage.this, "Please verify your email", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Toast.makeText(LoginPage.this, "login Unsuccessful "+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}