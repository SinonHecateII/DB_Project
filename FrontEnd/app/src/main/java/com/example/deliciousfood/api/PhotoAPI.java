package com.example.deliciousfood.api;

import com.example.deliciousfood.api.dto.LoginDTO;
import com.example.deliciousfood.api.dto.LoginResponseDTO;
import com.example.deliciousfood.api.dto.RegisterDTO;
import com.example.deliciousfood.api.dto.RegisterResponseDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PhotoAPI {
    final static String baseUrl = "https://test.loca.lt";

    static PhotoAPI create() {
        return getInstance().create(PhotoAPI.class);
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
        Image
     */

    @POST("/upload")
    Call<LoginResponseDTO> loginCall(@Body LoginDTO loginDTO);

    @POST("/session/register.php")
    Call<RegisterResponseDTO> registerCall(@Body RegisterDTO registerDTO);

}
