package com.asiantech.summer.api

import com.asiantech.summer.Food
import retrofit2.Call
import retrofit2.http.*

interface DataService {
    @GET("/foods")
    fun getAllFood(): Call<List<Food>>

    @GET("/foods/{id}")
    fun getIdFood(@Path("id") id: Int, @Body food: Food): Call<Food>

    @POST("/foods")
    fun addFood(@Body food: Food): Call<Food>

    @PUT("/foods/{id}")
    fun getUpdateFood(@Path("id") id: Int, @Body food: Food): Call<Food>

    @DELETE("/foods/{id}")
    fun deleteFood(@Path("id") id: Int): Call<Food>
}