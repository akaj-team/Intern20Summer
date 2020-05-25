package com.asiantech.summer.adapter

import android.app.ProgressDialog
import android.os.Bundle
import android.os.Message
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.asiantech.summer.Food
import com.asiantech.summer.R
import com.asiantech.summer.api.DataService
import com.asiantech.summer.api.RetrofitClient
import kotlinx.android.synthetic.`at-quynhho`.fragment_home_shopping.*
import kotlinx.android.synthetic.`at-quynhho`.recyclerview_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Suppress("DEPRECATION")
class HomeShoppingFragment : Fragment() {

    private lateinit var homeAdapter: HomeAdapter
    private lateinit var listFood: List<Food>
    private var position = 0
    private lateinit var dialog: ProgressDialog
    private val service: DataService? =
        RetrofitClient.getRetrofitInstance()?.create(DataService::class.java)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home_shopping, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDataServer()

    }

    private fun listProduct() {
        rvMyCart.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            homeAdapter = HomeAdapter(listFood)
            adapter = homeAdapter
        }
        homeAdapter.onAddClick = {
            addFoodOnCart(listFood[position])
        }
        homeAdapter.onEditClick = {
            updateFood()
        }
        imgAddtoCart.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.flShopping, ShoppingCartFragment.newInstance(listFood[position]))
                ?.commit()
        }
    }

    private fun getDataServer() {
        dialog = ProgressDialog(context)
        dialog.setMessage("Loading..")
        dialog.show()
        val call: Call<List<Food>>? = service?.getAllFood()
        call?.enqueue(object : Callback<List<Food>> {
            override fun onFailure(call: Call<List<Food>>, t: Throwable) {
                dialog.dismiss()
            }

            override fun onResponse(call: Call<List<Food>>, response: Response<List<Food>>) {
                dialog.dismiss()
                listFood = (response.body()) ?: listOf()
                listProduct()
            }

        })
    }

    private fun addFoodOnCart(food: Food) {
        val call: Call<Food>? = service?.addFood(food)
        call?.enqueue(object : Callback<Food> {
            override fun onFailure(call: Call<Food>, t: Throwable) {

            }

            override fun onResponse(call: Call<Food>, response: Response<Food>) {
                if (response.isSuccessful) {
                    Log.d("AAA", "" + response.body())
                    food.copy(
                        id = food.id, avatar = food.avatar, name = food.name,
                        title = food.title, price = food.price, quantum = food.quantum
                    )
                }
            }
        })
    }

    private fun updateFood() {
        val call: Call<Food>? = service?.getUpdateFood(0, listFood[position])
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
