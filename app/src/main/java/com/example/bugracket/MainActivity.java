package com.example.bugracket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkLoginStatus();

    }

    private void checkLoginStatus() {

        SharedPreferences sharedPreferences = getSharedPreferences("SharedPref", MODE_PRIVATE);

        boolean isLoggedIn = false;

        if (sharedPreferences != null) isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

        if (isLoggedIn) {

            Intent intent = new Intent(this, HomePageActivity.class);
            startActivity(intent);
            finish();

        } else {

            Intent authenticationIntent = new Intent(this, AuthenticationActivity.class);
            startActivity(authenticationIntent);
            finish();

        }
    }
}