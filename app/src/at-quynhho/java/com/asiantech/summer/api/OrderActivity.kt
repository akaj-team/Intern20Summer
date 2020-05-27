package com.asiantech.summer.api

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.asiantech.summer.R
import com.asiantech.summer.adapter.HomeShoppingFragment

class OrderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)
        supportFragmentManager.beginTransaction().replace(R.id.flShopping, HomeShoppingFragment())
            .commit()
    }
}
