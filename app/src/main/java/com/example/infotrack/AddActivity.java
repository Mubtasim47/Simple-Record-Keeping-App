package com.example.infotrack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText idEditText, nameEditText, ageEditText;
    RadioGroup radioGroup;
    RadioButton radioButton;
    Spinner spinner;
    String id, name, age, gender, section;

    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        idEditText = findViewById(R.id.adIdEditTextID);
        nameEditText = findViewById(R.id.adNameEditTextID);
        ageEditText = findViewById(R.id.adAgeEditTextID);
        radioGroup = findViewById(R.id.adRadioGroupID);
        spinner = findViewById(R.id.adSpinnerID);



        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.section_array, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(this);

        reference = FirebaseDatabase.getInstance().getReference("Students");
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        section = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void storeData(View view) {
        id = idEditText.getText().toString();
        name = nameEditText.getText().toString();
        age = ageEditText.getText().toString();

        String error = "Please fill up this field";
        if(TextUtils.isEmpty(id)) {
            idEditText.setError(error);
            return;
        }

        if(TextUtils.isEmpty(name)) {
            nameEditText.setError(error);
            return;
        }

        if(TextUtils.isEmpty(age)) {
            ageEditText.setError(error);
            return;
        }

        int buttonID = radioGroup.getCheckedRadioButtonId();
        error = "You must select your gender";
        if(buttonID == -1) {
            Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
            return;
        }
        radioButton = findViewById(buttonID);
        gender = radioButton.getText().toString();


        reference.child(id).setValue(new Student(name, age, gender, section));
        Toast.makeText(getApplicationContext(), "Data Inserted", Toast.LENGTH_SHORT).show();


    }

    public void mainMenu(View view) {
        startActivity(new Intent(getApplicationContext(), MenuActivity.class));
    }
}