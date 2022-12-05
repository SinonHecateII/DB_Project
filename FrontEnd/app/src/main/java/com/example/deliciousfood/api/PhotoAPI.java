package com.example.deliciousfood.api;

import com.example.deliciousfood.api.dto.requestDTO.LoginDTO;
import com.example.deliciousfood.api.dto.responseDTO.LoginResponseDTO;
import com.example.deliciousfood.api.dto.requestDTO.RegisterDTO;
import com.example.deliciousfood.api.dto.responseDTO.RegisterResponseDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface PhotoAPI {
    final static String baseUrl = "https://dbp6763.loca.lt";

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
    @Multipart
    @POST("/upload")
    Call<ResponseBody> uploadImage(@Part MultipartBody.Part img);



    @POST("/upload")
    Call<LoginResponseDTO> loginCall(@Body LoginDTO loginDTO);

    @POST("/session/register.php")
    Call<RegisterResponseDTO> registerCall(@Body RegisterDTO registerDTO);

}
