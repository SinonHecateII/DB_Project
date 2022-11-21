package com.example.deliciousfood.pages;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.deliciousfood.api.DeliciousAPI;
import com.example.deliciousfood.api.dto.requestDTO.ResIdSearchDTO;
import com.example.deliciousfood.api.dto.responseDTO.Result;
import com.example.deliciousfood.databinding.ActivityRestaurantBinding;
import com.example.deliciousfood.utils.Constants;
import com.example.deliciousfood.utils.ParentActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantActivity extends ParentActivity {
    private DeliciousAPI deliciousAPI = DeliciousAPI.create();

    private ActivityRestaurantBinding binding;
    private int restaurantId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRestaurantBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        restaurantId = getIntent().getIntExtra(Constants.INTENT_EXTRA_RESTAURANT_ID, -1);
        if (restaurantId == -1) {
            showShortToast("restaurantId 전달 에러");
            finish();
        }

        getRestaurantModel();
        getImage();
    }

    private void getImage() {
        try {
            // Glide 로 서버에서 이미지를 가져올 때 간혹 서버에 이미지가 없을 경우 nullPointException 을 일으키기 때문에
            // try - catch 로 잡아줌
            String imageUri = "http://210.125.212.207/shareimage/" + restaurantId + "_img0.jpg";
            Glide.with(getApplicationContext()).load(imageUri).into(binding.ivRestaurantImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getRestaurantModel() {
        ResIdSearchDTO resIdSearchDTO = new ResIdSearchDTO(restaurantId);
        Call<Result> restaurantIdCall = deliciousAPI.restaurantSearchIdCall(resIdSearchDTO);

        restaurantIdCall.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (response.isSuccessful()) {
                    Result result = response.body();
                    if (result != null) {
                        binding.tvRestaurantMood.setText(result.getMood());
                        binding.tvRestaurantTitle.setText(result.getName());
                        binding.tvRestaurantLocation.setText(result.getLocation());
                    } else {
                        showShortToast("식당 정보를 불러오는데 실패했습니다.");
                    }
                } else {
                    showShortToast("식당 정보를 불러오는데 실패했습니다.");
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

            }
        });
    }
}