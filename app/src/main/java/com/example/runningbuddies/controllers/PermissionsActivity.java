package com.example.runningbuddies.controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;

import com.example.runningbuddies.R;

public class PermissionsActivity extends AppCompatActivity {

    private Button btnGrant;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permissions);

//        if(ContextCompat.checkSelfPermission(PermissionsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
//            startActivity(new Intent(PermissionsActivity.this, MapActivity.class));
//            finish();
//            return;
//        }
        btnGrant = findViewById(R.id.btn_grant);


    }
}