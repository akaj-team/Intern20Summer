package com.asiantech.summer.layoutfragment

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.core.content.ContextCompat
import com.asiantech.summer.R
import kotlinx.android.synthetic.`at-quynhho`.fragment_edit_profile.*


class EditProfileFragment : Fragment() {

    private var userName: String = ""
    private var phone: String = ""
    private var gender: String = ""
    private var birthDay: String = ""
    private var language: String = ""
    private var avatar: String = ""
    private var image_uri: Unit? = null

    companion object {
        private const val ARG_AVATAR = "avatar"
        private const val USER_NAME = "User Name"
        private const val PHONE = "Phone"
        private const val GENDER = "Gender"
        private const val BIRTH_DAY = "Date of birth"
        private const val LANGUAGE = "Language"
        private const val PERMISSION_CODE = 100
        private const val IMAGE_CAPTURE_CODE = 101

        fun newInstance(
            avatar : String,
            userName: String,
            phone: String,
            gender: String,
            birthDay: String,
            language: String
        ) = EditProfileFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_AVATAR, avatar)
                putString(USER_NAME, userName)
                putString(PHONE, phone)
                putString(GENDER, gender)
                putString(BIRTH_DAY, birthDay)
                putString(LANGUAGE, language)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments.let {
            avatar = it?.getString(ARG_AVATAR).toString()
            userName = it?.getString(USER_NAME).toString()
            phone = it?.getString(PHONE).toString()
            gender = it?.getString(GENDER).toString()
            birthDay = it?.getString(BIRTH_DAY).toString()
            language = it?.getString(LANGUAGE).toString()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_edit_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        etUserName.setText(userName)
        etPhone.setText(phone)
        etBith.setText(birthDay)
        etGender.setText(gender)
        etLanguage.setText(language)
        if (avatar != "") {
            cvImg.setImageURI(avatar.toUri())
        }
        ivCamera.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_DENIED ||
                    ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED) {
                    val permission = arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    requestPermissions(permission, PERMISSION_CODE)
                } else {
                    openCamera()
                }
            } else {
                openCamera()
            }
        }
    }

    private fun openCamera() {
        val values = ContentValues()
        val resolver = context?.contentResolver
        values.put(MediaStore.Images.Media.TITLE, "New Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera")
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val mImage_uri = resolver?.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mImage_uri)
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED) {
                    openCamera()
                } else {
                    Toast.makeText(this.activity, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            cvImg.setImageURI(Uri.parse(image_uri.toString()))
            avatar = image_uri.toString()
            ivNo.setOnClickListener {
//                userName = etUserName.text.toString()
//                gender = etGender.text.toString()
//                phone = etPhone.text.toString()
//                birthDay = etBith.text.toString()
//                language = etLanguage.text.toString()
                fragmentManager?.beginTransaction()
                    ?.replace(R.id.profileFragment, MyProfileFragment(), null)
                    ?.addToBackStack(null)
                    ?.commit()
            }
            ivYes.setOnClickListener {
                userName = etUserName.text.toString()
                gender = etGender.text.toString()
                phone = etPhone.text.toString()
                birthDay = etBith.text.toString()
                language = etLanguage.text.toString()
                fragmentManager?.beginTransaction()
                    ?.replace(R.id.profileFragment, MyProfileFragment.newInstance(userName, gender, avatar, phone, birthDay, language), null)
                    ?.addToBackStack(null)
                    ?.commit()
            }
        }
    }


}