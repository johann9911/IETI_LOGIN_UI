package com.example.myapplicationprueba2.network.dto;

public class LoginDto {
    String email;
    String password;
    public LoginDto( String email, String password){
        this.email = email;
        this.password=password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
