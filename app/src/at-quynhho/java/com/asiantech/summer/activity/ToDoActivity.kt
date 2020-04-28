package com.asiantech.summer.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.asiantech.summer.R
import com.asiantech.summer.fragment.ToDoFragment

class ToDoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_do)
        supportFragmentManager.beginTransaction()
            .replace(R.id.flToDo, ToDoFragment())
            .commit()
    }
}
