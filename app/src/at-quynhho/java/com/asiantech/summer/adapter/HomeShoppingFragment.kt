@file:Suppress("DEPRECATION")

package com.asiantech.summer.adapter

import android.app.AlertDialog
import android.app.ProgressDialog
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
import kotlinx.android.synthetic.`at-quynhho`.fragment_home_shopping.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Suppress("DEPRECATION")
class HomeShoppingFragment : Fragment() {

    private lateinit var homeAdapter: HomeAdapter
    private lateinit var listMainFood: List<MainFood>
    private var addList: ArrayList<MainFood> = arrayListOf()
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
            homeAdapter = HomeAdapter(listMainFood)
            adapter = homeAdapter
        }
        homeAdapter.onAddClick = {
            //todo
            dialogGetNumber(it)

        }
        homeAdapter.onLikeClick = {
            homeAdapter.notifyDataSetChanged()
        }
        imgAddtoCart.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(
                    R.id.flShopping,
                    ShoppingCartFragment.newInstance(addList, User(1, foodUser = addList))
                )
                ?.commit()
        }
    }

    private fun getDataServer() {
        dialog = ProgressDialog(context)
        dialog.setMessage("Loading..")
        dialog.show()
        val call: Call<List<MainFood>>? = service?.getAllFood()
        call?.enqueue(object : Callback<List<MainFood>> {
            override fun onFailure(call: Call<List<MainFood>>, t: Throwable) {
                dialog.dismiss()
            }

            override fun onResponse(
                call: Call<List<MainFood>>,
                response: Response<List<MainFood>>
            ) {
                dialog.dismiss()
                listMainFood = (response.body()) ?: listOf()
                listProduct()
            }

        })
    }

    private fun addFoodOnCart(mainFood: MainFood) {
        val call: Call<MainFood>? = service?.addFoodUser(1, mainFood)
        call?.enqueue(object : Callback<MainFood> {
            override fun onFailure(call: Call<MainFood>, t: Throwable) {

            }
            override fun onResponse(call: Call<MainFood>, response: Response<MainFood>) {
                if (response.isSuccessful) {
                    Log.d("AAA", "" + response.body())
                    response.body()?.also {
                        addList.add(it)
                    }

                }
            }
        })
    }

    private fun dialogGetNumber(position:Int) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setTitle("Import quantum product")
        val editNum = EditText(context)
        editNum.inputType = InputType.TYPE_CLASS_NUMBER
        builder.setView(editNum)
        builder.setPositiveButton(
            "OK"
        ) { dialog, which ->
            val mainFood = listMainFood[position]
            mainFood.quantum = editNum.text.toString().toInt()
            addFoodOnCart(mainFood)
        }
        builder.setNegativeButton(
            "Cancel"
        ) { dialog, which -> dialog?.cancel() }
        builder.show()
    }
}


