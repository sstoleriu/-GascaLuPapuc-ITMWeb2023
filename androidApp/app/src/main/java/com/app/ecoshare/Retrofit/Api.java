package com.app.ecoshare.Retrofit;

import com.app.ecoshare.Models.Authenticate;
import com.app.ecoshare.Models.AuthenticateResponse;
import com.app.ecoshare.Models.Register;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Api {


    @POST("/api/v1/auth/register")
    Call<Register> register(@Body Register dataRegister);


    @POST("/api/v1/auth/login")
    Call<AuthenticateResponse> authenticate(@Body Authenticate dataRegister);

}
