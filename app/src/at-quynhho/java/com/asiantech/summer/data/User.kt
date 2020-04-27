package com.asiantech.summer.data

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.ByteArrayOutputStream
import java.util.*

@Entity
data class User(
    @PrimaryKey
    val userId: Int,
 //   @ColumnInfo(name = "image") val avatar: Bitmap,
    @ColumnInfo(name = "user_name") val userName: String?,
    @ColumnInfo(name = "nick_name") val nickName: String?,
    @ColumnInfo(name = "pass") val password: String?


)
{
    private fun convertBitMap(bitmap: Bitmap) : String? {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }
    private fun convertString(base64String: String) : Bitmap{

    }
}



