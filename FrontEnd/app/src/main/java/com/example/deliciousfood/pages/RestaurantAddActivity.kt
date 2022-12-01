package com.example.deliciousfood.pages

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.deliciousfood.databinding.ActivityRestaurantAddBinding
import com.example.deliciousfood.utils.Constants
import com.example.deliciousfood.utils.Utils

class RestaurantAddActivity : AppCompatActivity() {
    private val binding by lazy { ActivityRestaurantAddBinding.inflate(layoutInflater) }
    // 이미지 절대경로
    private var imageRealPath: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarRestaurantAdd)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        binding.run {
            spLocation.adapter = ArrayAdapter(this@RestaurantAddActivity,android.R.layout.simple_spinner_dropdown_item, Constants.locations)

            tvPhotoAdd.setOnClickListener {
                val intent = Intent(Intent.ACTION_PICK).apply {
                    type = "image/*"
                }
                startActivityForResult(intent, Constants.REQUEST_CODE_GALLERY)
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