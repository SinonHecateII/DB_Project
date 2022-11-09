package com.example.deliciousfood.pages;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.deliciousfood.R;
import com.example.deliciousfood.databinding.FragmentRestaurantBinding;
import com.example.deliciousfood.utils.Constants;


public class RestaurantFragment extends Fragment {
    FragmentRestaurantBinding binding;
    String location = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentRestaurantBinding.inflate(getLayoutInflater());

        location = getArguments().getString(Constants.ARGUMENT_LOCATION, null);
        if(location == null) {
            Toast.makeText(requireContext(), "Fragment Arguments 전달 에러", Toast.LENGTH_SHORT).show();
        }

        binding.tvTemp.setText(location);

        return binding.getRoot();
    }
}