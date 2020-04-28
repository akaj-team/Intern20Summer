package com.asiantech.summer.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.asiantech.summer.dao.UserDao
import com.asiantech.summer.data.User


@Database(entities = arrayOf(User::class), version = 1)
abstract class NoteDatabase : RoomDatabase() {
    companion object {
        private var INSTANCE: NoteDatabase? = null
        fun newInstance(context: Context): NoteDatabase? {
            return if (INSTANCE == null) {
                Room.databaseBuilder(context, NoteDatabase::class.java, "note_new")
                    .allowMainThreadQueries()
                    .build()
            } else INSTANCE
        }
    }

    abstract fun userDao(): UserDao
}