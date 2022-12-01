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
import com.example.deliciousfood.databinding.ItemMenuRemovableBinding
import com.example.deliciousfood.databinding.ItemRestaurantBinding
import com.example.deliciousfood.models.RestaurantModel

class RemovableMenuAdapter(
    private val context: Context,
    private val models: ArrayList<String>,
) :
    RecyclerView.Adapter<RemovableMenuAdapter.Holder>() {


    interface OnRemoveClickListener {
        fun onRemoveClick(menu: String, position: Int)
    }

    var onRemoveClickListener: OnRemoveClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = inflater.inflate(R.layout.item_menu_removable, parent, false)

        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.binding.run {
            tvItemMenuName.text = models[position]
        }
    }

    override fun getItemCount(): Int {
        return models.size
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemMenuRemovableBinding.bind(itemView)

        init {
            binding.btnMenuRemove.setOnClickListener {
                onRemoveClickListener?.onRemoveClick(models[bindingAdapterPosition], bindingAdapterPosition)
            }
        }
    }
}