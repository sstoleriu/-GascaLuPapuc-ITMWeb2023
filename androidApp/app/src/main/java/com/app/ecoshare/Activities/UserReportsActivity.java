package com.app.ecoshare.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.app.ecoshare.Adapters.ReportsCustomAdapter;
import com.app.ecoshare.Models.Report;
import com.app.ecoshare.Models.ReportResponse;
import com.app.ecoshare.MyApplication;
import com.app.ecoshare.R;
import com.app.ecoshare.Retrofit.Api;
import com.app.ecoshare.Retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserReportsActivity extends AppCompatActivity {
    MyApplication application = new MyApplication();
    String token = "Bearer " + application.getUserToken();
    ReportsCustomAdapter reportsCustomAdapter;
    Integer userId = application.getUserId();
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_reports);
        listView = findViewById(R.id.listView);
        List<ReportResponse> reportArrayList = new ArrayList<>();
        Api api = RetrofitClient.getRetrofitEcoInstance().create(Api.class);
        Call<List<ReportResponse>> call = api.getReports(token, userId);
        call.enqueue(new Callback<List<ReportResponse>>() {
            @Override
            public void onResponse(Call<List<ReportResponse>> call, Response<List<ReportResponse>> response) {

                assert response.body() != null;
                reportArrayList.addAll(response.body());
                Log.i("REPORTS LIST DONE!", reportArrayList.toString());
                reportsCustomAdapter = new ReportsCustomAdapter( UserReportsActivity.this, reportArrayList);
                listView.setAdapter(reportsCustomAdapter);
                reportsCustomAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<ReportResponse>> call, Throwable t) {
                Log.i("ERROR REPORTS LIST", t.toString());
            }
        });

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