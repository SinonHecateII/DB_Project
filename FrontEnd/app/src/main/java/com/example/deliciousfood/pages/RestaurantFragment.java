package com.example.deliciousfood.pages;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.deliciousfood.R;
import com.example.deliciousfood.adapter.RestaurantAdapter;
import com.example.deliciousfood.databinding.FragmentRestaurantBinding;
import com.example.deliciousfood.models.RestaurantModel;
import com.example.deliciousfood.utils.Constants;

import java.util.ArrayList;


public class RestaurantFragment extends Fragment {
    private FragmentRestaurantBinding binding;
    private String location = null;
    private ArrayList<RestaurantModel> restaurantList = new ArrayList<>();
    private RestaurantAdapter restaurantAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentRestaurantBinding.inflate(getLayoutInflater());

        location = getArguments().getString(Constants.ARGUMENT_LOCATION, null);
        if (location == null) {
            Toast.makeText(requireContext(), "Fragment Arguments 전달 에러", Toast.LENGTH_SHORT).show();
        }


        getRestaurantList();

        return binding.getRoot();
    }

    public void getRestaurantList() {
        restaurantAdapter = new RestaurantAdapter(requireActivity(), restaurantList);
        binding.rvRestaurant.setAdapter(restaurantAdapter);

        ArrayList<String> tempMenu = new ArrayList<String>();

        restaurantList.add(new RestaurantModel("한솥", null, "평범", tempMenu));
        restaurantList.add(new RestaurantModel("강나루", null, "평범", tempMenu));
        restaurantList.add(new RestaurantModel("파스타랩", null, "평범", tempMenu));

        restaurantAdapter.notifyDataSetChanged();
    }
}