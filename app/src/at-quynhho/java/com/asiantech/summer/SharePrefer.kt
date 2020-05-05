package com.asiantech.summer

import android.content.Context
import android.content.SharedPreferences


@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class SharePrefer(val context: Context) {

    fun saveLogin(username:String, password:String ) {

        val sharePreference: SharedPreferences = context.getSharedPreferences(R.string.login.toString(), Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharePreference.edit()
        editor.putString("UserName",username)
        editor.putString("Password", password)
        editor.apply()
    }
    fun getUserName(): String? {
        val sharedPreferences:SharedPreferences = context.getSharedPreferences(R.string.login.toString(), Context.MODE_PRIVATE)
        return sharedPreferences.getString("UserName", "")
    }
    fun isUserLogOut(): Boolean {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(R.string.login.toString(), Context.MODE_PRIVATE)
        val isUserNameEmpty = sharedPreferences.getString("UserName", "").isEmpty()
        val isPasswordEmpty = sharedPreferences.getString("Password", "" ).isEmpty()
        return isUserNameEmpty || isPasswordEmpty
    }
}