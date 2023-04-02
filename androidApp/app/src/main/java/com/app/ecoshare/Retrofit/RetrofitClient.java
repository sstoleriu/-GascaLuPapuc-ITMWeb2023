package com.app.ecoshare.Retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class RetrofitClient {
    private static Retrofit retrofit;
    private static final String BASE_URL_AUTH = "http://192.168.1.149:8081/";
    private static final String BASE_URL_ECO = "http://192.168.1.149:8082/";


    public static Retrofit getRetrofitSIGNINInstance() {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL_AUTH).addConverterFactory(GsonConverterFactory.create()).build();
        return retrofit;
    }

    public static Retrofit getRetrofitEcoInstance() {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL_ECO).addConverterFactory(GsonConverterFactory.create()).build();
        return retrofit;
    }

}
