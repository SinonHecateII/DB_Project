package com.example.deliciousfood.pages.session;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.deliciousfood.R;
import com.example.deliciousfood.adapter.MyReviewAdapter;
import com.example.deliciousfood.api.DeliciousAPI;
import com.example.deliciousfood.api.dto.requestDTO.DeleteAccountDTO;
import com.example.deliciousfood.api.dto.requestDTO.LoginDTO;
import com.example.deliciousfood.api.dto.requestDTO.RegisterDTO;
import com.example.deliciousfood.api.dto.requestDTO.ReviewSearchResIdDTO;
import com.example.deliciousfood.api.dto.requestDTO.ReviewSearchUserIdDTO;
import com.example.deliciousfood.api.dto.requestDTO.WriterIdDTO;
import com.example.deliciousfood.api.dto.responseDTO.OnlyResultDTO;
import com.example.deliciousfood.api.dto.responseDTO.RegisterResponseDTO;
import com.example.deliciousfood.api.dto.responseDTO.RestaurantResponseDTO;
import com.example.deliciousfood.api.dto.responseDTO.ReviewSearchUserIdResponseDTO;
import com.example.deliciousfood.api.dto.responseDTO.ReviewSearchUserIdResult;
import com.example.deliciousfood.databinding.ActivityMyPageBinding;
import com.example.deliciousfood.pages.MyReviewActivity;
import com.example.deliciousfood.utils.PopupActivity;
import com.example.deliciousfood.utils.SharedPreferenceHelper;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyPageActivity extends AppCompatActivity {
    private ActivityMyPageBinding binding;
    private DeliciousAPI deliciousAPI;
    private String name;
    private String userID;
    EditText UserName;
    Button Edit_Nickname_btn;
    Button Written_Review;
    Button DeleteAccountBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMyPageBinding.inflate(getLayoutInflater());
        deliciousAPI = DeliciousAPI.create();

        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbarMypage);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*
         * 유저 닉네임 확인 및 수정 부분
         * */
        // 유저 Nickname 가져오기
        name = SharedPreferenceHelper.INSTANCE.getNickname(getApplicationContext());
        UserName = (EditText) findViewById(R.id.tv_mypage_nickname);
        UserName.setText(name);

        // 엔터키 방지
        UserName.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == event.KEYCODE_ENTER) return true;
                return false;
            }
        });

        Edit_Nickname_btn = (Button) findViewById(R.id.EditNickname_btn);
        // 닉네임 수정 완료 버튼
        Edit_Nickname_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onChange();
            }
        });

        /*
         * 작성한 리뷰 개수 확인 부분
         * */
        Written_Review = findViewById(R.id.btn_mypage_review);
        userID = SharedPreferenceHelper.INSTANCE.getLoginID(getApplicationContext());

        deliciousAPI.reviewSearchUserIdCall(new ReviewSearchUserIdDTO(userID)).enqueue(new Callback<ReviewSearchUserIdResponseDTO>() {
            @Override
            public void onResponse(Call<ReviewSearchUserIdResponseDTO> call, Response<ReviewSearchUserIdResponseDTO> response) {
                try {
                    if (response.isSuccessful()) {
                        ReviewSearchUserIdResponseDTO reviewSearchResponseDTO = response.body();
                        Written_Review.setText("총 " + String.valueOf(reviewSearchResponseDTO.getResult().size()) + "건");
                    }
                }catch (Exception e){
                    Written_Review.setText("총 0건");
                }
            }

            @Override
            public void onFailure(Call<ReviewSearchUserIdResponseDTO> call, Throwable t) {

            }
        });


        //작성한 리뷰 목록 보러가는 버튼
        Written_Review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MyReviewActivity.class));
            }
        });

        //작성한 가게 개수 설정
        deliciousAPI.restaurantSearchWriterIdCall(new WriterIdDTO(userID)).enqueue(new Callback<RestaurantResponseDTO>() {
            @Override
            public void onResponse(Call<RestaurantResponseDTO> call, Response<RestaurantResponseDTO> response) {
                try {
                    if (response.isSuccessful()) {
                        RestaurantResponseDTO restaurantResponseDTO = response.body();
                        binding.btnMypageRestaurant.setText("총 " + String.valueOf(restaurantResponseDTO.getResult().size()) + "건");
                    }
                } catch (Exception e) {
                    binding.btnMypageRestaurant.setText("총 0건");
                }
            }

            @Override
            public void onFailure(Call<RestaurantResponseDTO> call, Throwable t) {

            }
        });
        binding.btnMypageRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MyRestaurantActivity.class));
            }
        });


        /*
         * 회원 탈퇴 버튼
         * */
        DeleteAccountBtn = (Button) findViewById(R.id.tv_mypage_withdrawal);
        DeleteAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyPageActivity.this, PopupActivity.class));
                //
            }
        });


        /*
            비밀번호 변경
         */
        binding.tvMypagePasswordChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDialog passwordChangeDialog = new BottomSheetDialog(MyPageActivity.this);
                passwordChangeDialog.setContentView(R.layout.bottom_dialog_change_passowrd);
                passwordChangeDialog.show();

                EditText etPassWordChange = passwordChangeDialog.findViewById(R.id.et_password_change);

                passwordChangeDialog.findViewById(R.id.btn_password_change_ok).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String password = etPassWordChange.getText().toString();
                        if(password.length() == 0) {
                            Toast.makeText(MyPageActivity.this, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        LoginDTO loginDTO = new LoginDTO(userID, password);
                        deliciousAPI.passwordChangeCall(loginDTO).enqueue(new Callback<OnlyResultDTO>() {
                            @Override
                            public void onResponse(Call<OnlyResultDTO> call, Response<OnlyResultDTO> response) {
                                if(response.isSuccessful()) {
                                    Toast.makeText(MyPageActivity.this, response.body().getResult(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<OnlyResultDTO> call, Throwable t) {
                                Toast.makeText(MyPageActivity.this, "비밀번호 변경 실패", Toast.LENGTH_SHORT).show();
                            }
                        });

                        passwordChangeDialog.dismiss();
                    }
                });
            }
        });
    }

    //닉네임 수정 버튼 기능
    private void onChange() {
        String nickName = UserName.getText().toString();
        String id = SharedPreferenceHelper.INSTANCE.getLoginID(getApplicationContext());
        String pw = SharedPreferenceHelper.INSTANCE.getLoginPW(getApplicationContext());
        String email = SharedPreferenceHelper.INSTANCE.getLoginEmail(getApplicationContext());

        RegisterDTO registerDTO = new RegisterDTO(id, pw, nickName, email);

        Call<RegisterResponseDTO> EditNicknameCall = deliciousAPI.EditNicknameCall(registerDTO);
        EditNicknameCall.enqueue(new Callback<RegisterResponseDTO>() {
            @Override
            public void onResponse(Call<RegisterResponseDTO> call, Response<RegisterResponseDTO> response) {
                if (response.isSuccessful()) {
                    RegisterResponseDTO registerResponseDTO = response.body();

                    switch (registerResponseDTO.getResult()) {
                        case "Duplicated nickname":
                            Toast.makeText(getApplicationContext(), "이미 존재하는 닉네임 입니다", Toast.LENGTH_SHORT).show();
                            break;

                        case "Update success":
                            Toast.makeText(getApplicationContext(), "닉네임 변경에 성공하였습니다", Toast.LENGTH_SHORT).show();
                            SharedPreferenceHelper.INSTANCE.setNickname(getApplicationContext(), nickName);
                            finish();
                            break;

                        case "Update fail":
                            Toast.makeText(getApplicationContext(), "닉네임 변경에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<RegisterResponseDTO> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "문제가 발생했습니다.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}