package com.example.runningbuddies.controllers;
import com.example.runningbuddies.interfaces.User;



import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.runningbuddies.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {
    // Define the object for Radio Group,
    // Submit and Clear buttons
    EditText editTextFirstName, editTextLastName, editTextUserName, editTextAge;
    private RadioGroup radioGroupGender;
    private RadioButton selectedRadioButtonGender;

    private RadioGroup radioGroupRun;
    private RadioButton selectedRadioButtonRun;

    Button submitUserInformation;

    FirebaseAuth mAuth;

    DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        editTextFirstName = findViewById(R.id.firstName);
        editTextLastName = findViewById(R.id.lastName);
        editTextUserName = findViewById(R.id.userName);
        editTextAge = findViewById(R.id.age);

        mAuth = FirebaseAuth.getInstance();

        List<String> arrayList = Arrays.asList("6 km/h", "8 km/h", "10 km/h", "12 km/h",
                "14 km/h", "16 km/h", "18 km/h", "20 km/h", "22 km/h");

        Spinner spinner = findViewById(R.id.spinner);

        //First spinner
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        //Second spinner
        Spinner spinner_max = findViewById(R.id.spinner2);
        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_max.setAdapter(arrayAdapter1);

        addListenerOnButton();

        }

        public void addListenerOnButton() {
            submitUserInformation = findViewById(R.id.idBtnSubmitCourse);

            // RadioGroup Info
            radioGroupGender = findViewById(R.id.radio_group_gender);
            radioGroupRun = findViewById(R.id.radio_group_run);

            submitUserInformation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String firstName, lastName, userName;
                    int age;
                    firstName = String.valueOf(editTextFirstName.getText());
                    lastName = String.valueOf(editTextLastName.getText());
                    userName = String.valueOf(editTextUserName.getText());
                    age = Integer.parseInt(editTextAge.getText().toString());

                    // get selected radio button from radioGroup
                    int genderSelectedId = radioGroupGender.getCheckedRadioButtonId();
                    int runSelectedId = radioGroupRun.getCheckedRadioButtonId();

                    // find the radiobutton by returned id
                    selectedRadioButtonGender = findViewById(genderSelectedId);
                    selectedRadioButtonRun = findViewById(runSelectedId);

                    Spinner minSpinner = findViewById(R.id.spinner);
                    Spinner maxSpinner = findViewById(R.id.spinner2);
                    String minSpeed = minSpinner.getSelectedItem().toString();
                    String maxSpeed = maxSpinner.getSelectedItem().toString();

                    // Get current user
                    FirebaseUser currentUser = mAuth.getCurrentUser();
                    if (currentUser != null) {
                        String userUid = currentUser.getUid();
                        String userEmail = currentUser.getEmail();
                        // Write a message to the database
                        mDatabase = FirebaseDatabase.getInstance().getReference();

                        // Create a new User
                        User user = new User(userName, userEmail, firstName, lastName, age,
                                selectedRadioButtonGender.toString(),
                                selectedRadioButtonRun.toString(), minSpeed, maxSpeed);

                        // Save user info
                        mDatabase.child("users").child(userUid).setValue(user);

                        // Go to main activity
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    Log.d("Information", "Gender = " + selectedRadioButtonGender.getText()
                            + "Speed = " + selectedRadioButtonRun.getText()
                            + "firstName = " + firstName
                            + "lastName = " + lastName
                            + "userName = " + userName
                            + "age = " + age
                            + "minSpeed = " + minSpeed
                            + "maxSpeed = " + maxSpeed);
                }
            });
        }

}