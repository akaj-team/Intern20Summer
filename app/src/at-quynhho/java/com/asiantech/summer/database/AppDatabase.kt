package com.asiantech.summer.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.asiantech.summer.dao.UserDao
import com.asiantech.summer.data.User

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract class userDao() : UserDao
}