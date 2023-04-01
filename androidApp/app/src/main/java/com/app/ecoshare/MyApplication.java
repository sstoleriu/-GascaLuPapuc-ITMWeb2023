package com.app.ecoshare;

import android.app.Application;

public class MyApplication extends Application {
    private static String userToken;
    private static Integer userId;

    public Integer getUserId(){
        return userId;
    }

    public void setUserId(Integer id){userId = id;}

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String token) {
        userToken = token;
    }

}
