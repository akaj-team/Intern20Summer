package com.asiantech.summer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.asiantech.summer.fragmentLayout.MenuMessageFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val menuMessageFragment = MenuMessageFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.menu_view, menuMessageFragment, null)
            .commit()
    }
}
