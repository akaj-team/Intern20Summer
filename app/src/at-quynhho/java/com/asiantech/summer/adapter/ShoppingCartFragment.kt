package com.asiantech.summer.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.asiantech.summer.Food
import com.asiantech.summer.R
import kotlinx.android.synthetic.`at-quynhho`.fragment_shopping_cart.*

class ShoppingCartFragment : Fragment() {

    private var listFood: ArrayList<Food> = ArrayList()
    private var cartAdapter = CartAdapter(listFood)
    private var position = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shopping_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvMyCart.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = cartAdapter
        }
        onCLickButton()
    }

    private fun onCLickButton() {
        cartAdapter.onDeleteClick = {
            position = it
        }
//        cartAdapter.notifyDataSetChanged()
    }
}
