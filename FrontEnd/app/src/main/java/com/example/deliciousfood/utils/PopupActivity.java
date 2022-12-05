package com.example.deliciousfood.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deliciousfood.R;
import com.example.deliciousfood.api.DeliciousAPI;
import com.example.deliciousfood.api.dto.requestDTO.DeleteAccountDTO;
import com.example.deliciousfood.api.dto.responseDTO.OnlyResultDTO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PopupActivity extends Activity {
    private DeliciousAPI deliciousAPI;
    Button OkayBtn;
    Button CancelBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        deliciousAPI = DeliciousAPI.create();

        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.popup_activity_delete_account);

        //계정 삭제 확인 버튼
        OkayBtn = (Button) findViewById(R.id.Delete_btn);
        OkayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteAccount();
            }
        });

        CancelBtn = (Button) findViewById(R.id.Cancel_btn);
        CancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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
                            finish();
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<OnlyResultDTO> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "계정 삭제에 알 수 없는 오류가 발생하였습니다.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }
}
