package com.example.runningbuddies.controllers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.runningbuddies.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {

    TextView userEmail;
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;

    private RadioGroup radioGroupRun;
    private RadioButton selectedRadioButtonRun;

    Button saveButton;

    FirebaseAuth mAuth;

    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        userEmail = findViewById(R.id.settings_user_email);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            userEmail.setText(currentUser.getEmail());
        }

        List<String> arrayList = Arrays.asList("6 km/h", "8 km/h", "10 km/h", "12 km/h",
                "14 km/h", "16 km/h", "18 km/h", "20 km/h", "22 km/h");

        Spinner spinner = findViewById(R.id.settings_spinner);

        //First spinner
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        //Second spinner
        Spinner spinner_max = findViewById(R.id.settings_spinner2);
        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_max.setAdapter(arrayAdapter1);

        addListenerOnButton();
    }

    public void addListenerOnButton() {
        saveButton = findViewById(R.id.save_info_button);

        // RadioGroup Info
        radioGroupRun = findViewById(R.id.settings_radio_group_run);

        saveButton.setOnClickListener(view -> {

            // get selected radio button from radioGroup
            int runSelectedId = radioGroupRun.getCheckedRadioButtonId();

            // find the radiobutton by returned id
            selectedRadioButtonRun = findViewById(runSelectedId);

            Spinner minSpinner = findViewById(R.id.settings_spinner);
            Spinner maxSpinner = findViewById(R.id.settings_spinner2);
            String minSpeed = minSpinner.getSelectedItem().toString();
            String maxSpeed = maxSpinner.getSelectedItem().toString();

            // Get current user
            FirebaseUser currentUser = mAuth.getCurrentUser();
            if (currentUser != null) {
                String userUid = currentUser.getUid();

                // Update user information
                mDatabase = FirebaseDatabase.getInstance().getReference();
                mDatabase.child("users").child(userUid).child("userTimePreference").setValue(selectedRadioButtonRun.getText().toString());
                mDatabase.child("users").child(userUid).child("userMinSpeed").setValue(minSpeed);
                mDatabase.child("users").child(userUid).child("userMaxSpeed").setValue(maxSpeed);

                // Save info in shared preferences
                sharedpreferences.edit().putString("userTimePreference", selectedRadioButtonRun.getText().toString()).apply();
                sharedpreferences.edit().putString("userMinSpeed", minSpeed).apply();
                sharedpreferences.edit().putString("userMaxSpeed", maxSpeed).apply();

                Toast.makeText(SettingsActivity.this, "Information saved.",
                        Toast.LENGTH_SHORT).show();


                // Go to main activity
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }

        });
    }

}