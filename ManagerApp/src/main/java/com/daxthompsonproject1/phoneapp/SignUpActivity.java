package com.daxthompsonproject1.phoneapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.lifecycle.ViewModelProvider;

import com.daxthompsonproject1.api.viewmodels.ManagerViewModel;

public class SignUpActivity extends AppCompatActivity {

    private ManagerViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        this.viewModel = new ViewModelProvider(this).get(ManagerViewModel.class);

        AppCompatEditText username = findViewById(R.id.username);
        AppCompatEditText password = findViewById(R.id.password);
        AppCompatEditText displayName = findViewById(R.id.displayName);
        AppCompatEditText company = findViewById(R.id.company);

        AppCompatButton signUp = findViewById(R.id.signUp);
        signUp.setOnClickListener(view -> {
            viewModel.signUp(username.getText().toString(), password.getText().toString(), displayName.getText().toString(), company.getText().toString());
            finish();
        });

    }
}