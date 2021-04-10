package com.daxthompsonproject1.phoneapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.daxthompsonproject1.api.Verify;
import com.daxthompsonproject1.api.models.Reservation;
import com.daxthompsonproject1.api.viewmodels.ManagerViewModel;

public class HomeActivity extends AppCompatActivity{

    private ManagerViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        this.viewModel = new ViewModelProvider(this).get(ManagerViewModel.class);

        AppCompatButton signOut = findViewById(R.id.signOut);
        signOut.setOnClickListener(view -> {
            viewModel.signOut();
        });

        AppCompatTextView userDataText = findViewById(R.id.userData);
        viewModel.getUser().observe(this, user ->{
            if(user == null) {
                Intent intent = new Intent(this, SignInActivity.class);
                startActivity(intent);
            }
        });

        viewModel.getUserData().observe(this, userData -> {
            if(userData != null){
                userDataText.setText(userData.toString());
            }
        });

        LinearLayout reservations = findViewById(R.id.reservations);
        viewModel.getWaitList().observe(this, waitlist -> {
            reservations.removeAllViews();

           for(Reservation r : viewModel.getWaitList().getValue()){
               LinearLayout container = r.render(this);
               container.setOnClickListener(view ->{
                   reservations.removeView(container);
                   viewModel.removeReservation(r);
               });

               reservations.addView(container);
           }
        });

        if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        AppCompatTextView locationView = findViewById(R.id.location);
        LocationManager manager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                locationView.setText(String.format("Your location is %s latitude %s longitude", location.getLatitude(), location.getLongitude()));
            }
        });


    }
}