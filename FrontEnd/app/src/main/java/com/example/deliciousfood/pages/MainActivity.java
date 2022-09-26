package com.example.deliciousfood.pages;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.deliciousfood.R;
import com.example.deliciousfood.api.DeliciousAPI;
import com.example.deliciousfood.utils.SharedPreferenceHelper;

public class MainActivity extends AppCompatActivity {
    private DeliciousAPI deliciousAPI;
    String TAG = "main";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(getApplicationContext(), SharedPreferenceHelper.INSTANCE.getLoginID(getApplicationContext()), Toast.LENGTH_SHORT).show();
    }
}