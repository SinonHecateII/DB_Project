package com.example.deliciousfood.pages.session;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.deliciousfood.adapter.RestaurantAdapter;
import com.example.deliciousfood.api.DeliciousAPI;
import com.example.deliciousfood.api.dto.requestDTO.WriterIdDTO;
import com.example.deliciousfood.api.dto.responseDTO.RestaurantResponseDTO;
import com.example.deliciousfood.databinding.ActivityMyRestaurantBinding;
import com.example.deliciousfood.models.RestaurantModel;
import com.example.deliciousfood.pages.RestaurantActivity;
import com.example.deliciousfood.utils.Constants;
import com.example.deliciousfood.utils.SharedPreferenceHelper;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyRestaurantActivity extends AppCompatActivity {
    private ActivityMyRestaurantBinding binding;
    private String userID;
    private DeliciousAPI deliciousAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyRestaurantBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        deliciousAPI = DeliciousAPI.create();
        userID = SharedPreferenceHelper.INSTANCE.getLoginID(getApplicationContext());

        ArrayList<RestaurantModel> restaurantModels = new ArrayList<RestaurantModel>();
        RestaurantAdapter restaurantAdapter = new RestaurantAdapter(this, restaurantModels);

        binding.rvMyRestaurant.setAdapter(restaurantAdapter);


        deliciousAPI.restaurantSearchWriterIdCall(new WriterIdDTO(userID)).enqueue(new Callback<RestaurantResponseDTO>() {
            @Override
            public void onResponse(Call<RestaurantResponseDTO> call, Response<RestaurantResponseDTO> response) {
                if (response.isSuccessful()) {
                    RestaurantResponseDTO restaurantResponseDTO = response.body();
                    if (restaurantResponseDTO.getResult() != null && restaurantResponseDTO.getResult().size() > 0) {
                        //    var restaurantId:Int,
                        //    var name: String,
                        //    var photoCnt: Int,
                        //    var mood: String,

                        restaurantResponseDTO.getResult().forEach(it -> {
                            restaurantModels.add(new RestaurantModel(Integer.parseInt(it.getRestaurantID()), it.getName(), Integer.parseInt(it.getPhotoCnt()), it.getMood(), new ArrayList<>()));
                        });

                        restaurantAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<RestaurantResponseDTO> call, Throwable t) {

            }
        });


        restaurantAdapter.setOnItemClickListener(new RestaurantAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull RestaurantModel model) {
                Intent intent = new Intent(getApplicationContext(), RestaurantActivity.class);
                intent.putExtra(Constants.INTENT_EXTRA_RESTAURANT_ID, model.getRestaurantId());
                startActivity(intent);
            }
        });


    }
}