package com.example.deliciousfood.pages;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.deliciousfood.R;
import com.example.deliciousfood.api.DeliciousAPI;
import com.example.deliciousfood.databinding.ActivityMainBinding;
import com.example.deliciousfood.utils.SharedPreferenceHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

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
        Glide.with(this).load(imageStr).into(binding.ivImageTest);
    }
}

class ImageUrlDown extends AsyncTask<String, String, Bitmap> {
    // 다운로드 완료 후 호출 할 리스너
    public interface OnPostDownLoadListener {
        void onPost(Bitmap bitmap);
    }

    private Bitmap bitmap = null;
    private OnPostDownLoadListener onPostDownLoad;

    // 리스너 세팅
    public ImageUrlDown(OnPostDownLoadListener paramOnPostDownLad) {
        onPostDownLoad = paramOnPostDownLad;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        try {
            // 파라미터로 받은 url로 부터 이미지 다운로드
            bitmap = BitmapFactory.decodeStream((InputStream) new URL(strings[0]).getContent());
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        // 이미지 다운로드 완료 후 리스너 호출
        if (onPostDownLoad != null)
            onPostDownLoad.onPost(bitmap);
    }
}

