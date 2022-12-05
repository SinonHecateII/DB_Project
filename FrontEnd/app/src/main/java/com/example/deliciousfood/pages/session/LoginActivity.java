package com.example.deliciousfood.pages.session;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.deliciousfood.R;
import com.example.deliciousfood.api.DeliciousAPI;
import com.example.deliciousfood.api.dto.requestDTO.LoginDTO;
import com.example.deliciousfood.api.dto.responseDTO.LoginResponseDTO;
import com.example.deliciousfood.pages.MainActivity;
import com.example.deliciousfood.utils.ParentActivity;
import com.example.deliciousfood.utils.SharedPreferenceHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends ParentActivity {
    private DeliciousAPI deliciousAPI;

    private EditText et_login_id;
    private EditText et_login_pw;
    private Button btn_register;
    private Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_register);
        et_login_id = findViewById(R.id.et_login_id);
        et_login_pw = findViewById(R.id.et_login_pw);

        deliciousAPI = DeliciousAPI.create();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLogin();
            }
        });


        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    private void onLogin() {
        showProgress(LoginActivity.this, "잠시만 기다려주세요");
        String id = et_login_id.getText().toString();
        String pw = et_login_pw.getText().toString();

        LoginDTO loginDTO = new LoginDTO(id, pw);

        Call<LoginResponseDTO> loginCall = deliciousAPI.loginCall(loginDTO);

        loginCall.enqueue(new Callback<LoginResponseDTO>() {
            @Override
            public void onResponse(Call<LoginResponseDTO> call, Response<LoginResponseDTO> response) {
                hideProgress();
                if (response.isSuccessful()) {
                    LoginResponseDTO responseDTO = response.body();

                    if (responseDTO.isSuccess()) {
                        SharedPreferenceHelper.INSTANCE.setLoginID(getApplicationContext(), responseDTO.getId());
                        SharedPreferenceHelper.INSTANCE.setLoginPW(getApplicationContext(), responseDTO.getPassword());
                        SharedPreferenceHelper.INSTANCE.setLoginPW(getApplicationContext(), responseDTO.getEmail());
                        SharedPreferenceHelper.INSTANCE.setNickname(getApplicationContext(), responseDTO.getName());

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "아이디 혹은 이메일이 잘못되었습니다", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponseDTO> call, Throwable t) {
                hideProgress();
            }
        });
    }
}