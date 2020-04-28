package com.asiantech.summer.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.asiantech.summer.data.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): List<User>

    @Query("SELECT * FROM user WHERE userId IN(:mUserId)")
    fun loadAllById(mUserId:IntArray): List<User>

    @Query("SELECT *FROM user WHERE user_name = :mUserName AND pass = :mPass" )
    fun findByNameAndPass(mUserName:String, mPass:String): User

    @Query("SELECT * FROM user WHERE image = :mImage")
    fun findByImage(mImage:String): User

    @Insert
    fun insertAll(vararg users:User)

    @Delete
    fun deleteAll(user: User)

}