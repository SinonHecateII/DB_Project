package com.example.deliciousfood.pages.session;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.deliciousfood.R;
import com.example.deliciousfood.api.dto.responseDTO.LoginResponseDTO;
import com.example.deliciousfood.databinding.ActivityMyPageBinding;
import com.example.deliciousfood.utils.SharedPreferenceHelper;

public class MyPageActivity extends AppCompatActivity {
    private ActivityMyPageBinding binding;
    TextView UserName;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMyPageBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbarMypage);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        editor = pref.edit();

        // 유저 Nickname 가져오기
        String name = SharedPreferenceHelper.INSTANCE.getNickname(getApplicationContext());
        UserName = (TextView) findViewById(R.id.tv_mypage_nickname);
        UserName.setText(name);

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}