package com.asiantech.summer.api

import com.asiantech.summer.MainFood
import com.asiantech.summer.User
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.*

interface DataService {
    @GET("/mainFood")
    fun getAllFood(): Call<List<MainFood>>

    @GET("/foods/{id}")
    fun getIdFood(@Path("id") id: Int): Call<MainFood>

    @GET("/user/2")
    fun getUser(): Call<User>

    @POST("/user/{id}/foods")
    fun addFoodUser(@Path("id") id: Int, @Body mainFood: MainFood): Call<MainFood>

    @PUT("/mainFood/{id}")
    fun getUpdateFood(@Path("id") id: Int, @Body mainFood: MainFood): Call<MainFood>

    @DELETE("/user/{id}/foods/{id}")
    fun deleteFood(@Path("id") id: Int): Call<ResponseBody>
}