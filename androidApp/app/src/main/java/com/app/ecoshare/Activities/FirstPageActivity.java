package com.app.ecoshare.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.app.ecoshare.R;
import com.google.android.material.button.MaterialButton;

public class FirstPageActivity extends AppCompatActivity {

    MaterialButton logIn, signUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);
        logIn = findViewById(R.id.loginBtn);
        signUp = findViewById(R.id.signUpBtn);

        logIn.setOnClickListener(view -> {
             Intent intent = new Intent(FirstPageActivity.this, LogInActivity.class);
             startActivity(intent);
         }
        );

        signUp.setOnClickListener(view -> {
            Intent intent = new Intent(FirstPageActivity.this, SignUpActivity.class);
            startActivity(intent);
        });

    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

}