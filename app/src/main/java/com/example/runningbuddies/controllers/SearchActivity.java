package com.example.runningbuddies.controllers;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.runningbuddies.R;
import com.example.runningbuddies.helpers.MyAdapter;
import com.example.runningbuddies.interfaces.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class SearchActivity extends AppCompatActivity {

    public static final String MyPREFERENCES = "MyPrefs" ;
    DatabaseReference mDatabase;
    FirebaseAuth mAuth;
    RecyclerView mRecyclerView;
    MyAdapter mMyAdapter;
    ArrayList<User> mUsers;
    String currentUserMinSpeed, currentUserMaxSpeed, currentUserPreference;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        currentUserMinSpeed = sharedpreferences.getString("userMinSpeed", "");
        currentUserMaxSpeed = sharedpreferences.getString("userMaxSpeed", "");
        currentUserPreference = sharedpreferences.getString("userTimePreference", "");

        mAuth = FirebaseAuth.getInstance();

        mRecyclerView = findViewById(R.id.userList);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mUsers = new ArrayList<>();
        mMyAdapter = new MyAdapter(this, mUsers);
        mRecyclerView.setAdapter(mMyAdapter);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");
        FirebaseUser currentUser = mAuth.getCurrentUser();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Map<String, Object> users = (Map<String, Object>) snapshot.getValue();

                if (users != null && currentUser != null) {
                    String currentUserUid = currentUser.getUid();

                    for (Map.Entry<String, Object> entry : users.entrySet()) {
                        //Get user map and info
                        Map singleUser = (Map) entry.getValue();

                        String key = entry.getKey();

                        // If same user, then save info and continue
                        if (key.equals(currentUserUid)) {
                            continue;
                        }

                        String firstName = (String) singleUser.get("firstName");
                        String lastName = (String) singleUser.get("lastName");
                        String userName = (String) singleUser.get("userName");
                        String userEmail = (String) singleUser.get("userEmail");
                        Long userAge = (Long) singleUser.get("userAge");
                        String userGender = (String) singleUser.get("userGender");
                        String userMinSpeed = (String) singleUser.get("userMinSpeed");
                        String userMaxSpeed = (String) singleUser.get("userMaxSpeed");
                        String otherUserTimePreference = (String) singleUser.get("userTimePreference");

                        // Convert all necessary values
                        assert userMaxSpeed != null;
                        Integer otherUserMaxSpeed = convertSpeed(userMaxSpeed);
                        Integer currentUserMaxSpeedNumber = convertSpeed(currentUserMaxSpeed);

                        int diff = (currentUserMaxSpeedNumber - otherUserMaxSpeed);
                        // Filter results
                        if (diff >= -2 && diff <= 2) {
                            if (currentUserPreference.equals(otherUserTimePreference)) {
                                String[] tokens = otherUserTimePreference.split(" ");

                                User newUser = new User(userName, userEmail, firstName, lastName, userAge,
                                        userGender, tokens[0], userMinSpeed, userMaxSpeed);
                                mUsers.add(newUser);
                            }
                        }
                        mMyAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "loadPost:onCancelled", error.toException());
            }
        });
    }

    private Integer convertSpeed(String speedString) {
        // Split the string
        String[] tokens = speedString.split(" ");
        String speed = tokens[0];

        // Return converted speed value
        return Integer.parseInt(speed);
    }
}
