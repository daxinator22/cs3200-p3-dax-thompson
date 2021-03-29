package com.daxthompsonproject1.phoneapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.daxthompsonproject1.api.Verify;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Verify.verifyPhoneApp();


    }
}