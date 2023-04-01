package com.app.ecoshare.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.app.ecoshare.MyApplication;
import com.app.ecoshare.R;


public class UserReportsActivity extends AppCompatActivity {
    MyApplication application = new MyApplication();
    String token = "Bearer " + application.getUserToken();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_reports);
    }

    public void logOut(View view) {

        application.setUserId(null);
        application.setUserToken(null);
        Intent intent = new Intent(UserReportsActivity.this, FirstPageActivity.class);
        startActivity(intent);
    }

    public void addReport(View view){
        Intent intent = new Intent(UserReportsActivity.this, NewReportActivity.class);
        startActivity(intent);
    }


}