package com.asiantech.summer

import android.content.Context
import android.content.SharedPreferences


@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class SharePrefer(val context: Context) {

    companion object {
        private const val SHARE_PREFER = "share_prefer"
        private const val SHARE_PREFER_TODO = "share_prefer"
    }

    fun saveLogin(id: Int) {
        val sharePreference: SharedPreferences =
            context.getSharedPreferences(SHARE_PREFER, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharePreference.edit()
        editor.putInt("id", id)
        editor.apply()
    }

    fun getLogin(): Int {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(SHARE_PREFER, Context.MODE_PRIVATE)
        return sharedPreferences.getInt("id", -1)
    }

    fun getUserName(): String? {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(SHARE_PREFER, Context.MODE_PRIVATE)
        return sharedPreferences.getString("UserName", "")
    }

    fun isUserLogOut(): Boolean {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(SHARE_PREFER, Context.MODE_PRIVATE)
        val isUserNameEmpty = sharedPreferences.getString("UserName", "").isEmpty()
        val isPasswordEmpty = sharedPreferences.getString("Password", "").isEmpty()

        return isUserNameEmpty || isPasswordEmpty
    }
}