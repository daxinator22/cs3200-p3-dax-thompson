package com.daxthompsonproject1.phoneapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.daxthompsonproject1.api.Verify;
import com.daxthompsonproject1.api.models.Reservation;
import com.daxthompsonproject1.api.viewmodels.ManagerViewModel;

public class HomeActivity extends AppCompatActivity {

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

    }
}