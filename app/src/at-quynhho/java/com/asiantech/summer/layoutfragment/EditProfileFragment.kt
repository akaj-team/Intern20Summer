package com.asiantech.summer.layoutfragment

import android.Manifest
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
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
    private val PERMISSION_CODE = 100
    private var image_uri: Uri? = null
    private var avatar: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        arguments.let {
//            avatar = it?.getString(ARG_AVATAR).toString()
//            userName = it?.getString(USER_NAME).toString()
//            phone = it?.getString(PHONE).toString()
//            gender = it?.getString(GENDER).toString()
//            birthDay = it?.getString(BIRTH_DAY).toString()
//            language = it?.getString(LANGUAGE).toString()
//        }
//        val myProfile = UserProfile
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_edit_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ivYes.setOnClickListener {
            var edtAvatar = image_uri.toString()
            var edtName = etUserName.text.toString()
            var edtPhone = etPhone.text.toString()
            var edtBirth = etBith.text.toString()
            var edtGender = etGender.text.toString()
            var edtLanguage = etLanguage.text.toString()
            var userProfile =
                UserProfile(edtAvatar, edtName, edtPhone, edtGender, edtBirth, edtLanguage)
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.profileFragment,MyProfileFragment.newInstance(userProfile))
                ?.commit()
        }
        if (avatar != "") {
            cvImg.setImageURI(avatar.toUri())
        }
        ivCamera.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_DENIED ||
                    ContextCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                    == PackageManager.PERMISSION_DENIED
                ) {
                    val permission = arrayOf(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
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
        image_uri = resolver?.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri)
        startActivityForResult(cameraIntent, PERMISSION_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    openCamera()
                } else {
                    Toast.makeText(this.activity, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK && requestCode == PERMISSION_CODE) {
            cvImg.setImageURI(image_uri)
            Log.d("vv", data?.data.toString())
            avatar = image_uri.toString()
            ivNo.setOnClickListener {
                fragmentManager?.beginTransaction()
                    ?.replace(R.id.profileFragment, MyProfileFragment(), null)
                    ?.addToBackStack(null)
                    ?.commit()
            }

        }
    }


}