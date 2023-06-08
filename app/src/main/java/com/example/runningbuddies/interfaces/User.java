package com.example.runningbuddies.interfaces;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {

    public String userName;
    public String firstName;

    public String lastName;
    public String userEmail;
    public Long userAge;
    public String userTimePreference;
    public String userMaxSpeed;
    public String userMinSpeed;
    public String userGender;
    public double userLatitude;
    public double userLongitude;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Long getUserAge() {
        return userAge;
    }

    public String getUserTimePreference() {
        return userTimePreference;
    }

    public String getUserMaxSpeed() {
        return userMaxSpeed;
    }

    public String getUserMinSpeed() {
        return userMinSpeed;
    }

    public User(String userName, String userEmail, String firstName, String lastName, Long age,
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
        this.userLatitude = 0.0;
        this.userLongitude = 0.0;
    }

}
