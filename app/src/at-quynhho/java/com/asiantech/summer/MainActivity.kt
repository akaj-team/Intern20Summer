package com.asiantech.summer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.asiantech.summer.fragmentLayout.HomePageFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val userMessageFragment = HomePageFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.flMainMessage, userMessageFragment)
            .commit()
    }
}
