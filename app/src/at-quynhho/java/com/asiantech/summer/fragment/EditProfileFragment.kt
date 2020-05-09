package com.asiantech.summer.fragment

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.asiantech.summer.R
import com.asiantech.summer.SharePrefer
import com.asiantech.summer.data.User
import com.asiantech.summer.database.NoteDatabase
import kotlinx.android.synthetic.`at-quynhho`.item_header_edit_profile.*

class EditProfileFragment : Fragment() {

    private var imageGallery: Uri? = null

    companion object {
        private val IMAGE_CODE = 100
        private val PERMISSION_CODE = 101
        private val USER_PROFILE = "user"
        fun newInstance(user: User): EditProfileFragment {
            return EditProfileFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(USER_PROFILE, user)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.item_header_edit_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        civAvatarEdit.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    )
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
        btEditProfile.setOnClickListener {
            saveData()
            activity?.supportFragmentManager?.popBackStack()
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_CODE -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                pickImageFromGalley()
            } else {
                Toast.makeText(this.activity, "Image denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_CODE) {
            imageGallery = data?.data
            Log.d("images", "Select From Image Gallery: " + imageGallery.toString())
            civAvatarEdit.setImageURI(imageGallery)
        }
    }

    private fun pickImageFromGalley() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_CODE)
    }

    private fun saveData() {
        val imgAvatar = imageGallery.toString()
        val edtName = edtUserNameEdit.text.toString()
        val edtNick = edtNickNameEdit.text.toString()
        val edtPass = edtPasswordEdit.text.toString()

        when {
            imgAvatar.isEmpty() -> {
                civAvatarEdit.requestFocus()
                return
            }
            edtName.isEmpty() -> {
                edtUserNameEdit.requestFocus()
                return
            }
            edtNick.isEmpty() -> {
                edtNickNameEdit.requestFocus()
                return
            }
            edtPass.isEmpty() -> {
                edtPasswordEdit.requestFocus()
                return
            }
        }
        context?.let {
            val db = NoteDatabase.newInstance(it)
            val sharePrefer = SharePrefer(it)
            val userId = sharePrefer.getLogin() //get id tu share sau do edit luu vao db
            db?.userDao()?.findUserId(id)
            val user = User(
                userId = userId,
                avatar = imgAvatar,
                userName = edtName,
                nickName = edtNick,
                password = edtPass
            )
            db?.userDao()?.updateAll(user)
            db?.userDao()?.getAll()
        }
    }
}
