package com.asiantech.summer.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.asiantech.summer.MainFood
import com.asiantech.summer.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.`at-quynhho`.recyclerview_cart.view.*

class CartAdapter(private var listMainFood: List<MainFood>) :
    RecyclerView.Adapter<CartAdapter.CartRecyclerViewHolder?>() {
    var onDeleteClick: (position: Int) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartRecyclerViewHolder {
        return CartRecyclerViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.recyclerview_cart,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = listMainFood.size

    override fun onBindViewHolder(holder: CartRecyclerViewHolder, position: Int) {
        (holder as? CartRecyclerViewHolder)?.bind()
    }

    inner class CartRecyclerViewHolder constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        var nameFoodCart = itemView.tvNameProduct
        var titleFoodCart = itemView.tvIntroductionProduct
        var price = itemView.tvPriceProduct
        var quantum = itemView.tvQuantum
        var imageFood = itemView.imgProduct
        var imageDelete = itemView.imgDeleteProduct

        init {
            imageDelete.setOnClickListener {
                onDeleteClick.invoke(adapterPosition)
            }
        }

        fun bind() {
            listMainFood[adapterPosition].let {
                nameFoodCart.text = it.name
                titleFoodCart.text = it.title
                price.text = it.price.toString()
                quantum.text = it.quantum.toString()

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