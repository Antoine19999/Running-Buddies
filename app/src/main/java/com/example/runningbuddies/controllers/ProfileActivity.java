package com.example.runningbuddies.controllers;



import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.runningbuddies.R;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {
    // Define the object for Radio Group,
    // Submit and Clear buttons
    private RadioGroup radioGroup;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Spinner spinner = findViewById(R.id.spinner);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("6 km/h");
        arrayList.add("8 km/h");
        arrayList.add("10 km/h");
        arrayList.add("12 km/h");
        arrayList.add("14 km/h");
        arrayList.add("16 km/h");
        arrayList.add("18 km/h");
        arrayList.add("20 km/h");
        arrayList.add("22 km/h");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,                         android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        //Second spinner
        Spinner spinner_max = findViewById(R.id.spinner2);
        ArrayList<String> arrayList1 = new ArrayList<>();
        arrayList1.add("6 km/h");
        arrayList1.add("8 km/h");
        arrayList1.add("10 km/h");
        arrayList1.add("12 km/h");
        arrayList1.add("14 km/h");
        arrayList1.add("16 km/h");
        arrayList1.add("18 km/h");
        arrayList1.add("20 km/h");
        arrayList1.add("22 km/h");


        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(this,                         android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_max.setAdapter(arrayAdapter1);


        // RadioGroup Info
        radioGroup = findViewById(R.id.radio_group_gender);



        }

}