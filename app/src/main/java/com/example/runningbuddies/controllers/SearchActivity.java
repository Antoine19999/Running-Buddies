package com.example.runningbuddies.controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.runningbuddies.R;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }

    private void searchUsers() {
        // https://firebase.google.com/docs/database/android/read-and-write?authuser=0&hl=en
        // Check Db, get users and display info
        // Add friend to db (using an ?)
        // Implement ValueEventListener
        // Login in the other user and accept user request
    }
}
