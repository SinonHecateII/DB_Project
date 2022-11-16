package com.example.deliciousfood.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.deliciousfood.R
import com.example.deliciousfood.databinding.ItemRestaurantBinding
import com.example.deliciousfood.models.RestaurantModel

class RestaurantAdapter(
    private val context: Context,
    private val models: ArrayList<RestaurantModel>,
) :
    RecyclerView.Adapter<RestaurantAdapter.Holder>() {

    interface OnItemClickListener {
        fun onItemClick(model: RestaurantModel)
    }

    var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = inflater.inflate(R.layout.item_restaurant, parent, false)

        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.binding.run {
            val model = models[position]
            tvItemRestaurantName.text = model.name
            tvItemMood.text = "# ${model.mood}"


            /*
            뷰를 재사용하는 리사이클러뷰의 특성 상 이미지가 없을 때 기본 이미지로 예외처리를 하지 않으면
            기존의 가져온 이미지가 남아있는 현상이 있음
            */

            if (model.photoCnt > 0) {
                Glide.with(context)
                    .load("http://210.125.212.207/shareimage/${model.restaurantId}_img0.jpg")
                    .into(ivItemRestaurant)
            } else {
                Glide.with(context)
                    .load(R.drawable.img_food_photo_not_exist)
                    .into(ivItemRestaurant)
            }
        }
    }

    override fun getItemCount(): Int {
        return models.size
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemRestaurantBinding.bind(itemView)

        init {
            itemView.setOnClickListener {
                onItemClickListener?.onItemClick(models[bindingAdapterPosition])
            }
        }
    }
}