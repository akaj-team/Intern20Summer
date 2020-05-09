package com.asiantech.summer.dao

import androidx.room.*
import com.asiantech.summer.data.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): List<User>

    @Query("SELECT * FROM user WHERE userId = :userId")
    fun findUserId(userId: Int): User

    @Query("SELECT * FROM user WHERE userId IN(:mUserId)")
    fun loadAllById(mUserId: IntArray): List<User>

    @Query("SELECT *FROM user WHERE user_name = :mUserName AND pass = :mPass")
    fun findByNameAndPass(mUserName: String, mPass: String): User

    @Query("SELECT * FROM user WHERE image = :mImage")
    fun findByImage(mImage: String): User

    @Query("SELECT * FROM user WHERE nick_name = :mNick")
    fun findByNick(mNick: String): User

    @Insert
    fun insertAll(vararg users: User)

    @Update
    fun updateAll(user: User)

    @Delete
    fun deleteAll(user: User)

}
