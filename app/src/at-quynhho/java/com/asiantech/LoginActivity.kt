package com.asiantech

import Login
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.asiantech.summer.R
import kotlinx.android.synthetic.`at-quynhho`.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        tvbutton.setOnClickListener {
            login()
        }
        tvsignup.setOnClickListener() {
            val intent: Intent = Intent(this@LoginActivity, SignUpActivity::class.java)
            startActivity(intent)
        }
        tvFb.setOnClickListener {
            val intent: Intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com"))
            startActivity(intent)
        }
        tvTw.setOnClickListener {
            val intent: Intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.twitter.com"))
            startActivity(intent)
        }
        tvGg.setOnClickListener {
            val intent: Intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"))
            startActivity(intent)
        }
    }

    private fun login() {
        if (tvuser.text.toString().equals(Login.EMAIL) && tvpass.text.toString().equals(Login.PASSW)) {
            val mess = android.app.AlertDialog.Builder(this)

            mess.setMessage("Login success!")
                .setPositiveButton("OK", { dialogInterface: DialogInterface, i -> goHome() })
                .show()


        } else {

            val mess = android.app.AlertDialog.Builder(this)
            mess.setMessage("The account login is incorrect!")
                .setNegativeButton(
                    "OK",
                    { dialogInterface: DialogInterface, i -> goCheckMail() })
                .show()

        }
    }

    private fun goHome() {
        var intent = (Intent(this, MainActivity::class.java))
        intent.putExtra("data", "Login success")
        startActivity(intent)
    }

    private fun goCheckMail() {
        startActivity(Intent(this, CheckEmailActivity::class.java))
    }


}


