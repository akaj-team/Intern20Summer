package com.asiantech.summer.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar;
import com.asiantech.summer.R
import com.asiantech.summer.SharePrefer
import com.asiantech.summer.data.User
import com.asiantech.summer.fragment.LoginFragment
import com.asiantech.summer.fragment.MenuFragment

class AppActivity : AppCompatActivity() {

    private lateinit var user: User
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app)
        val sharePrefer = SharePrefer(this)
        if (sharePrefer.getLogin() == -1) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.flSignIn, LoginFragment())
                .commit()
        }
        if (!sharePrefer.isUserLogOut()) {
            finish()
            supportFragmentManager.beginTransaction()
                .replace(R.id.flSignIn, LoginFragment())
                .commit()
        } else {
            supportFragmentManager.beginTransaction()
                .replace(R.id.flSignIn, MenuFragment())
                .commit()
        }
    }
}

