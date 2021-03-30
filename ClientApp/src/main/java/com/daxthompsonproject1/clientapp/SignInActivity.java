package com.daxthompsonproject1.clientapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import com.daxthompsonproject1.api.viewmodels.ClientViewModel;
import com.daxthompsonproject1.api.viewmodels.ManagerViewModel;

public class SignInActivity extends AppCompatActivity {

    private ClientViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        this.viewModel = new ViewModelProvider(this).get(ClientViewModel.class);

        AppCompatEditText username = findViewById(R.id.username);
        AppCompatEditText password = findViewById(R.id.password);

        AppCompatButton signIn = findViewById(R.id.signIn);
        signIn.setOnClickListener(view -> {
            viewModel.signIn(username.getText().toString(), password.getText().toString());
            finish();
        });

        AppCompatButton signUp = findViewById(R.id.signUp);
        signUp.setOnClickListener(view -> {
            Intent intent = new Intent(this, SignUpActivity.class);
            startActivity(intent);
            finish();
        });
    }
}