package com.example.runningbuddies.controllers;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
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

    DatabaseReference mDatabase;
    FirebaseAuth mAuth;
    RecyclerView mRecyclerView;
    MyAdapter mMyAdapter;
    ArrayList<User> mUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mAuth = FirebaseAuth.getInstance();

        mRecyclerView = findViewById(R.id.userList);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mUsers = new ArrayList<>();
        mMyAdapter = new MyAdapter(this, mUsers);
        mRecyclerView.setAdapter(mMyAdapter);

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

                        // If same user, then continue
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
                        String userTimePreference = (String) singleUser.get("userTimePreference");

                        if (userTimePreference != null) {
                            String[] tokens = userTimePreference.split(" ");


                            User newUser = new User(userName, userEmail, firstName, lastName, userAge,
                                    userGender, tokens[0], userMinSpeed, userMaxSpeed);
                            mUsers.add(newUser);
                        }
                    }
                    mMyAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "loadPost:onCancelled", error.toException());
            }
        });
    }
}
