package com.asiantech.summer.api

import com.asiantech.summer.MainFood
import com.asiantech.summer.User
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface DataService {
    @GET("/mainFood")
    fun getAllFood(): Call<List<MainFood>>

    @GET("/foods/{id}")
    fun getIdFood(@Path("id") id: Int): Call<MainFood>

    @GET("/user/1")
    fun getUser(): Call<User>

    @GET("/user/{userId}/foods/{id}")
    fun getFoodOnUser(@Path("userId") userId: Int, @Path("id") id: Int): Call<MainFood>

    @POST("/user/{userId}/foods")
    fun addFoodUser(@Path("userId") userId: Int, @Body mainFood: MainFood): Call<MainFood>

    @PUT("/user/{userId}/foods/{id}")
    fun getUpdateFood(@Path("userId") userId: Int, @Path("id") id: Int, @Body mainFood: MainFood): Call<MainFood>

    @DELETE("/user/{userId}/foods/{id}")
    fun deleteFood(@Path("userId") userId: Int, @Path("id") id: Int): Call<ResponseBody>
}