package com.example.deliciousfood.pages;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.deliciousfood.R;
import com.example.deliciousfood.adapter.RestaurantViewPagerAdapter;
import com.example.deliciousfood.api.DeliciousAPI;
import com.example.deliciousfood.databinding.ActivityMainBinding;
import com.example.deliciousfood.pages.session.MyPageActivity;
import com.example.deliciousfood.utils.Constants;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private DeliciousAPI deliciousAPI;
    String TAG = "main";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // 뷰 바인딩
        // findViewById 대신 binding 으로 xml 객체와 java 코드 상 객체를 연결
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String imageStr = "http://210.125.212.207/shareimage/pasta.jpg";
        //Glide.with(this).load(imageStr).into(binding.ivImageTest);


        // 위치
        String[] locations = Constants.locations;


        // ViewPager 에 쓸 프래그먼트
        ArrayList<Fragment> fragments = new ArrayList<>();

        for (int i = 0; i < locations.length; i++) {
            fragments.add(new RestaurantFragment());

            // 액티비티 -> 프래그먼트 값 전달
            // 번들에 String 값을 입력하고 Argument 로 번들을 전달함
            Bundle bundle = new Bundle();
            bundle.putString(Constants.ARGUMENT_LOCATION, locations[i]);
            fragments.get(i).setArguments(bundle);
        }

        // 뷰페이저 어댑터
        RestaurantViewPagerAdapter viewPagerAdapter = new RestaurantViewPagerAdapter(fragments, locations, getSupportFragmentManager());

        binding.vpRestaurant.setAdapter(viewPagerAdapter); // Layout(xml) 의 ViewPager 객체에 어댑터를 set
        binding.tlRestaurant.setupWithViewPager(binding.vpRestaurant); // Layout(xml) 의 TabLayout 을 ViewPager 객체와 연동함


        binding.toolbarRegister.findViewById(R.id.menu_mypage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MyPageActivity.class));
            }
        });

        binding.btnRestaurantAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RestaurantAddActivity.class));
            }
        });
    }
}