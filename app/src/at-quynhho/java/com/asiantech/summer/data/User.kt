package com.asiantech.summer.data

import android.text.Editable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true) val userId: Int,
    @ColumnInfo(name = "image") val avatar: String?,
    @ColumnInfo(name = "user_name") val userName: String?,
    @ColumnInfo(name = "nick_name") val nickName: String?,
    @ColumnInfo(name = "pass") val password: String?

)


