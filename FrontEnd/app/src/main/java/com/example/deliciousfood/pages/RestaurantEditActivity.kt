package com.example.deliciousfood.pages

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.example.deliciousfood.api.PhotoAPI
import com.example.deliciousfood.api.dto.requestDTO.ResEditDTO
import com.example.deliciousfood.api.dto.requestDTO.ResIdSearchDTO
import com.example.deliciousfood.api.dto.requestDTO.RestaurantDTO
import com.example.deliciousfood.api.dto.responseDTO.OnlyResultDTO
import com.example.deliciousfood.api.dto.responseDTO.RestaurantResponseDTO
import com.example.deliciousfood.api.dto.responseDTO.RestaurantResponseModel
import com.example.deliciousfood.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.await
import java.io.File

class RestaurantEditActivity : RestaurantAddActivity() {

    private var restaurantID: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnRegisterFinish.setOnClickListener {
            onEditRestaurant()
        }

        restaurantID = intent.getIntExtra(Constants.INTENT_EXTRA_RESTAURANT_ID, -1)

        commonSetting() // 추가, 삭제 공통 설정

        onEditSetting()
    }

    private fun onEditRestaurant() {
        //    private int restaurantID;
        //    private String location;
        //    private String name;
        //    private String mood;
        val restaurantDTO = ResEditDTO(
            restaurantID!!,
            binding.spLocation.selectedItem.toString(),
            binding.etRestaurantName.text.toString(),
            binding.etRestaurantMood.text.toString()
        )

        // 사진 업로드 call
        if(imageRealPath != null) {
            val file = File(imageRealPath!!)

            // 사진 업로드
            val multipartImage = MultipartBody.Part.createFormData(
                "img",
                "${restaurantID}_img0.jpg",
                RequestBody.create("image/*".toMediaTypeOrNull(), file)
            )

            photoAPI.uploadImage(multipartImage).enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
                        hideProgress()
                        finish()
                    } else {
                        Log.d(TAG, "onResponse: ${response.errorBody()}")
                        showShortToast("사진 업로드 실패 2")
                        hideProgress()
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    showShortToast("사진 업로드 실패")
                    hideProgress()
                }
            })
        }


        deliciousAPI.restaurantEditCall(restaurantDTO).enqueue(object : Callback<OnlyResultDTO> {
            override fun onResponse(
                call: Call<OnlyResultDTO>,
                response: Response<OnlyResultDTO>
            ) {
                if (response.isSuccessful) {
                    showShortToast("수정이 완료되었습니다.")
                    startActivity(Intent(
                        this@RestaurantEditActivity,
                        RestaurantActivity::class.java
                    )
                        .apply {
                            putExtra(Constants.INTENT_EXTRA_RESTAURANT_ID, restaurantID)
                        })
                    finish()


                } else {
                    showShortToast("수정에 실패하였습니다.")
                }
            }

            override fun onFailure(call: Call<OnlyResultDTO>, t: Throwable) {
                showShortToast("수정에 실패하였습니다.")
            }
        })

    }


    private fun onEditSetting() {

        try {
            Glide.with(applicationContext)
                .load("http://210.125.212.207/shareimage/${restaurantID}_img0.jpg")
                .into(binding.ivRestaurantImage)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        deliciousAPI.restaurantSearchIdCall(ResIdSearchDTO(restaurantID!!))
            .enqueue(object : Callback<RestaurantResponseModel> {
                override fun onResponse(
                    call: Call<RestaurantResponseModel>,
                    response: Response<RestaurantResponseModel>
                ) {
                    if (response.isSuccessful) {
                        val restaurant = response.body()!!
                        binding.run {
                            spLocation.setSelection(Constants.locations.indexOf(restaurant.location))
                            etRestaurantName.setText(restaurant.name)
                            etRestaurantMood.setText(restaurant.mood)
                        }
                    } else {
                        showShortToast("에러가 발생했습니다 1")
                    }
                }

                override fun onFailure(call: Call<RestaurantResponseModel>, t: Throwable) {
                    showShortToast("에러가 발생했습니다 2")
                }

            })
    }
}