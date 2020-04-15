package com.asiantech.summer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.summer.layoutfragment.MyProfileFragment
import com.asiantech.summer.layoutfragment.UserProfile


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val userProfile = UserProfile("", "", "", "", "", "")
        supportFragmentManager.beginTransaction()
            .replace(R.id.profileFragment, MyProfileFragment.newInstance(userProfile))
            .addToBackStack(null)
            .commit()
    }

}
