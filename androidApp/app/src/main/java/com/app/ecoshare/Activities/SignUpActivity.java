package com.app.ecoshare.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import android.widget.TextView;
import android.widget.Toast;

import com.app.ecoshare.Models.Register;
import com.app.ecoshare.R;
import com.app.ecoshare.Retrofit.Api;
import com.app.ecoshare.Retrofit.RetrofitClient;
import com.google.android.material.button.MaterialButton;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    TextView firstname, lastname, email, password, phoneNumber;
    MaterialButton signUpBtn;
    ProgressBar loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firstname = findViewById(R.id.name);
        lastname = findViewById(R.id.lname);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        phoneNumber = findViewById(R.id.phoneNumber);
        signUpBtn = findViewById(R.id.signUpBtn);
        loading = findViewById(R.id.idLoadingPB);

        signUpBtn.setOnClickListener(view -> {
            loading.setVisibility(View.VISIBLE);
            if (validateEmail() && validateLastName() && validateFirstName() && validatePassword() && validatePhoneNumber()) {
                Api api = RetrofitClient.getRetrofitSIGNINInstance().create(Api.class);
                Register dataRegister = new Register(firstname.getText().toString(), lastname.getText().toString()
                        , email.getText().toString(), password.getText().toString(), phoneNumber.getText().toString());
                Call<Register> call = api.register(dataRegister);
                call.enqueue(new Callback<Register>() {
                    @Override
                    public void onResponse(Call<Register> call, Response<Register> response) {
                        loading.setVisibility(View.GONE);
                        Toast.makeText(SignUpActivity.this, "The register was successful!", Toast.LENGTH_LONG).show();
                        Log.i("Response", response.toString());
                        Intent intent = new Intent(SignUpActivity.this, UserReportsActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<Register> call, Throwable t) {
                        loading.setVisibility(View.GONE);
                        Toast.makeText(SignUpActivity.this, "ERROR!" + t, Toast.LENGTH_SHORT).show();
                        Log.e("ErrorSignIn", t.toString());
                    }
                });
            } else {
                Toast.makeText(SignUpActivity.this, "Please make sure the values you entered are correct!", Toast.LENGTH_LONG).show();
            }
        });

    }


    private boolean validateFirstName() {
        String val = firstname.getText().toString().trim();
        if (val.isEmpty()) {
            firstname.setError("The field can not be empty!");
            return false;
        } else {
            firstname.setError(null);
            return true;
        }
    }

    private boolean validateLastName() {
        String val = lastname.getText().toString().trim();
        if (val.isEmpty()) {
            lastname.setError("The field can not be empty!");
            return false;
        } else {
            lastname.setError(null);
            return true;
        }
    }

    private boolean validatePhoneNumber() {
        String val = phoneNumber.getText().toString().trim();
        if (android.util.Patterns.PHONE.matcher(val).matches()) {
            phoneNumber.setError(null);
            return true;
        } else {
            phoneNumber.setError("Phone Number is not correct!");
            return false;
        }
    }

    private boolean validateEmail() {
        String val = email.getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+";
        if (val.isEmpty()) {
            email.setError("The field can not be empty!");
            return false;
        } else if (!val.matches(checkEmail)) {
            email.setError("Invalid email! ");
            return false;
        } else {
            email.setError(null);
            return true;
        }
    }

    public boolean validatePassword() {
        String val = password.getText().toString().trim();
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(val);
        if (!matcher.matches()) {

            password.setError("The password must contain at least 4 characters, at least 1 digit, a lowercase letter, a uppercase letter and a special character" +
                    " and it must not contain spaces."
            );
            return false;
        } else {
            password.setError(null);
            return true;
        }
    }

}