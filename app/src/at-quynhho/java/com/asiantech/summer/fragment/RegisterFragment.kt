package com.asiantech.summer.fragment

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build.*
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import com.asiantech.summer.R
import com.asiantech.summer.data.User
import com.asiantech.summer.database.NoteDatabase
import kotlinx.android.synthetic.`at-quynhho`.fragment_register.*
import android.Manifest
import android.app.Activity.RESULT_OK
import android.widget.Toast
import com.asiantech.summer.Activity


class RegisterFragment : Fragment() {

    private val IMAGE_AVATAR = ""
    private val IMAGE_CODE = 100
    private val PERMISSION_CODE = 101


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        civAvatar.apply {
            if (IMAGE_AVATAR.isEmpty()) {
                civAvatar.setImageResource(R.drawable.icons_user)
            } else {
                civAvatar.setImageURI(Uri.parse(IMAGE_AVATAR))
            }
        }
        civAvatar.setOnClickListener {
            if (VERSION.SDK_INT >= VERSION_CODES.M) {
                if (checkSelfPermission( requireContext(),Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED
                ) {
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(permissions, PERMISSION_CODE)
                } else {
                    pickImageFromGalley()
                }
            } else {
                pickImageFromGalley()
            }
        }
        btRegister.setOnClickListener {
            saveData()
        }
    }


    private fun pickImageFromGalley() {

        val intent = Intent(Intent.ACTION_PICK)
        startActivityForResult(intent, IMAGE_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_CODE -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                pickImageFromGalley()
            }
            else{
                Toast.makeText(this.activity, "Image denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK && requestCode == IMAGE_CODE){
            civAvatar.setImageURI(data?.data)
        }

    }

    private fun saveData() {

        val imgAvatar = civAvatar.setImageURI(Uri.parse(IMAGE_AVATAR)).toString()
        val edtName = edtUserName.text.toString()
        val edtNick = edtNickName.text.toString()
        val edtPass = edtPassword.text.toString()

        when {
            edtName.isEmpty() -> {
                edtUserName.error = "Import username"
                edtUserName.requestFocus()
                return
            }
            edtNick.isEmpty() -> {
                edtNickName.error = "Import nick name"
                edtNickName.requestFocus()
                return
            }
            edtPass.isEmpty() -> {
                edtPassword.error = "Import Password"
                edtPassword.requestFocus()
                return
            }
        }
        val user = User(
            userId = 0,
            avatar = imgAvatar,
            userName = edtName,
            nickName = edtNick,
            password = edtPass
        )
        context?.let {
            val db = NoteDatabase.newInstance(it)
            db?.userDao()?.insertAll(user)
            db?.userDao()?.getAll()?.let {
                Log.d("TAG11", "" + it[it.size - 1].userName)
            }

        }

    }
}
