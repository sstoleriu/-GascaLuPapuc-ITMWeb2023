package com.app.ecoshare.Retrofit;

import com.app.ecoshare.Models.Authenticate;
import com.app.ecoshare.Models.AuthenticateResponse;
import com.app.ecoshare.Models.Register;
import com.app.ecoshare.Models.RegisterResponse;
import com.app.ecoshare.Models.Report;
import com.app.ecoshare.Models.ReportResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Api {


    @POST("/api/v1/auth/register")
    Call<RegisterResponse> register(@Body Register dataRegister);


    @POST("/api/v1/auth/login")
    Call<AuthenticateResponse> authenticate(@Body Authenticate dataRegister);

    @POST("/api/v1/report/{userId}")
    Call<Report> addNewReport(@Body Report reportData, @Header("Authorization") String authToken,
                              @Path("userId")Integer userId);

    @POST("/api/v1/")
    Call<Void> function(@Header("Authorization") String authToken);

    @GET("/api/v1/report/allReports/android/user/{userId}")
    Call<List<ReportResponse>> getReports(@Header("Authorization") String authToken, @Path("userId")Integer userId);
}
