package com.example.runningbuddies.interfaces;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {

    public String userName;
    public String firstName;
    public String lastName;
    public String userEmail;
    public int userAge;
    public String userTimePreference;
    public String userMaxSpeed;
    public String userMinSpeed;
    public String userGender;



    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }


    public User(String userName, String userEmail, String firstName, String lastName, int age,
                String userGender, String timePreference, String minimumSpeed, String maximumSpeed) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userAge = age;
        this.userTimePreference = timePreference;
        this.userGender = userGender;
        this.userMinSpeed = minimumSpeed;
        this.userMaxSpeed = maximumSpeed;
    }

}
