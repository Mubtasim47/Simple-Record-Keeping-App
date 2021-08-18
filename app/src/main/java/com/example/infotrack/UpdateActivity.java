package com.example.infotrack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText idEditText, nameEditText, ageEditText;
    RadioGroup radioGroup;
    RadioButton radioButton;
    Spinner spinner;
    TextView textView1, textView2;
    Button updateButton;

    String id, name, age, gender, section;
    String error;

    boolean errorFlag;

    DatabaseReference reference, reference2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        idEditText = findViewById(R.id.upSearchEditTextID);
        nameEditText = findViewById(R.id.upNameEditTextID);
        ageEditText = findViewById(R.id.upAgeEditTextID);
        textView1 = findViewById(R.id.upTextView1ID);
        radioGroup = findViewById(R.id.upRadioGroupID);
        textView2 = findViewById(R.id.upTextView2ID);
        spinner = findViewById(R.id.upSpinnerID);
        updateButton = findViewById(R.id.upUpdateButtonID);


        nameEditText.setVisibility(View.INVISIBLE);
        ageEditText.setVisibility(View.INVISIBLE);
        textView1.setVisibility(View.INVISIBLE);
        radioGroup.setVisibility(View.INVISIBLE);
        textView2.setVisibility(View.INVISIBLE);
        spinner.setVisibility(View.INVISIBLE);
        updateButton.setVisibility(View.INVISIBLE);

        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.section_array, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(this);

    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        section = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void submit(View view) {
        error = "Please fill up this field";
        id = idEditText.getText().toString();
        if(TextUtils.isEmpty(id)) {
            idEditText.setError(error);
            return;
        }

        reference = FirebaseDatabase.getInstance().getReference().child("Students").child(id);
        errorFlag = false;
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    name = snapshot.child("name").getValue().toString();
                    age = snapshot.child("age").getValue().toString();
                    gender = snapshot.child("gender").getValue().toString();
                    section = snapshot.child("section").getValue().toString();

                    nameEditText.setVisibility(View.VISIBLE);
                    nameEditText.setHint(name);
                    ageEditText.setVisibility(View.VISIBLE);
                    ageEditText.setHint(age);
                    textView1.setVisibility(View.VISIBLE);

                    if(gender.equals("Male")) {
                        radioGroup.check(radioGroup.getChildAt(0).getId());
                    } else {
                        radioGroup.check(radioGroup.getChildAt(1).getId());
                    }

                    radioGroup.setVisibility(View.VISIBLE);
                    textView2.setVisibility(View.VISIBLE);
                    spinner.setVisibility(View.VISIBLE);
                    updateButton.setVisibility(View.VISIBLE);

                } catch(Throwable e) {
                   Toast.makeText(UpdateActivity.this, "ID doesn't have any match", Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void update(View view) {

        reference2 = FirebaseDatabase.getInstance().getReference().child("Students");


        name = nameEditText.getText().toString();
        age = ageEditText.getText().toString();


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
            Toast.makeText(UpdateActivity.this, error, Toast.LENGTH_SHORT).show();
            return;
        }
        radioButton = findViewById(buttonID);
        gender = radioButton.getText().toString();

        reference2.child(id).setValue(new Student(name, age, gender, section));
        Toast.makeText(UpdateActivity.this, "Data Updated Successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(UpdateActivity.this, MenuActivity.class));
    }
}