package com.example.deliciousfood.pages;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.deliciousfood.R;
import com.example.deliciousfood.adapter.ReviewAdapter;
import com.example.deliciousfood.api.DeliciousAPI;
import com.example.deliciousfood.api.dto.requestDTO.ResIdSearchDTO;
import com.example.deliciousfood.api.dto.requestDTO.ReviewDTO;
import com.example.deliciousfood.api.dto.requestDTO.ReviewSearchResIdDTO;
import com.example.deliciousfood.api.dto.responseDTO.OnlyResultDTO;
import com.example.deliciousfood.api.dto.responseDTO.RestaurantResponseModel;
import com.example.deliciousfood.api.dto.responseDTO.ReviewSearchResponseDTO;
import com.example.deliciousfood.databinding.ActivityRestaurantBinding;
import com.example.deliciousfood.models.ReviewModel;
import com.example.deliciousfood.utils.Constants;
import com.example.deliciousfood.utils.ParentActivity;
import com.example.deliciousfood.utils.SharedPreferenceHelper;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantActivity extends ParentActivity {
    private DeliciousAPI deliciousAPI = DeliciousAPI.create();
    private String TAG = "RestaurantActivity";
    private ActivityRestaurantBinding binding;
    private int restaurantId = -1;
    private ArrayList<ReviewModel> reviewModels = new ArrayList<>();
    private ReviewAdapter reviewAdapter;
    private ResIdSearchDTO resIdSearchDTO;
    private RestaurantResponseModel restaurantModel;
    private String currentUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRestaurantBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        currentUserID = SharedPreferenceHelper.INSTANCE.getLoginID(getApplicationContext());

        reviewAdapter = new ReviewAdapter(getApplicationContext(), reviewModels);

        restaurantId = getIntent().getIntExtra(Constants.INTENT_EXTRA_RESTAURANT_ID, -1);
        if (restaurantId == -1) {
            showShortToast("restaurantId 전달 에러");
            finish();
        }
        Log.d(TAG, "onCreate: restaurantId: " + restaurantId);


        binding.btnReviewAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openWriteReviewDialog();
            }
        });

        binding.ivRestaurantEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RestaurantEditActivity.class);
                intent.putExtra(Constants.INTENT_EXTRA_RESTAURANT_ID, restaurantId);
                startActivity(intent);
                finish();
            }
        });

        binding.ivRestaurantDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deliciousAPI.restaurantDeleteCall(new ResIdSearchDTO(restaurantId)).enqueue(new Callback<OnlyResultDTO>() {
                    @Override
                    public void onResponse(Call<OnlyResultDTO> call, Response<OnlyResultDTO> response) {
                        showShortToast("가게 삭제 성공");
                        finish();
                    }

                    @Override
                    public void onFailure(Call<OnlyResultDTO> call, Throwable t) {

                    }
                });
            }
        });

        getRestaurantModel();
        getImage();
    }

    private void openWriteReviewDialog() {
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
                int score = (int) ((RatingBar) reviewDialog.findViewById(R.id.rb_review_rating)).getRating();

                onReviewAdd(reviewContent, score);


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


    private void onReviewAdd(String content, int score) {
        ReviewDTO reviewDTO = new ReviewDTO(
                content,
                score,
                Integer.parseInt(restaurantModel.getRestaurantID()),
                SharedPreferenceHelper.INSTANCE.getLoginID(getApplicationContext()),
                System.currentTimeMillis()
        );

        deliciousAPI.reviewAddCall(reviewDTO).enqueue(new Callback<OnlyResultDTO>() {
            @Override
            public void onResponse(Call<OnlyResultDTO> call, Response<OnlyResultDTO> response) {
                if (response.isSuccessful()) {
                    showShortToast(response.body().getResult());

                    reviewModels.add(new ReviewModel(
                            SharedPreferenceHelper.INSTANCE.getNickname(getApplicationContext()),
                            score,
                            content));
                    reviewAdapter.notifyDataSetChanged();

                } else {
                    showShortToast("리뷰 등록 실패");
                }
            }

            @Override
            public void onFailure(Call<OnlyResultDTO> call, Throwable t) {
                showShortToast("리뷰 등록 실패");
            }
        });

    }


    private void setUpReviewRecyclerView() {
        binding.rvReview.setAdapter(reviewAdapter);

        Log.d(TAG, "가게 idx = " + restaurantId);

        deliciousAPI.reviewSearchCall(new ReviewSearchResIdDTO(restaurantId)).enqueue(new Callback<ReviewSearchResponseDTO>() {
            @Override
            public void onResponse(Call<ReviewSearchResponseDTO> call, Response<ReviewSearchResponseDTO> response) {
                if (response.isSuccessful()) {
                    ReviewSearchResponseDTO reviewSearchResponseDTO = response.body();
                    if (reviewSearchResponseDTO != null) {
                        Log.d(TAG, "onResponse: size = " + reviewSearchResponseDTO.getResult().size());

                        reviewSearchResponseDTO.getResult().forEach(review -> {
                            reviewModels.add(new ReviewModel(review.getNickname(), Integer.parseInt(review.getScore()), review.getContent()));
                        });

                        reviewAdapter.notifyDataSetChanged();
                    }
                } else {
                    Log.d(TAG, "onResponse: reviewSearchCall 실패");
                    ;
                }
            }

            @Override
            public void onFailure(Call<ReviewSearchResponseDTO> call, Throwable t) {

            }
        });

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
        Call<RestaurantResponseModel> restaurantIdCall = deliciousAPI.restaurantSearchIdCall(resIdSearchDTO);

        restaurantIdCall.enqueue(new Callback<RestaurantResponseModel>() {
            @Override
            public void onResponse(Call<RestaurantResponseModel> call, Response<RestaurantResponseModel> response) {
                if (response.isSuccessful()) {
                    restaurantModel = response.body();
                    if (restaurantModel != null) {
                        binding.tvRestaurantMood.setText(restaurantModel.getMood());
                        binding.tvRestaurantTitle.setText(restaurantModel.getName());
                        binding.tvRestaurantLocation.setText(restaurantModel.getLocation());

                        setUpReviewRecyclerView();

                        if(currentUserID.equals(restaurantModel.getWriterID())) {
                            binding.ivRestaurantDelete.setVisibility(View.VISIBLE);
                            binding.ivRestaurantEdit.setVisibility(View.VISIBLE);
                        } else {
                            binding.ivRestaurantDelete.setVisibility(View.INVISIBLE);
                            binding.ivRestaurantEdit.setVisibility(View.INVISIBLE);
                        }
                    } else {
                        showShortToast("식당 정보를 불러오는데 실패했습니다.");
                    }
                } else {
                    showShortToast("식당 정보를 불러오는데 실패했습니다.");
                }
            }

            @Override
            public void onFailure(Call<RestaurantResponseModel> call, Throwable t) {

            }
        });
    }
}