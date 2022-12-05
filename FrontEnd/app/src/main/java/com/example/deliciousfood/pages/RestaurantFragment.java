package com.example.deliciousfood.pages;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
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

        restaurantAdapter = new RestaurantAdapter(requireActivity(), restaurantList);
        binding.rvRestaurant.setAdapter(restaurantAdapter);

        setUpRecyclerClickEvent();

        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();

        getRestaurantList();
    }

    public void setUpRecyclerClickEvent() {
        restaurantAdapter.setOnItemClickListener(new RestaurantAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull RestaurantModel model) {
                Intent intent = new Intent(requireContext(), RestaurantActivity.class);
                Log.d(TAG, "onItemClick point1: " + model.getRestaurantId());
                intent.putExtra(Constants.INTENT_EXTRA_RESTAURANT_ID, model.getRestaurantId());
                startActivity(intent);
            }
        });
    }

    public void getRestaurantList() {
        restaurantList.clear();


        ArrayList<String> tempMenu = new ArrayList<String>();

        ResSearchDTO resSearchDTO = new ResSearchDTO(location);

        Call<RestaurantResponseDTO> restaurantSearchCall = deliciousAPI.restaurantSearchCall(resSearchDTO);
        restaurantSearchCall.enqueue(new Callback<RestaurantResponseDTO>() {
            @Override
            public void onResponse(Call<RestaurantResponseDTO> call, Response<RestaurantResponseDTO> response) {
                if (response.isSuccessful()) {
                    RestaurantResponseDTO restaurantResponseDTO = response.body();
                    if (restaurantResponseDTO != null) {
                        for (int i = 0; i < restaurantResponseDTO.getResult().size(); i++) {
                            restaurantList.add(
                                    new RestaurantModel(
                                            Integer.parseInt(restaurantResponseDTO.getResult().get(i).getRestaurantID()),
                                            restaurantResponseDTO.getResult().get(i).getName(),
                                            Integer.parseInt(restaurantResponseDTO.getResult().get(i).getPhotoCnt()),
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