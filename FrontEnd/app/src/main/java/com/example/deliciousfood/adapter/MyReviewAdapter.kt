package com.example.deliciousfood.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.deliciousfood.R
import com.example.deliciousfood.api.dto.responseDTO.ReviewResponseModel
import com.example.deliciousfood.api.dto.responseDTO.ReviewSearchUserIdResult
import com.example.deliciousfood.databinding.ItemMyReviewBinding
import com.example.deliciousfood.databinding.ItemReviewBinding
import com.example.deliciousfood.models.ReviewModel

class MyReviewAdapter(
    private val context: Context,
    private val models: ArrayList<ReviewSearchUserIdResult>,
) :
    RecyclerView.Adapter<MyReviewAdapter.Holder>() {

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    interface OnMoreClickListener {
        fun onMoreClick(position: Int, imageView: ImageView)
    }

    var onItemClickListener: OnItemClickListener? = null
    var onMoreClickListener: OnMoreClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = inflater.inflate(R.layout.item_my_review, parent, false)

        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.binding.run {
            val model = models[position]

            tvItemMyReviewTitle.text = model.restaurantName
            tvItemMyReviewContent.text = model.content
            rbItemMyReviewRating.rating = model.score.toFloat()
            tvItemMyReviewRating.text = model.score
        }
    }

    override fun getItemCount(): Int {
        return models.size
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemMyReviewBinding.bind(itemView)

        init {
            itemView.setOnClickListener {
                onItemClickListener?.onItemClick(bindingAdapterPosition)
            }
            binding.ivItemMyReviewMore.setOnClickListener {
                onMoreClickListener?.onMoreClick(bindingAdapterPosition, binding.ivItemMyReviewMore)
            }
        }
    }
}