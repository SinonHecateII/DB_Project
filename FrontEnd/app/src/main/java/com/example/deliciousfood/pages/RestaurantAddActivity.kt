package com.example.deliciousfood.pages

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ScrollView
import android.widget.Toast
import com.example.deliciousfood.adapter.RemovableMenuAdapter
import com.example.deliciousfood.api.DeliciousAPI
import com.example.deliciousfood.api.PhotoAPI
import com.example.deliciousfood.api.dto.requestDTO.RestaurantDTO
import com.example.deliciousfood.api.dto.responseDTO.RestaurantAddResponseDTO
import com.example.deliciousfood.databinding.ActivityRestaurantAddBinding
import com.example.deliciousfood.utils.Constants
import com.example.deliciousfood.utils.ParentActivity
import com.example.deliciousfood.utils.Utils
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.*
import java.io.File

open class RestaurantAddActivity : ParentActivity() {
    protected val TAG = "RestaurantAddActivity"
    protected val binding by lazy { ActivityRestaurantAddBinding.inflate(layoutInflater) }
    protected val menuItems = ArrayList<String>()
    protected val menuAdapter by lazy { RemovableMenuAdapter(applicationContext, menuItems) }
    protected val deliciousAPI by lazy { DeliciousAPI.create() }
    protected val photoAPI by lazy { PhotoAPI.create() }


    // 이미지 절대경로
    protected var imageRealPath: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnRegisterFinish.setOnClickListener {
            onRestaurantAdd()
        }

        commonSetting()
    }

    // 수정, 삭제 공통세팅
    protected fun commonSetting() {
        setSupportActionBar(binding.toolbarRestaurantAdd)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        binding.run {
            spLocation.adapter = ArrayAdapter(
                this@RestaurantAddActivity,
                android.R.layout.simple_spinner_dropdown_item,
                Constants.locations
            )

            tvPhotoAdd.setOnClickListener {
                val intent = Intent(Intent.ACTION_PICK).apply {
                    type = "image/*"
                }
                startActivityForResult(intent, Constants.REQUEST_CODE_GALLERY)
            }


        }

        setUpMenuRecyclerView()
    }


    private fun onRestaurantAdd(): Unit = with(binding) {
        val location = spLocation.selectedItem.toString()
        val name = etRestaurantName.text.toString()
        val mood = etRestaurantMood.text.toString()

        if (location.isEmpty() || name.isEmpty() || mood.isEmpty()) {
            showShortToast("모든 항목을 입력해주세요")
            return
        }

        showProgress(this@RestaurantAddActivity, "잠시만 기다려 주세요")

        // location, name, mood, photoCnt
        val restaurant = RestaurantDTO(location, name, mood, if(imageRealPath == null) 0 else 1)

        deliciousAPI.restaurantAddCall(restaurant).enqueue(object : Callback<RestaurantAddResponseDTO> {
            override fun onResponse(
                call: Call<RestaurantAddResponseDTO>,
                response: Response<RestaurantAddResponseDTO>
            ) {
                if(response.isSuccessful) {
                    val restaurantId = response.body()!!.restaurantID
                    val file = File(imageRealPath!!)

                    // 사진 업로드
                    val multipartImage = MultipartBody.Part.createFormData(
                        "img",
                        "${restaurantId}_img0.jpg",
                        RequestBody.create("image/*".toMediaTypeOrNull(), file)
                    )

                    if(imageRealPath != null) {
                        photoAPI.uploadImage(multipartImage)
                            .enqueue(object : Callback<ResponseBody> {
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
                } else {
                    showShortToast("식당 등록에 실패했습니다")
                    hideProgress()
                }
            }

            override fun onFailure(call: Call<RestaurantAddResponseDTO>, t: Throwable) {
                showShortToast("서버와의 통신에 실패했습니다 1")
                hideProgress()
            }
        })

    }


    private fun setUpMenuRecyclerView() {
        binding.run {
            // Set FlexboxLayoutManager
            FlexboxLayoutManager(this@RestaurantAddActivity).apply {
                flexWrap = FlexWrap.WRAP
                flexDirection = FlexDirection.ROW
                justifyContent = JustifyContent.CENTER
            }.let {
                rvMenuList.layoutManager = it
                rvMenuList.adapter = menuAdapter
            }


            // Set Menu Add Button Click Event
            btnMenuAdd.setOnClickListener {
                etMenuAdd.text.toString().let {
                    if (it.isEmpty()) {
                        Toast.makeText(applicationContext, "메뉴를 입력해주세요.", Toast.LENGTH_SHORT).show()
                    } else {
                        menuItems.add(it)
                        menuAdapter.notifyDataSetChanged()
                        etMenuAdd.setText("")

                        svMainScroll.post {
                            svMainScroll.fullScroll(ScrollView.FOCUS_DOWN)
                        }
                    }
                }
            }

            // Set RecyclerView Item Remove Click Event
            menuAdapter.onRemoveClickListener =
                object : RemovableMenuAdapter.OnRemoveClickListener {
                    override fun onRemoveClick(menu: String, position: Int) {
                        menuItems.removeAt(position)
                        menuAdapter.notifyDataSetChanged()
                    }
                }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constants.REQUEST_CODE_GALLERY && resultCode == RESULT_OK) {
            data?.data?.let { uri ->
                binding.ivRestaurantImage.setImageURI(uri)
                imageRealPath = Utils.getPath(this, uri)
            }
        }
    }
}