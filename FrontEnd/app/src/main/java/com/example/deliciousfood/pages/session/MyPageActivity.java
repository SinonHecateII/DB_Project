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
import com.example.deliciousfood.api.DeliciousAPI;
import com.example.deliciousfood.api.dto.requestDTO.DeleteAccountDTO;
import com.example.deliciousfood.api.dto.requestDTO.RegisterDTO;
import com.example.deliciousfood.api.dto.responseDTO.OnlyResultDTO;
import com.example.deliciousfood.api.dto.responseDTO.RegisterResponseDTO;
import com.example.deliciousfood.databinding.ActivityMyPageBinding;
import com.example.deliciousfood.utils.SharedPreferenceHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyPageActivity extends AppCompatActivity {
    private ActivityMyPageBinding binding;
    private DeliciousAPI deliciousAPI;
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
        String name = SharedPreferenceHelper.INSTANCE.getNickname(getApplicationContext());
        UserName = (EditText) findViewById(R.id.tv_mypage_nickname);
        UserName.setText(name);

        // 엔터키 방지
        UserName.setOnKeyListener(new View.OnKeyListener(){
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event){
                if(keyCode == event.KEYCODE_ENTER) return true;
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
        * 작성한 리뷰 확인 부분 [작성 중]
        * */
        Written_Review = findViewById(R.id.btn_mypage_review);
        int review_num = 0;  //작성한 리뷰 수

        Written_Review.setText("총" + String.valueOf(review_num) + "건");

        /*
        * 회원 탈퇴 버튼
        * */
        DeleteAccountBtn = (Button) findViewById(R.id.tv_mypage_withdrawal);
        DeleteAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteAccount();
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

    //계정 삭제 버튼 기능
    private void DeleteAccount(){
        String id = SharedPreferenceHelper.INSTANCE.getLoginID(getApplicationContext());

        DeleteAccountDTO deleteAccountDTO = new DeleteAccountDTO(id);

        Call<OnlyResultDTO> deleteAccountCall = deliciousAPI.deleteAccountCall(deleteAccountDTO);
        deleteAccountCall.enqueue(new Callback<OnlyResultDTO>() {
            @Override
            public void onResponse(Call<OnlyResultDTO> call, Response<OnlyResultDTO> response) {
                if(response.isSuccessful()){
                    OnlyResultDTO onlyResultDTO = response.body();

                    switch (onlyResultDTO.getResult()){
                        case "Delete success":
                            Toast.makeText(getApplicationContext(), "계정을 성공적으로 삭제하였습니다.", Toast.LENGTH_SHORT).show();

                            //재 로그인을 위한 앱 재시작
                            PackageManager packageManager = getPackageManager();
                            Intent intent = packageManager.getLaunchIntentForPackage(getPackageName());
                            ComponentName componentName = intent.getComponent();
                            Intent mainIntent = Intent.makeRestartActivityTask(componentName);
                            startActivity(mainIntent);
                            System.exit(0);

                            break;
                        case "Delete fail":
                            Toast.makeText(getApplicationContext(), "계정 삭제에 실패하였습니다..", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<OnlyResultDTO> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "계정 삭제에 알 수 없는 오류가 발생하였습니다.", Toast.LENGTH_SHORT).show();
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