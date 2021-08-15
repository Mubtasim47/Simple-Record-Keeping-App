package com.example.infotrack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText userEmail, userPassword;
    ProgressBar progressBar;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userEmail = findViewById(R.id.lgUserLoginEmailID);
        userPassword = findViewById(R.id.lgUserLoginPasswordID);
        progressBar = findViewById(R.id.lgLoginProgressBarID);

        mAuth = FirebaseAuth.getInstance();

    }

    public void login(View view) {

        String email = userEmail.getText().toString();
        String password = userPassword.getText().toString();

        if(TextUtils.isEmpty(email)) {
            userEmail.setError("Enter your email");
            return;
        }

        if(TextUtils.isEmpty(password)) {
            userPassword.setError("Password Required");
            return;
        }

        //check for authenticity

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.INVISIBLE);
                if(task.isSuccessful()) {
                    finish();
                    Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                    startActivity(intent);
                } else {
                    String display = "Error! " + task.getException().getMessage();
                    Toast.makeText(LoginActivity.this, display, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        //do nothing
    }

    public void registerPage(View view) {
        startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
    }
}