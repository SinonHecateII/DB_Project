package com.example.deliciousfood.pages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deliciousfood.R;
import com.example.deliciousfood.adapter.MyReviewAdapter;
import com.example.deliciousfood.api.DeliciousAPI;
import com.example.deliciousfood.api.dto.requestDTO.ReviewEditDTO;
import com.example.deliciousfood.api.dto.requestDTO.ReviewIdDTO;
import com.example.deliciousfood.api.dto.requestDTO.ReviewSearchUserIdDTO;
import com.example.deliciousfood.api.dto.responseDTO.OnlyResultDTO;
import com.example.deliciousfood.api.dto.responseDTO.ReviewResponseModel;
import com.example.deliciousfood.api.dto.responseDTO.ReviewSearchResponseDTO;
import com.example.deliciousfood.api.dto.responseDTO.ReviewSearchUserIdResponseDTO;
import com.example.deliciousfood.api.dto.responseDTO.ReviewSearchUserIdResult;
import com.example.deliciousfood.databinding.ActivityMyReviewBinding;
import com.example.deliciousfood.utils.Constants;
import com.example.deliciousfood.utils.ParentActivity;
import com.example.deliciousfood.utils.SharedPreferenceHelper;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyReviewActivity extends ParentActivity {

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
                if (response.isSuccessful()) {
                    ReviewSearchUserIdResponseDTO reviewSearchResponseDTO = response.body();

                    if (reviewSearchResponseDTO.getResult() != null && reviewSearchResponseDTO.getResult().size() > 0) {
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


        myReviewAdapter.setOnItemClickListener(new MyReviewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getApplicationContext(), RestaurantActivity.class);
                intent.putExtra(Constants.INTENT_EXTRA_RESTAURANT_ID, Integer.parseInt(reviewResponseModels.get(position).getRestaurantID()));
                startActivity(intent);
            }
        });

        myReviewAdapter.setOnMoreClickListener(new MyReviewAdapter.OnMoreClickListener() {
            @Override
            public void onMoreClick(int position, @NonNull ImageView imageView) {
                PopupMenu popupMenu = new PopupMenu(MyReviewActivity.this, imageView);
                popupMenu.getMenuInflater().inflate(R.menu.menu_my_review, popupMenu.getMenu());
                popupMenu.show();

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.menu_edit:
                                openReviewEditPopup(position);
                                return true;

                            case R.id.menu_delete:
                                onReviewDelete(Integer.parseInt(reviewResponseModels.get(position).getReviewID()));
                                return true;
                        }

                        return false;
                    }
                });
            }
        });
    }

    private void openReviewEditPopup(int position) {
        Dialog reviewDialog = new Dialog(this);
        reviewDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        reviewDialog.setContentView(R.layout.dialog_review);

        WindowManager.LayoutParams params = reviewDialog.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;

        ((TextView) reviewDialog.findViewById(R.id.tv_review_restaurant_name)).setText(reviewResponseModels.get(position).getRestaurantName() + " 리뷰 수정하기");

        ((Button) reviewDialog.findViewById(R.id.btn_review_finish)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String reviewContent = ((TextView) reviewDialog.findViewById(R.id.et_review_content)).getText().toString();
                int score = (int) ((RatingBar) reviewDialog.findViewById(R.id.rb_review_rating)).getRating();

                onReviewEdit(position, Integer.parseInt(reviewResponseModels.get(position).getReviewID()), score, reviewContent);


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

    private void onReviewEdit(int position, int reviewID, int score, String content) {
        ReviewEditDTO reviewEditDTO = new ReviewEditDTO(reviewID, score, content);
        deliciousAPI.reviewEditCall(reviewEditDTO).enqueue(new Callback<OnlyResultDTO>() {
            @Override
            public void onResponse(Call<OnlyResultDTO> call, Response<OnlyResultDTO> response) {
                if (response.isSuccessful()) {
                    showShortToast("리뷰 수정 완료");
                    reviewResponseModels.get(position).setScore(score + "");
                    reviewResponseModels.get(position).setContent(content);

                    myReviewAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<OnlyResultDTO> call, Throwable t) {

            }
        });
    }


    private void onReviewDelete(int reviewId) {
        deliciousAPI.reviewDeleteCall(new ReviewIdDTO(reviewId)).enqueue(new Callback<OnlyResultDTO>() {
            @Override
            public void onResponse(Call<OnlyResultDTO> call, Response<OnlyResultDTO> response) {
                if (response.isSuccessful()) {
                    showShortToast("리뷰가 삭제되었습니다");
                    // removeIf - 람다식
                    reviewResponseModels.removeIf(review -> review.getReviewID().equals(String.valueOf(reviewId)));
                    myReviewAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<OnlyResultDTO> call, Throwable t) {

            }
        });
    }

}