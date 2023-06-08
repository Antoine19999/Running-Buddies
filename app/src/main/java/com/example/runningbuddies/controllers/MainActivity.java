package com.example.runningbuddies.controllers;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.runningbuddies.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements LocationListener {


    FirebaseAuth auth;
    Button logoutButton, mapButton, searchButton, recordButton;
    TextView textView;
    FirebaseUser user;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    LocationManager mLocationManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        textView = findViewById(R.id.userEmail);

        auth = FirebaseAuth.getInstance();
        logoutButton = findViewById(R.id.logout);
        mapButton = findViewById(R.id.map);
        searchButton = findViewById(R.id.settings);
        recordButton = findViewById(R.id.record);
        user = auth.getCurrentUser();

        if (user == null){
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }
        else {
            textView.setText(user.getEmail());
        }

        logoutButton.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        });

        mapButton.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
            startActivity(intent);
        });

        searchButton.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
            startActivity(intent);
        });

        recordButton.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), RecordJourney.class);
            startActivity(intent);
        });

        requestForPermissions();
    }

    /**
     * Check for permissions and request if necessary
     */
    private void requestForPermissions() {
        // Check if permissions are granted. If not then ask
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACTIVITY_RECOGNITION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            }, 100);
        } else {
            getLocation();
        }
    }

    @RequiresPermission(allOf = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    private void getLocation() {
        mLocationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);

        // Request location updates if permission is granted
        if (mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
            }
        }
    }


    @Override
    public void onLocationChanged(@NonNull Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        mDatabase = FirebaseDatabase.getInstance("https://running-buddies-b032c-default-rtdb.europe-west1.firebasedatabase.app/").getReference();

        // Get current user
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            // Update user location
            mDatabase.child("users").child(currentUser.getUid()).child("longitude").setValue(longitude);
            mDatabase.child("users").child(currentUser.getUid()).child("latitude").setValue(latitude);
        }

    }
    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}