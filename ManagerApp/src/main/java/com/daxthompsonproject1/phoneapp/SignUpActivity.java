package com.daxthompsonproject1.phoneapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.lifecycle.ViewModelProvider;

import com.daxthompsonproject1.api.viewmodels.ManagerViewModel;

public class SignUpActivity extends AppCompatActivity {

    private ManagerViewModel viewModel;
    private String lat;
    private String lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        this.viewModel = new ViewModelProvider(this).get(ManagerViewModel.class);

        AppCompatEditText username = findViewById(R.id.username);
        AppCompatEditText password = findViewById(R.id.password);
        AppCompatEditText displayName = findViewById(R.id.displayName);
        AppCompatEditText company = findViewById(R.id.company);

        if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        LocationManager manager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, (location) -> {
            this.lat = location.getLatitude() + "";
            this.lon = location.getLongitude() + "";
        });

        AppCompatButton signUp = findViewById(R.id.signUp);
        signUp.setOnClickListener(view -> {
            viewModel.signUp(username.getText().toString(), password.getText().toString(), displayName.getText().toString(), company.getText().toString(), this.lat, this.lon);
            finish();
        });

    }
}