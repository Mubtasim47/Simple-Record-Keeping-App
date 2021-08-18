package com.example.infotrack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DeleteActivity extends AppCompatActivity {
    private EditText idEditText;

    DatabaseReference rootReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        idEditText = findViewById(R.id.dtIDEditTextID);
        rootReference = FirebaseDatabase.getInstance().getReference().getRoot();
    }

    public void delete(View view) {
        String searchID = idEditText.getText().toString();
        rootReference.child("Students").child(searchID).removeValue();

        Toast.makeText(DeleteActivity.this, "Record Deleted", Toast.LENGTH_SHORT).show();
    }

    public void mainMenu(View view) {
        startActivity(new Intent(DeleteActivity.this, MenuActivity.class));
    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(DeleteActivity.this, MenuActivity.class));
    }
}