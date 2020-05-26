package com.asiantech.summer.adapter

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.asiantech.summer.MainFood
import com.asiantech.summer.R
import com.asiantech.summer.User
import com.asiantech.summer.api.DataService
import com.asiantech.summer.api.RetrofitClient
import kotlinx.android.synthetic.`at-quynhho`.fragment_shopping_cart.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShoppingCartFragment : Fragment() {
    private lateinit var user: User
    private var listMainFood: ArrayList<MainFood> = arrayListOf()
    private var total: Double = 0.0
    private var quantum: String = ""
    private var cartAdapter: CartAdapter? = null
    private var position = 0
    private var service: DataService? =
        RetrofitClient.getRetrofitInstance()?.create(DataService::class.java)

    companion object {
        private const val LIST_FOOD = "listfood"
        private const val USER = "user"
        private const val QUANTUM = "quantum"

        fun newInstance(
            mainFoods: ArrayList<MainFood>,
            user: User
        ): ShoppingCartFragment {
            return ShoppingCartFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(LIST_FOOD, mainFoods)
                    putParcelable(USER, user)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        arguments?.let {
            listMainFood = it.getParcelableArrayList<MainFood>(LIST_FOOD)
            user = it.getParcelable(USER)
        }
        return inflater.inflate(R.layout.fragment_shopping_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listFoodCart()
    }

    private fun listFoodCart() {
        rvMyCart.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = CartAdapter(listMainFood)
            cartAdapter?.onDeleteClick = {
                deleteFoodOnCart(it)
            }

        }
        cartAdapter?.notifyDataSetChanged()
        Log.d("aaa", "cart " + listMainFood.size)
        totalProduct()
    }

    private fun deleteFoodOnCart(id: Int) {
        val call: Call<ResponseBody>? = service?.deleteFood(id)
        call?.execute()
        call?.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
//                    listMainFood = (response.body())
//                    response.code()
//                    listMainFood.removeAt(mainFood.id)
                }
            }
        })
    }

    private fun totalProduct() {
        total += (listMainFood[position].price)
//        total += total
        tvTotalPrice.text = "Total: $total $"
    }
}
