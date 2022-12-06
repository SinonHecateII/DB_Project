package com.example.deliciousfood.pages;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.deliciousfood.adapter.MyReviewAdapter;
import com.example.deliciousfood.api.DeliciousAPI;
import com.example.deliciousfood.api.dto.requestDTO.ReviewSearchUserIdDTO;
import com.example.deliciousfood.api.dto.responseDTO.ReviewResponseModel;
import com.example.deliciousfood.api.dto.responseDTO.ReviewSearchResponseDTO;
import com.example.deliciousfood.api.dto.responseDTO.ReviewSearchUserIdResponseDTO;
import com.example.deliciousfood.api.dto.responseDTO.ReviewSearchUserIdResult;
import com.example.deliciousfood.databinding.ActivityMyReviewBinding;
import com.example.deliciousfood.utils.SharedPreferenceHelper;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyReviewActivity extends AppCompatActivity {

    private ActivityMyReviewBinding binding;
    private DeliciousAPI deliciousAPI;
    private String userID;
    private MyReviewAdapter myReviewAdapter;
    private ArrayList<ReviewSearchUserIdResult> reviewResponseModels = new ArrayList<ReviewSearchUserIdResult>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        deliciousAPI = DeliciousAPI.create();

        binding = ActivityMyReviewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        myReviewAdapter = new MyReviewAdapter(this, reviewResponseModels);
        binding.rvMyReview.setAdapter(myReviewAdapter);


        userID = SharedPreferenceHelper.INSTANCE.getLoginID(getApplicationContext());

        deliciousAPI.reviewSearchUserIdCall(new ReviewSearchUserIdDTO(userID)).enqueue(new Callback<ReviewSearchUserIdResponseDTO>() {

            @Override
            public void onResponse(Call<ReviewSearchUserIdResponseDTO> call, Response<ReviewSearchUserIdResponseDTO> response) {
                if(response.isSuccessful()) {
                    ReviewSearchUserIdResponseDTO reviewSearchResponseDTO = response.body();

                    if(reviewSearchResponseDTO.getResult() != null && reviewSearchResponseDTO.getResult().size() > 0) {
                        reviewSearchResponseDTO.getResult().forEach(review -> {
                            reviewResponseModels.add(review);
                        });
                        myReviewAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<ReviewSearchUserIdResponseDTO> call, Throwable t) {

            }
        });

    }
}