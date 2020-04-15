package com.asiantech

import Login
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.summer.R
import kotlinx.android.synthetic.`at-quynhho`.activity_sign_up.*


class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        tvButtonSp.setOnClickListener {
            if (tvusersignup.text.toString().trim() == (Login.EMAIL) &&
                tvpasssignup.text.toString().trim() == (Login.PASSW) &&
                tvConfpass.text.toString().trim() == (Login.RE_PASSW)
            ) {
                Toast.makeText(this, "Sign up success!", Toast.LENGTH_SHORT).show()
            }
        }
        tvSigin.setOnClickListener {
            val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
            startActivity(intent)
        }
        tvFbSgup.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com"))
            startActivity(intent)
        }
        tvTwSup.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.twitter.com"))
            startActivity(intent)
        }
        tvGgSup.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"))
            startActivity(intent)
        }
    }
}
