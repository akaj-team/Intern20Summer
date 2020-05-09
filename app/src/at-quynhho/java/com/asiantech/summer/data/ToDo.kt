package com.asiantech.summer.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(
    tableName = "todo",
    foreignKeys = arrayOf(
        ForeignKey(
            entity = User::class,
        parentColumns = arrayOf("userId"),
        childColumns = arrayOf("user_id"),
        onDelete = ForeignKey.NO_ACTION
    )
    )
)
@Parcelize
data class ToDo(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "todoTitle") var todoTitle: String?,
    @ColumnInfo(name = "isDone") var isDone: Boolean,
    @ColumnInfo(name = "user_id") val uid: Int
) : Parcelable