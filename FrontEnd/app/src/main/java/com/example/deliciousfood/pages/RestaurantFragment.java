package com.example.deliciousfood.pages;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.deliciousfood.R;
import com.example.deliciousfood.adapter.RestaurantAdapter;
import com.example.deliciousfood.api.DeliciousAPI;
import com.example.deliciousfood.api.dto.requestDTO.ResSearchDTO;
import com.example.deliciousfood.api.dto.responseDTO.RestaurantResponseDTO;
import com.example.deliciousfood.databinding.FragmentRestaurantBinding;
import com.example.deliciousfood.models.RestaurantModel;
import com.example.deliciousfood.utils.Constants;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RestaurantFragment extends Fragment {
    private FragmentRestaurantBinding binding;
    private String location = null;
    private ArrayList<RestaurantModel> restaurantList = new ArrayList<>();
    private RestaurantAdapter restaurantAdapter;
    private DeliciousAPI deliciousAPI = DeliciousAPI.create();
    private String TAG = "RestaurantFragment";

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
        restaurantList.clear();

        restaurantAdapter = new RestaurantAdapter(requireActivity(), restaurantList);
        binding.rvRestaurant.setAdapter(restaurantAdapter);

        ArrayList<String> tempMenu = new ArrayList<String>();

        restaurantList.add(new RestaurantModel("한솥", null, "평범", tempMenu));
        restaurantList.add(new RestaurantModel("강나루", null, "평범", tempMenu));

        Call<RestaurantResponseDTO> restaurantSearchCall = deliciousAPI.restaurantSearchCall(location);
        restaurantSearchCall.enqueue(new Callback<RestaurantResponseDTO>() {
            @Override
            public void onResponse(Call<RestaurantResponseDTO> call, Response<RestaurantResponseDTO> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse: Point1");
                    Log.d(TAG, "onResponse: " + response.toString());
                    Log.d(TAG, "onResponse: " + response.body().toString());
                    Log.d(TAG, "onResponse: " + response.body().getResult().size());
                    RestaurantResponseDTO restaurantResponseDTO = response.body();
                    Log.d(TAG, "onResponse: ${" + restaurantResponseDTO + "}");
                    if (restaurantResponseDTO != null) {
                        for (int i = 0; i < restaurantResponseDTO.getResult().size(); i++) {
                            restaurantList.add(
                                    new RestaurantModel(
                                            restaurantResponseDTO.getResult().get(i).getName(),
                                            null,
                                            restaurantResponseDTO.getResult().get(i).getMood(),
                                            tempMenu));
                        }
                        restaurantAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<RestaurantResponseDTO> call, Throwable t) {

            }
        });

        restaurantAdapter.notifyDataSetChanged();
    }
}