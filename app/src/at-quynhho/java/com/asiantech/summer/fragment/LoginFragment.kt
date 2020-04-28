package com.asiantech.summer.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.asiantech.summer.R
import com.asiantech.summer.data.User
import com.asiantech.summer.database.NoteDatabase
import kotlinx.android.synthetic.`at-quynhho`.fragment_login.*


class LoginFragment : Fragment() {

    companion object {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btLogin.setOnClickListener {
            checkLogin()
        }
    }

    private fun checkLogin() {

        val edtName = edtUserName.text.toString()
        val edtPass = edtPassword.text.toString()
        when {
            edtName.isEmpty() -> {
                edtUserName.error = "Import username"
                edtUserName.requestFocus()
                return
            }
            edtPass.isEmpty() -> {
                edtPassword.error = "Import username"
                edtPassword.requestFocus()
                return
            }
        }
        context?.let {
            val db = NoteDatabase.newInstance(it)
            val user = db?.userDao()?.findByNameAndPass(mUserName = edtName.trim(), mPass = edtPass.trim())
            user.apply {
                Log.d("AAA", user.toString())
                fragmentManager?.beginTransaction()?.replace(R.id.flSignIn, RegisterFragment())?.commit()
                when {
                    user != null -> { fragmentManager?.beginTransaction()?.replace(R.id.flToDo, ToDoFragment())?.commit()}
                }
            }
        }
    }
}
