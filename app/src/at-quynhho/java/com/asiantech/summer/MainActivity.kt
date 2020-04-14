package com.asiantech.summer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.asiantech.summer.layoutfragment.MyProfileFragment


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
                    .replace(R.id.profileFragment, MyProfileFragment(), null)
                    .addToBackStack(null)
                    .commit()
    }

}
