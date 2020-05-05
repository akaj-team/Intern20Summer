package com.asiantech.summer.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo")
data class ToDo(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "todoTitle") var todoTitle: String?,
    @ColumnInfo(name = "isDone") var isDone: Boolean,
    @ColumnInfo(name = "user_id") val uid: Int
)