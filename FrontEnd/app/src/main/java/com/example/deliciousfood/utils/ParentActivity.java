package com.example.deliciousfood.utils;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.TextView;

import com.example.deliciousfood.R;

public class ParentActivity extends AppCompatActivity {
    Dialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent);
    }

    protected void showProgress(Activity activity, String text) {
        if(progressDialog != null) {
            progressDialog = null;
        }

        progressDialog = new Dialog(activity);
        progressDialog.setContentView(R.layout.dialog_progress);
        ((TextView)progressDialog.findViewById(R.id.tv_dialog_progress)).setText(text);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    protected void hideProgress() {
        if(progressDialog != null) {
            progressDialog.dismiss();
        }
        progressDialog = null;
    }
}