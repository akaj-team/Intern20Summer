package com.asiantech.summer.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.asiantech.summer.R
import com.asiantech.summer.fragment.LoginFragment
import com.asiantech.summer.fragment.RegisterFragment

class AppActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app)
        supportFragmentManager.beginTransaction()
            .replace(R.id.flSignIn, LoginFragment())
            .commit()
    }
}
