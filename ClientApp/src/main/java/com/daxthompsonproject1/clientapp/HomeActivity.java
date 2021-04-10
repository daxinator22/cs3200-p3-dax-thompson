package com.daxthompsonproject1.clientapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.daxthompsonproject1.api.models.ManagerData;
import com.daxthompsonproject1.api.models.Reservation;
import com.daxthompsonproject1.api.viewmodels.ClientViewModel;
import com.daxthompsonproject1.api.viewmodels.ManagerViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomeActivity extends AppCompatActivity {

    public static final int RECORD_CODE = 1;
    private boolean isTalking = false;

    private ClientViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        this.viewModel = new ViewModelProvider(this).get(ClientViewModel.class);

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

        RadioGroup managers = findViewById(R.id.managers);
        viewModel.getManagers().observe(this, managerList -> {
            for(ManagerData manager : viewModel.getManagers().getValue()){
                RadioButton managerOption = new RadioButton(this);
                managerOption.setText(manager.company);

                managers.addView(managerOption);
            }
        });

        AppCompatButton makeReservation = findViewById(R.id.makeReservation);
        makeReservation.setOnClickListener(view -> {
            int selected = managers.getCheckedRadioButtonId();
            RadioButton button = findViewById(selected);
            if(button != null) {
                viewModel.makeReservation(button.getText().toString());
            }
            managers.clearCheck();
        });

        LinearLayout reservations = findViewById(R.id.reservations);
        viewModel.getWaitList().observe(this, waitlist -> {
            reservations.removeAllViews();
            for(Reservation r : viewModel.getWaitList().getValue()) {
                LinearLayout container = r.render(this);

                reservations.addView(container);
                container.setOnClickListener(view -> {
                    reservations.removeView(container);
                    this.viewModel.removeReservation(r);
                });
            }
        });

        if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        AppCompatTextView locationView = findViewById(R.id.location);
        LocationManager manager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, (location) -> {
            locationView.setText(String.format("Your current location is %s latitude %s longitude", location.getLatitude(), location.getLongitude()));
        });
    }
}