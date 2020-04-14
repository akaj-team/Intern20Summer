package com.asiantech.summer.layoutfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import com.asiantech.summer.R
import kotlinx.android.synthetic.`at-quynhho`.fragment_my_profile.*


@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class MyProfileFragment : Fragment() {

    private var userName : String = ""
    private var phone : String = ""
    private var gender : String = ""
    private var birthDay : String = ""
    private var language : String = ""
    private var avatar : String =""

    companion object{
        private const val ARG_AVATAR = "avatar"
        private const val USER_NAME = "User Name"
        private const val PHONE = "Phone"
        private const val GENDER = "Gender"
        private const val BIRTH_DAY = "Date of birth"
        private const val LANGUAGE = "Language"


        fun newInstance(avatar: String, userName: String, phone: String, gender: String, birthDay: String, language: String) = EditProfileFragment().apply{
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
        arguments?.let {
            avatar = it.getString(ARG_AVATAR).toString()
            userName = it.getString(USER_NAME).toString()
            phone = it.getString(PHONE).toString()
            gender = it.getString(GENDER).toString()
            birthDay = it.getString(BIRTH_DAY).toString()
            language = it.getString(LANGUAGE).toString()
        }
    }
//    private fun gender(Female: Boolean = true, Male: Boolean = false): Boolean {
//        if(true){
//            return Female
//        }else{
//            return Male
//        }
//    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvUserName.text = userName
        tvPhone.text = phone
        tvGender.text = gender
        tvBith.text = birthDay
        tvLanguage.text = language
        if (avatar != ""){
            cvImg.setImageURI(avatar.toUri())
        }
        tvButton.setOnClickListener{
            fragmentManager?.beginTransaction()
                ?.replace(R.id.profileFragment, EditProfileFragment.newInstance(avatar, userName, phone, gender, birthDay, language), null)
                ?.addToBackStack(null)
                ?.commit()
        }
        //this.setTargetFragment()
    }

}


