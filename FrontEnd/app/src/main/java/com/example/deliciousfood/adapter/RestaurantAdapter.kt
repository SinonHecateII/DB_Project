package com.example.deliciousfood.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.deliciousfood.R
import com.example.deliciousfood.databinding.ItemRestaurantBinding
import com.example.deliciousfood.models.RestaurantModel

class RestaurantAdapter(
    private val context: Context,
    val models: ArrayList<RestaurantModel>,
) :
    RecyclerView.Adapter<RestaurantAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = inflater.inflate(R.layout.item_restaurant, parent, false)

        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.binding.run {
            val model = models[position]
            tvItemRestaurantName.text = model.name
            tvItemMood.text = model.mood
        }
    }

    override fun getItemCount(): Int {
        return models.size
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemRestaurantBinding.bind(itemView)
    }
}