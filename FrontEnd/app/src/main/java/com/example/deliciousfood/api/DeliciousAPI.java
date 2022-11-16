package com.example.deliciousfood.api;


import com.example.deliciousfood.api.dto.requestDTO.LoginDTO;
import com.example.deliciousfood.api.dto.requestDTO.ResSearchDTO;
import com.example.deliciousfood.api.dto.responseDTO.LoginResponseDTO;
import com.example.deliciousfood.api.dto.requestDTO.RegisterDTO;
import com.example.deliciousfood.api.dto.responseDTO.RegisterResponseDTO;
import com.example.deliciousfood.api.dto.responseDTO.RestaurantResponseDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface DeliciousAPI {
    static String baseUrl = "http://dbp6763.dothome.co.kr";

    static DeliciousAPI create() {
        return getInstance().create(DeliciousAPI.class);
    }

    static Retrofit getInstance() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }


    /*
        Session
     */

    @POST("/session/login.php")
    Call<LoginResponseDTO> loginCall(@Body LoginDTO loginDTO);

    @POST("/session/register.php")
    Call<RegisterResponseDTO> registerCall(@Body RegisterDTO registerDTO);


    /*
        Restaurant
     */
    @POST("/restaurant/restaurant_search.php")
    Call<RestaurantResponseDTO> restaurantSearchCall(@Body ResSearchDTO location);
}