package com.example.ecommerce;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class Login extends AppCompatActivity {
private FirebaseAuth auth;
private EditText loginEmail,loginPassword;
private TextView signupRedirectText;
private Button loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

    auth = FirebaseAuth.getInstance();
    loginEmail=findViewById(R.id.login_email);
    loginPassword=findViewById(R.id.login_password);
    loginButton=findViewById(R.id.login_button);
    signupRedirectText=findViewById(R.id.signupRedirectText);



    loginButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String email= loginEmail.getText().toString();
            String pass=loginPassword.getText().toString();
            if(!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                if(!pass.isEmpty()){
                    auth.signInWithEmailAndPassword(email,pass)
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(Login.this,MainActivity.class));
                                    finish();
                                }

                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Login.this, "Login Failed", Toast.LENGTH_SHORT).show();

                                }
                            });

                }
                else{
                    loginPassword.setError("Password Cannot be Empty");
                }


            }
            else if(email.isEmpty()){
                loginEmail.setError("Email Cannot be Empty");
            }
            else{
                loginEmail.setError("Please Enter the Valid email");
            }

        }
    });
    signupRedirectText.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Login.this,Register.class);
            startActivity(intent);
        }
    });
    }
}