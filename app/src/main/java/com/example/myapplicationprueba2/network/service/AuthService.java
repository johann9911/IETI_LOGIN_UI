package com.example.myapplicationprueba2.network.service;



import com.example.myapplicationprueba2.network.dto.LoginDto;
import com.example.myapplicationprueba2.network.dto.TokenDto;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

public interface AuthService {
    @POST("auth")
    Observable<TokenDto> auth(@Body LoginDto loginDto);
}
