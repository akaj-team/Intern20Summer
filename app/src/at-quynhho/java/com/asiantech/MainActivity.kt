package com.asiantech

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.summer.R
import kotlinx.android.synthetic.`at-quynhho`.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intent = intent
        val user: String = intent.getStringExtra("data")
        tvLoginSuccess.text = user
    }
}