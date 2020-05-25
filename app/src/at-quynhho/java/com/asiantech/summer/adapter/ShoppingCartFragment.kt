package com.asiantech.summer.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.asiantech.summer.Food
import com.asiantech.summer.R
import com.asiantech.summer.api.DataService
import com.asiantech.summer.api.RetrofitClient
import kotlinx.android.synthetic.`at-quynhho`.fragment_shopping_cart.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShoppingCartFragment : Fragment() {

    private var listFood: List<Food>? = null
    private var cartAdapter = CartAdapter(listFood ?: listOf())
    private var position = 0
    private var service: DataService? =
        RetrofitClient.getRetrofitInstance()?.create(DataService::class.java)

    companion object {
        fun newInstance(food: Food): ShoppingCartFragment {
            return ShoppingCartFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("LIST_FOOD", food)

                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shopping_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getFoodOnCart()
    }

    private fun listFoodCart() {
        rvMyCart.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = cartAdapter
            cartAdapter.onDeleteClick = {
                deleteFoodOnCart()
            }
        }

    }

    private fun getFoodOnCart() {
        val id = listFood?.get(position)?.id
        val call: Call<Food>? =
            listFood?.get(position)?.let { service?.getIdFood(id ?: 0, it) } as Call<Food>
        call?.enqueue(object : Callback<Food> {
            override fun onFailure(call: Call<Food>, t: Throwable) {

            }

            override fun onResponse(call: Call<Food>, response: Response<Food>) {
                listFood = (response.body()) as? List<Food>
                listFoodCart()
            }
        })
    }

    private fun deleteFoodOnCart() {
        val id = listFood?.get(position)?.id
        val call: Call<Food>? =
            listFood?.get(position)?.let { service?.deleteFood(id ?: 0) } as Call<Food>
        call?.enqueue(object : Callback<Food> {
            override fun onFailure(call: Call<Food>, t: Throwable) {

            }

            override fun onResponse(call: Call<Food>, response: Response<Food>) {
                if (response.isSuccessful) {

                }
            }
        })
    }
}
