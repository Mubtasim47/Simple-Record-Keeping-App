package com.example.infotrack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FindActivity extends AppCompatActivity {

    TextView textView1, textView2, textView3, textView4;
    EditText editText;

    ProgressBar progressBar;

    DatabaseReference reference;

    String name, age, gender, section;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);

        textView1 = findViewById(R.id.fdTextView1ID);
        textView2 = findViewById(R.id.fdTextView2ID);
        textView3 = findViewById(R.id.fdTextView3ID);
        textView4 = findViewById(R.id.fdTextView4ID);
        editText = findViewById(R.id.fdSearchEditTextID);
        progressBar = findViewById(R.id.fdProgressBarID);



    }

    public void submit(View view) {

        String key = editText.getText().toString();
        if(TextUtils.isEmpty(key)) {
            editText.setError("Required field");
            return;
        }

        reference = FirebaseDatabase.getInstance().getReference().child("Students").child(key);

        progressBar.setVisibility(View.VISIBLE);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                try {
                    name = snapshot.child("name").getValue().toString();
                    age = snapshot.child("age").getValue().toString();
                    gender = snapshot.child("gender").getValue().toString();
                    section = snapshot.child("section").getValue().toString();
                }catch (Throwable e) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(), "ID not found", Toast.LENGTH_LONG).show();
                    return;
                }




                progressBar.setVisibility(View.INVISIBLE);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fdFragmentloadid, new DisplayFragment());
                ft.commit();


                age = "Age: " + age;
                gender = "Gender: " + gender;
                section = "Section: " + section;

                textView1.setText(name);
                textView2.setText(age);
                textView3.setText(gender);
                textView4.setText(section);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void mainMenu(View view) {
        startActivity(new Intent(getApplicationContext(), MenuActivity.class));
    }
}