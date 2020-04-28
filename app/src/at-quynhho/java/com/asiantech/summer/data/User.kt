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
//{
//    private fun convertBitMapToBase64(bitmap: Bitmap): String? {
//        val byteArrayOutputStream = ByteArrayOutputStream()
//        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
//        val byteArray = byteArrayOutputStream.toByteArray()
//        return Base64.encodeToString(byteArray, Base64.DEFAULT)
//    }
//
//    private fun convertBase64ToBitMap(base64String: String): Bitmap {
//        val decodedString = Base64.decode(base64String, Base64.DEFAULT)
//        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
//    }
//
//}



