package com.asiantech.summer.adapter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.text.InputType
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
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
    private var cartAdapter: CartAdapter? = null
    private var service: DataService? =
        RetrofitClient.getRetrofitInstance()?.create(DataService::class.java)

    companion object {
        private const val LIST_FOOD = "listfood"
        private const val USER = "user"

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
            listMainFood = it.getParcelableArrayList<MainFood>(LIST_FOOD) as ArrayList<MainFood>
            user = it.getParcelable(USER) as User
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
            cartAdapter = CartAdapter(listMainFood)
            adapter = cartAdapter
            cartAdapter?.onUpdateClick = {
                dialogGetNumber(it)
                cartAdapter?.notifyDataSetChanged()
                totalProduct()
            }
            cartAdapter?.onDeleteClick = {
                deleteFoodOnCart(it)
                totalProduct()
            }
            totalProduct()
        }
    }

    private fun deleteFoodOnCart(position: Int) {
        val call: Call<ResponseBody>? = service?.deleteFood(1, listMainFood[position].id)
        call?.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d("BBB", t.message)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    listMainFood.removeAt(position)
                    cartAdapter?.notifyItemRemoved(position)
                    Log.d("BBB", "aaa")
                } else {
                    Log.d("BBB", "dddd")
                }
            }
        })
    }

    private fun updateQuantumFood(position: Int, mainFood: MainFood) {
        val call: Call<MainFood>? = service?.getUpdateFood(1, position, mainFood)
        call?.enqueue(object : Callback<MainFood> {
            override fun onFailure(call: Call<MainFood>, t: Throwable) {

            }

            override fun onResponse(call: Call<MainFood>, response: Response<MainFood>) {
                if (response.isSuccessful) {
                    listMainFood.add(mainFood)
                }
            }
        })
    }

    private fun dialogGetNumber(position: Int) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setTitle("Update quantum product")
        val editNum = EditText(context)
        editNum.inputType = InputType.TYPE_CLASS_NUMBER
        builder.setView(editNum)
        builder.setPositiveButton(
            "OK"
        ) { dialog, which ->
            val mainFood = listMainFood[position]
            mainFood.quantum = editNum.text.toString().toInt()
            updateQuantumFood(position, mainFood)
        }
        builder.setNegativeButton(
            "Cancel"
        ) { dialog, which -> dialog?.cancel() }
        builder.show()
    }

    @SuppressLint("SetTextI18n")
    private fun totalProduct() {
        var total = 0.0
        listMainFood.forEach {
            total += it.price * (it.quantum?.toDouble() ?: 0.0)
            Log.d("TAG11", "$total")
        }
        tvTotalPrice.text = "Total: $total$"
    }
}

