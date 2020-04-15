package com.asiantech.summer.layoutfragment

import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import com.asiantech.summer.Activity
import com.asiantech.summer.MainActivity
import com.asiantech.summer.R
import kotlinx.android.synthetic.`at-quynhho`.fragment_my_profile.*


@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class MyProfileFragment : Fragment() {
    private var myProfile: UserProfile? = null

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

    //    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
////        arguments.let {
////            myProfile = arguments!!.getParcelable(USERPROFILE) as UserProfile
////        }
//
//    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        myProfile = arguments?.getParcelable<UserProfile>(USERPROFILE) as UserProfile
        return inflater.inflate(R.layout.fragment_my_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cvImg.setImageURI(Uri.parse(myProfile?.avatar))
        tvUserName.text = myProfile?.userName
        tvPhone.text = myProfile?.phone
        tvGender.text = myProfile?.gender
        tvBith.text = myProfile?.birthDay
        tvLanguage.text = myProfile?.language
        tvButton.setOnClickListener {
            fragmentManager?.beginTransaction()
                ?.replace(R.id.profileFragment, EditProfileFragment())
                ?.addToBackStack(null)
                ?.commit()
        }
        //this.setTargetFragment()
    }

}


