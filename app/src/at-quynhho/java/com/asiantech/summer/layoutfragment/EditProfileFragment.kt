package com.asiantech.summer.layoutfragment

import android.Manifest
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
import kotlinx.android.synthetic.`at-quynhho`.fragment_edit_profile.cvImg


class EditProfileFragment : Fragment() {
    private val PERMISSION_CODE = 100
    private var image_uri: Uri? = null
    private var avatar: String = ""
    private var myProfile: UserProfile? = null
    private var myProfileEdit: UserProfile? = null

    companion object {
        private const val USERPROFILE = "user"
        fun newInstance(userProfile: UserProfile): EditProfileFragment {
            return EditProfileFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(USERPROFILE, userProfile)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        myProfile = arguments?.getParcelable(USERPROFILE)
        return inflater.inflate(R.layout.fragment_edit_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (myProfile != null) {
            myProfile?.apply {
                if (avatar.isEmpty()) {
                    cvImg.setImageResource(R.drawable.icons_user)
                } else {
                    cvImg.setImageURI(Uri.parse(avatar))
                }
                etUserName.setText(userName)
                etPhone.setText(phone)
                etBith.setText(birthDay)
                etGender.setText(gender)
                etLanguage.setText(language)
            }
        }

        ivYes.setOnClickListener {
            val edtAvatar = image_uri.toString()
            val edtName = etUserName.text.toString()
            val edtPhone = etPhone.text.toString()
            val edtBirth = etBith.text.toString()
            val edtGender = etGender.text.toString()
            val edtLanguage = etLanguage.text.toString()
            myProfileEdit =
                UserProfile(edtAvatar, edtName, edtPhone, edtGender, edtBirth, edtLanguage)
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.profileFragment, MyProfileFragment.newInstance(myProfileEdit!!))
                ?.commit()
        }
        ivNo.setOnClickListener {
            fragmentManager?.beginTransaction()
                ?.replace(R.id.profileFragment, MyProfileFragment())
                ?.addToBackStack(null)
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


        }
    }


}