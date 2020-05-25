package com.asiantech.summer.api

import com.asiantech.summer.Food
import retrofit2.Call
import retrofit2.http.*

interface DataService {
    @GET("/foods")
    fun getAllFood(): Call<List<Food>>

    @POST("/foods")
    fun addFood(@Body food: Food): Call<Food>

    @DELETE("delete/{id}")
    fun deleteFood(@Path("id") id: Int): Call<Food>
}