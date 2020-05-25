package com.asiantech.summer.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.asiantech.summer.Food
import com.asiantech.summer.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.`at-quynhho`.recyclerview_home.view.*

class HomeAdapter(private var listFood: List<Food>) :
    RecyclerView.Adapter<HomeAdapter.HomeRecyclerViewHolder?>() {

    var onAddClick: (position: Int) -> Unit = {}
    var onEditClick: (position: Int) -> Unit = {}

    override fun onBindViewHolder(holder: HomeRecyclerViewHolder, position: Int) {
        (holder as? HomeRecyclerViewHolder)?.bind()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeRecyclerViewHolder {
        return HomeRecyclerViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.recyclerview_home,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = listFood.size

    inner class HomeRecyclerViewHolder constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        var nameFood = itemView.tvNameProduct
        var titleFood = itemView.tvIntroductionProduct
        var priceFood = itemView.tvPriceProduct
        var imageFood = itemView.imgProduct
        var imageAdd = itemView.imgAddToBuy
        var imageLike = itemView.imgLikeProduct

        init {
            imageAdd.setOnClickListener {
                onAddClick.invoke(adapterPosition)
            }
            imageLike.setOnClickListener {
                onEditClick.invoke(adapterPosition)
            }
        }

        fun bind() {
            listFood[adapterPosition].let {
                nameFood.text = it.name
                titleFood.text = it.title
                priceFood.text = it.price.toString()

                it.avatar.let {
                    Glide.with(itemView)
                        .load(it)
                        .placeholder(R.drawable.ic_food_100)
                        .into(imageFood)
                }
            }
        }

    }

}