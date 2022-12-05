package com.example.deliciousfood.api;


import com.example.deliciousfood.api.dto.requestDTO.LoginDTO;
import com.example.deliciousfood.api.dto.requestDTO.ResIdSearchDTO;
import com.example.deliciousfood.api.dto.requestDTO.ResSearchDTO;
import com.example.deliciousfood.api.dto.requestDTO.ReviewDTO;
import com.example.deliciousfood.api.dto.responseDTO.LoginResponseDTO;
import com.example.deliciousfood.api.dto.requestDTO.RegisterDTO;
import com.example.deliciousfood.api.dto.responseDTO.OnlyResultDTO;
import com.example.deliciousfood.api.dto.responseDTO.RegisterResponseDTO;
import com.example.deliciousfood.api.dto.responseDTO.RestaurantResponseDTO;
import com.example.deliciousfood.api.dto.responseDTO.RestaurantResponseModel;
import com.example.deliciousfood.api.dto.responseDTO.ReviewSearchResponseDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
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

    @POST("/session/edit_account.php")
    Call<RegisterResponseDTO> EditNicknameCall(@Body RegisterDTO registerDTO);


    /*
        Restaurant
     */
    @POST("/restaurant/restaurant_search.php")
    Call<RestaurantResponseDTO> restaurantSearchCall(@Body ResSearchDTO location);

    @POST("/restaurant/restaurant_search_id.php")
    Call<RestaurantResponseModel> restaurantSearchIdCall(@Body ResIdSearchDTO restaurantID);


    /*
        Review
     */
    @POST("/review/review_search_restaurant_id.php")
    Call<ReviewSearchResponseDTO> reviewSearchCall(@Body String restaurantID);

    @POST("/review/review_add.php")
    Call<OnlyResultDTO> reviewAddCall(@Body ReviewDTO reviewDTO);
}