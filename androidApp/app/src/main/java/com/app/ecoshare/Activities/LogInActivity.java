package com.app.ecoshare.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.ecoshare.Models.Authenticate;
import com.app.ecoshare.Models.AuthenticateResponse;
import com.app.ecoshare.MyApplication;
import com.app.ecoshare.R;
import com.app.ecoshare.Retrofit.Api;
import com.app.ecoshare.Retrofit.RetrofitClient;
import com.google.android.material.button.MaterialButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogInActivity extends AppCompatActivity {

    ProgressBar loading;
    TextView email,password;
    MaterialButton loginBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        email = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginBtn = findViewById(R.id.loginBtn);
        loading  = findViewById(R.id.idLoadingPB);

        loginBtn.setOnClickListener(view -> {
            loading.setVisibility(View.VISIBLE);
            Api api = RetrofitClient.getRetrofitSIGNINInstance().create(Api.class);
            Authenticate dataAuthenticate = new Authenticate(email.getText().toString(), password.getText().toString());
            Call<AuthenticateResponse> call = api.authenticate(dataAuthenticate);
            call.enqueue(new Callback<AuthenticateResponse>() {
                @Override
                public void onResponse(Call<AuthenticateResponse> call, Response<AuthenticateResponse> response) {

                MyApplication application = new MyApplication();
                application.setUserToken(response.body().getToken());
                Toast.makeText(LogInActivity.this,"Successfully authentication!",Toast.LENGTH_SHORT).show();


                String[] base64Token = response.body().getToken().split("\\.");
                byte[] decodedBytes = Base64.getDecoder().decode(base64Token[1]);
                String decodedString = new String(decodedBytes);

                try {
                        JSONObject obj = new JSONObject(decodedString);
                        application.setUserId( obj.getInt("id"));
                } catch (JSONException e) {
                        e.printStackTrace();
                }

                Log.i("My response! ", String.valueOf(application.getUserId()));
                Intent intent = new Intent(LogInActivity.this, UserReportsActivity.class);
                startActivity(intent);

                loading.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<AuthenticateResponse> call, Throwable t) {
                    Toast.makeText(LogInActivity.this,"The email or the password is wrong!!" + t,Toast.LENGTH_LONG).show();
                    Log.e("ErrorLogIn" , "Aaa", t);
                    loading.setVisibility(View.GONE);
                }
            });
        });

    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}