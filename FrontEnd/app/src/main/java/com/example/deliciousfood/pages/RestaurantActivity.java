package com.example.deliciousfood.pages;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.deliciousfood.R;
import com.example.deliciousfood.adapter.ReviewAdapter;
import com.example.deliciousfood.api.DeliciousAPI;
import com.example.deliciousfood.api.dto.requestDTO.ResIdSearchDTO;
import com.example.deliciousfood.api.dto.responseDTO.Result;
import com.example.deliciousfood.databinding.ActivityRestaurantBinding;
import com.example.deliciousfood.models.ReviewModel;
import com.example.deliciousfood.utils.Constants;
import com.example.deliciousfood.utils.ParentActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantActivity extends ParentActivity {
    private DeliciousAPI deliciousAPI = DeliciousAPI.create();

    private ActivityRestaurantBinding binding;
    private int restaurantId = -1;
    private ArrayList<ReviewModel> reviewModels = new ArrayList<>();
    private ReviewAdapter reviewAdapter;
    private ResIdSearchDTO resIdSearchDTO;
    private Result restaurantModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRestaurantBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        reviewAdapter = new ReviewAdapter(getApplicationContext(), reviewModels);

        restaurantId = getIntent().getIntExtra(Constants.INTENT_EXTRA_RESTAURANT_ID, -1);
        if (restaurantId == -1) {
            showShortToast("restaurantId 전달 에러");
            finish();
        }

        binding.btnReviewAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openReview();
            }
        });

        getRestaurantModel();
        getImage();
        setUpReviewRecyclerView();
    }

    private void openReview() {
        Dialog reviewDialog = new Dialog(this);
        reviewDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        reviewDialog.setContentView(R.layout.dialog_review);

        WindowManager.LayoutParams params = reviewDialog.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;

        if (restaurantModel != null && restaurantModel.getName() != null) {
            ((TextView) reviewDialog.findViewById(R.id.tv_review_restaurant_name)).setText(restaurantModel.getName() + " 리뷰 등록하기");
        }

        ((Button) reviewDialog.findViewById(R.id.btn_review_finish)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String reviewContent = ((TextView) reviewDialog.findViewById(R.id.et_review_content)).getText().toString();

                // TODO: 2022-11-30 리뷰 등록하기


                reviewDialog.dismiss();
            }
        });

        ((RatingBar) reviewDialog.findViewById(R.id.rb_review_rating)).setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                ratingBar.setRating(v);
            }
        });

        reviewDialog.show();
    }




    private void setUpReviewRecyclerView() {
        binding.rvReview.setAdapter(reviewAdapter);

        reviewModels.add(new ReviewModel("사용자 1", 3, "맛있어요"));
        reviewModels.add(new ReviewModel("사용자 2", 4, "맛있어요222"));
        reviewModels.add(new ReviewModel("사용자 3", 1, "맛있어요333"));

        reviewAdapter.notifyDataSetChanged();
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
                    restaurantModel = response.body();
                    if (restaurantModel != null) {
                        binding.tvRestaurantMood.setText(restaurantModel.getMood());
                        binding.tvRestaurantTitle.setText(restaurantModel.getName());
                        binding.tvRestaurantLocation.setText(restaurantModel.getLocation());
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