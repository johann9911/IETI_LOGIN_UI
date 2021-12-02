package com.example.myapplicationprueba2.network.dto;

import java.util.Date;

public class TokenDto {
    String accessToken;

    Date expirationDate;

    public TokenDto( String accessToken, Date expirationDate )
    {
        this.accessToken = accessToken;
        this.expirationDate = expirationDate;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
}
