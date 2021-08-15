package com.example.infotrack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity {

    TextView sampleTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        sampleTextView = findViewById(R.id.mnTextViewID);
    }

    public void beginProcess(View view) {
        if(view.getId() == R.id.mnImageView1ID) {
            startActivity(new Intent(getApplicationContext(), AddActivity.class));
        } else if(view.getId() == R.id.mnImageView2ID) {
            startActivity(new Intent(getApplicationContext(), DeleteActivity.class));
        } else if(view.getId() == R.id.mnImageView3ID) {
            startActivity(new Intent(getApplicationContext(), FindActivity.class));
        } else if(view.getId() == R.id.mnImageView4ID) {
            startActivity(new Intent(getApplicationContext(), UpdateActivity.class));
        }
    }

    @Override
    public void onBackPressed() {
        //do nothing
    }
}