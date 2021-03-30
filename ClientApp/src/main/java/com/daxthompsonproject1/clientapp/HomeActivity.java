package com.daxthompsonproject1.clientapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import com.daxthompsonproject1.api.viewmodels.ClientViewModel;
import com.daxthompsonproject1.api.viewmodels.ManagerViewModel;

public class HomeActivity extends AppCompatActivity {

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
    }
}