package com.example.infotrack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class RegisterActivity extends AppCompatActivity {

    EditText fullName, email, password, rePassword;
    ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fullName = findViewById(R.id.rgFullNameID);
        email = findViewById(R.id.rgUserEmailID);
        password = findViewById(R.id.rgPasswordID);
        rePassword = findViewById(R.id.rgReTypePasswordID);
        progressBar = findViewById(R.id.rgProgressBarID);

        mAuth = FirebaseAuth.getInstance();
    }

    public void register(View view) {

        String display;

        String nameText = fullName.getText().toString();
        String emailText = email.getText().toString();
        String passwordText = password.getText().toString();
        String rePasstext = rePassword.getText().toString();

        boolean isEmpty = TextUtils.isEmpty(nameText) || TextUtils.isEmpty(emailText) ||
                TextUtils.isEmpty(passwordText) || TextUtils.isEmpty(rePasstext);

        if(isEmpty) {
            display = "Please fill up all required fields";
            Toast.makeText(RegisterActivity.this, display, Toast.LENGTH_SHORT).show();
            return;
        }

        if(passwordText.length() < 8) {
            display = "Password must be 8 characters long";
            Toast.makeText(RegisterActivity.this, display, Toast.LENGTH_SHORT).show();
            return;
        }

        if(!passwordText.equals(rePasstext)) {
            display = "Password doesn't match";
            Toast.makeText(RegisterActivity.this, display, Toast.LENGTH_SHORT).show();
            return;
        }


        if(!passwordText.contains("$")) {
            display = "Password must contain $ symbol";
            Toast.makeText(RegisterActivity.this, display, Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);



        //register user in firebase
        mAuth.createUserWithEmailAndPassword(emailText, passwordText).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.INVISIBLE);
                String show;
                if(task.isSuccessful()) {
                    finish();
                    show = "User Created Successfully";
                    Toast.makeText(RegisterActivity.this, show, Toast.LENGTH_LONG).show();
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                } else {
                    show = "Error! " + task.getException().getMessage();
                    Toast.makeText(RegisterActivity.this, show, Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    public void loginPage(View view) {
        finish();
        Intent login = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(login);
    }

    @Override
    public void onBackPressed() {
        //do nothing
    }
}