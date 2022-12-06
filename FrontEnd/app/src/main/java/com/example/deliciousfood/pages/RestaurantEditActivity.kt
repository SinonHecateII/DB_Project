package com.example.deliciousfood.pages

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class RestaurantEditActivity : RestaurantAddActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnRegisterFinish.setOnClickListener {
            onEditRestaurant()
        }

        commonSetting()
    }

    private fun onEditRestaurant() {

    }
}