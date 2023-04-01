package com.app.ecoshare.Models;


public class Register {

    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String phoneNumber;

    public Register(String firstname, String lastname, String email, String password, String phoneNumber) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }


}