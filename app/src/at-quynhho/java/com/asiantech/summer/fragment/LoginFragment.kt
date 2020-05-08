package com.asiantech.summer.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.asiantech.summer.R
import com.asiantech.summer.SharePrefer
import com.asiantech.summer.database.NoteDatabase
import kotlinx.android.synthetic.`at-quynhho`.fragment_login.*

class LoginFragment : Fragment() {

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
        btClickRegister.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.flSignIn, RegisterFragment())
                ?.commit()
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
            val user =
                db?.userDao()?.findByNameAndPass(mUserName = edtName.trim(), mPass = edtPass.trim())
            user.apply {
                Log.d("AAA", user.toString())
                activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.flSignIn, RegisterFragment())
                    ?.commit()
                when {
                    user != null -> {
                        // Luu id user vao sharereferences
                        val sharePrefer = SharePrefer(requireContext())
                        sharePrefer.saveLogin(user.userId)
                        activity?.supportFragmentManager?.beginTransaction()
                            ?.replace(R.id.flSignIn, MenuFragment.newInstance())?.commit()
                    }
                }
            }
        }
    }
}
