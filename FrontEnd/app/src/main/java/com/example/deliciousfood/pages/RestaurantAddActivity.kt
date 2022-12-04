package com.example.deliciousfood.pages

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ScrollView
import android.widget.SpinnerAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.deliciousfood.adapter.RemovableMenuAdapter
import com.example.deliciousfood.databinding.ActivityRestaurantAddBinding
import com.example.deliciousfood.utils.Constants
import com.example.deliciousfood.utils.Utils
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent

class RestaurantAddActivity : AppCompatActivity() {
    private val binding by lazy { ActivityRestaurantAddBinding.inflate(layoutInflater) }
    private val menuItems = ArrayList<String>()
    private val menuAdapter by lazy { RemovableMenuAdapter(applicationContext, menuItems) }


    // 이미지 절대경로
    private var imageRealPath: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

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
                    if(it.isEmpty()) {
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
            menuAdapter.onRemoveClickListener = object : RemovableMenuAdapter.OnRemoveClickListener {
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