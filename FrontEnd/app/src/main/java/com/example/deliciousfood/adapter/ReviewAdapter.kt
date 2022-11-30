package com.example.deliciousfood.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.deliciousfood.R
import com.example.deliciousfood.databinding.ItemReviewBinding
import com.example.deliciousfood.models.ReviewModel

class ReviewAdapter(
    private val context: Context,
    private val models: ArrayList<ReviewModel>,
) :
    RecyclerView.Adapter<ReviewAdapter.Holder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = inflater.inflate(R.layout.item_review, parent, false)

        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.binding.run {
            val model = models[position]

            tvItemReviewUser.text = model.name
            tvItemReviewContent.text = model.content
            rbItemReviewRating.rating = model.rating
        }
    }

    override fun getItemCount(): Int {
        return models.size
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemReviewBinding.bind(itemView)
    }
}