package com.daxthompsonproject1.clientapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.daxthompsonproject1.api.viewmodels.ClientViewModel;
import com.daxthompsonproject1.api.viewmodels.ManagerViewModel;

public class SignUpActivity extends AppCompatActivity {

    private ClientViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        this.viewModel = new ViewModelProvider(this).get(ClientViewModel.class);

        AppCompatEditText username = findViewById(R.id.username);
        AppCompatEditText password = findViewById(R.id.password);
        AppCompatEditText displayName = findViewById(R.id.displayName);

        AppCompatButton signUp = findViewById(R.id.signUp);
        signUp.setOnClickListener(view -> {
            viewModel.signUp(username.getText().toString(), password.getText().toString(), displayName.getText().toString());
            finish();
        });
    }
}