package com.example.deliciousfood.pages.session;

import androidx.annotation.NonNull;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.deliciousfood.R;
import com.example.deliciousfood.api.DeliciousAPI;
import com.example.deliciousfood.api.dto.requestDTO.RegisterDTO;
import com.example.deliciousfood.api.dto.responseDTO.RegisterResponseDTO;
import com.example.deliciousfood.utils.ParentActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends ParentActivity {
    private DeliciousAPI deliciousAPI;

    private EditText et_reg_id;
    private EditText et_reg_pw;
    private EditText et_reg_pw_check;
    private EditText et_reg_nickname;
    private EditText et_reg_email;
    private Button btn_reg_finish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setSupportActionBar(findViewById(R.id.toolbar_register));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        deliciousAPI = DeliciousAPI.create();

        et_reg_id = findViewById(R.id.et_reg_id);
        et_reg_pw = findViewById(R.id.et_reg_pw);
        et_reg_pw_check = findViewById(R.id.et_reg_pw_check);
        et_reg_nickname = findViewById(R.id.et_reg_nickname);
        et_reg_email = findViewById(R.id.et_reg_email);
        btn_reg_finish = findViewById(R.id.btn_reg_finish);

        btn_reg_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRegister();
            }
        });
    }


    private void onRegister() {
        String id = et_reg_id.getText().toString();
        String pw = et_reg_pw.getText().toString();
        String pwCheck = et_reg_pw_check.getText().toString();
        String nickName = et_reg_nickname.getText().toString();
        String email = et_reg_email.getText().toString();

        if (!pw.equals(pwCheck)) {
            Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
            return;
        }

        showProgress(RegisterActivity.this, "잠시만 기다려 주세요");

        RegisterDTO registerDTO = new RegisterDTO(id, pw, nickName, email);

        Call<RegisterResponseDTO> registerCall = deliciousAPI.registerCall(registerDTO);

        registerCall.enqueue(new Callback<RegisterResponseDTO>() {
            @Override
            public void onResponse(Call<RegisterResponseDTO> call, Response<RegisterResponseDTO> response) {
                hideProgress();
                if (response.isSuccessful()) {
                    RegisterResponseDTO registerResponseDTO = response.body();

                    switch (registerResponseDTO.getResult()) {
                        case "Duplicated id":
                            Toast.makeText(getApplicationContext(), "이미 존재하는 아이디 입니다.", Toast.LENGTH_SHORT).show();
                            break;

                        case "Duplicated nickname":
                            Toast.makeText(getApplicationContext(), "이미 존재하는 닉네임 입니다", Toast.LENGTH_SHORT).show();
                            break;

                        case "sign up success":
                            Toast.makeText(getApplicationContext(), "회원가입에 성공하였습니다", Toast.LENGTH_SHORT).show();
                            finish();
                            break;

                        case "sign up Error":
                            Toast.makeText(getApplicationContext(), "회원가입 오류입니다", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<RegisterResponseDTO> call, Throwable t) {
                hideProgress();
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