package com.example.bugracket;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.transition.TransitionManager;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class AuthenticationActivity extends AppCompatActivity {
    private static final String TAG = "AuthenticationActivity";
    private static final ProfileManager profileManager = new ProfileManager();
    private TextView logInTextView;
    private TextInputEditText userName;
    private TextInputEditText password;
    private TextInputEditText email;
    private TextInputLayout emailWrapper;
    private Button confirmButton;
    private Button logInButton;
    private Button signInButton;
    private boolean toLogIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        toLogIn = true;
        initializeUI();
        setOnClickListener();
        setOnEditorActionListener();

    }

    private void initializeUI() {

        logInTextView = findViewById(R.id.log_in_textView);
        userName = findViewById(R.id.user_name);
        password = findViewById(R.id.password);
        email = findViewById(R.id.email);
        emailWrapper = findViewById(R.id.email_wrapper);
        confirmButton = findViewById(R.id.confirm_button);
        logInButton = findViewById(R.id.log_in_button);
        signInButton = findViewById(R.id.sign_in_button);

        email.setVisibility(View.GONE);
        emailWrapper.setVisibility(View.GONE);
        logInButton.setVisibility(View.GONE);

    }

    @SuppressLint("SetTextI18n")
    private void setOnClickListener() {

        confirmButton.setOnClickListener(view -> {

            String usernameInput = Objects.requireNonNull(userName.getText()).toString();
            String passwordInput = Objects.requireNonNull(password.getText()).toString();

            if (toLogIn) {

                profileManager.verifyPassword(usernameInput, passwordInput, this, new ProfileManager.verificationCallback() {
                    @Override
                    public void onSuccess(boolean pass) {

                        if (pass) {

                            User.getInstance().setName(usernameInput);

                            Toast.makeText(AuthenticationActivity.this, "Logged in!", Toast.LENGTH_SHORT).show();

                            SharedPreferences sharedPreferences = getSharedPreferences("SharedPref", MODE_PRIVATE);
                            SharedPreferences.Editor myEdit = sharedPreferences.edit();

                            myEdit.putBoolean("isLoggedIn", true);
                            myEdit.putString("name", usernameInput);
                            myEdit.apply();

                            Intent homePageIntent = new Intent(AuthenticationActivity.this, HomePageActivity.class);
                            startActivity(homePageIntent);
                            finish();

                        } else {

                            Toast.makeText(AuthenticationActivity.this, "Incorrect user name or password, please try again", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailure(Exception e) {

                        Log.d(TAG, String.valueOf(e));

                    }
                });
            } else {

                String emailInput = Objects.requireNonNull(email.getText()).toString();

                profileManager.createNewUser(usernameInput, passwordInput, emailInput, this, new ProfileManager.emptyCallback() {
                    @Override
                    public void onSuccess() {

                        User.getInstance().setName(usernameInput);
                        User.getInstance().setEmail(emailInput);

                        Toast.makeText(AuthenticationActivity.this, "Account created, welcome!", Toast.LENGTH_SHORT).show();

                        SharedPreferences sharedPreferences = getSharedPreferences("SharedPref", MODE_PRIVATE);
                        SharedPreferences.Editor myEdit = sharedPreferences.edit();

                        myEdit.putBoolean("isLoggedIn", true);
                        myEdit.putString("name", usernameInput);
                        myEdit.apply();

                        Intent homePageIntent = new Intent(AuthenticationActivity.this, HomePageActivity.class);
                        startActivity(homePageIntent);
                        finish();

                    }

                    @Override
                    public void onFailure(Exception e) {

                        Toast.makeText(AuthenticationActivity.this, "Failed to create the account", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, String.valueOf(e));

                    }
                });
            }
        });

        signInButton.setOnClickListener(view -> {

            TransitionManager.beginDelayedTransition((ViewGroup) view.getRootView());
            logInTextView.setText("Sign up");
            email.setVisibility(View.VISIBLE);
            emailWrapper.setVisibility(View.VISIBLE);
            signInButton.setVisibility(View.GONE);
            logInButton.setVisibility(View.VISIBLE);
            toLogIn = false;

        });

        logInButton.setOnClickListener(view -> {

            TransitionManager.beginDelayedTransition((ViewGroup) view.getRootView());
            logInTextView.setText("Log in");
            email.setVisibility(View.GONE);
            emailWrapper.setVisibility(View.GONE);
            logInButton.setVisibility(View.GONE);
            signInButton.setVisibility(View.VISIBLE);
            toLogIn = true;

        });
    }

    private void setOnEditorActionListener() {

        userName.setOnEditorActionListener((v, actionId, event) -> {

            if (actionId == EditorInfo.IME_ACTION_NEXT) {

                new Handler(Looper.getMainLooper()).postDelayed(() -> {

                    if (email.getVisibility() == View.VISIBLE) {

                        email.requestFocus();

                    } else {

                        password.requestFocus();

                    }
                }, 100); // Delay of 100 milliseconds

                return true;

            }

            return false;

        });

        email.setOnEditorActionListener((v, actionId, event) -> {

            if (actionId == EditorInfo.IME_ACTION_NEXT) {

                new Handler(Looper.getMainLooper()).postDelayed(() -> password.requestFocus(), 100);
                return true;

            }

            return false;

        });

        password.setOnEditorActionListener((v, actionId, event) -> {

            if (actionId == EditorInfo.IME_ACTION_DONE) {

                // Hide the keyboard
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                return true;

            }

            return false;

        });
    }
}