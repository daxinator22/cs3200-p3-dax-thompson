package com.daxthompsonproject1.phoneapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.daxthompsonproject1.api.viewmodels.ManagerViewModel;

public class SignInActivity extends AppCompatActivity {

    private ManagerViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        this.viewModel = new ViewModelProvider(this).get(ManagerViewModel.class);

        AppCompatEditText username = findViewById(R.id.username);
        AppCompatEditText password = findViewById(R.id.password);

        AppCompatButton signIn = findViewById(R.id.signIn);

        signIn.setOnClickListener(view -> {
            viewModel.signIn(username.getText().toString(), password.getText().toString());
            finish();
        });

    }
}