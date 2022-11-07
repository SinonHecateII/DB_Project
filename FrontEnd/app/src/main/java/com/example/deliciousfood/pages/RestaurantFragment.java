package com.example.deliciousfood.pages;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.deliciousfood.R;
import com.example.deliciousfood.databinding.FragmentRestaurantBinding;


public class RestaurantFragment extends Fragment {
    FragmentRestaurantBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentRestaurantBinding.inflate(getLayoutInflater());



        return binding.getRoot();
    }
}