package com.example.ecommerce;

import android.content.Intent;
import android.os.Bundle;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    private FirebaseAuth auth;
    private EditText nameEt_signUpPage, emailEt_signUpPage, PassEt_signUpPage, cPassEt_signUpPage;
    private Button signUpBtn_signUpPage;
    private TextView loginRedirectText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();
        nameEt_signUpPage = findViewById(R.id.nameEt_signUpPage);
        emailEt_signUpPage = findViewById(R.id.emailEt_signUpPage);
        PassEt_signUpPage = findViewById(R.id.PassEt_signUpPage);
        cPassEt_signUpPage = findViewById(R.id.cPassEt_signUpPage);
        signUpBtn_signUpPage = findViewById(R.id.signUpBtn_signUpPage); // Initialize signUpBtn_signUpPage
        loginRedirectText = findViewById(R.id.loginRedirectText);

        signUpBtn_signUpPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = emailEt_signUpPage.getText().toString().trim();
                String pass = PassEt_signUpPage.getText().toString().trim();
                String cPass = cPassEt_signUpPage.getText().toString().trim();

                if (user.isEmpty()) {
                    emailEt_signUpPage.setError("Email cannot be Empty");
                }
                if (pass.isEmpty()) {
                    PassEt_signUpPage.setError("Password cannot be empty");
                    return; // Return to avoid further execution
                }
                if (!pass.equals(cPass)) {
                    cPassEt_signUpPage.setError("Passwords do not match");
                    return; // Return to avoid further execution
                }

                auth.createUserWithEmailAndPassword(user, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Register.this, "Signup Success", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Register.this, Login.class));
                        } else {
                            Toast.makeText(Register.this, "Signup Failed" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register.this, Login.class));
            }
        });
    }
}
