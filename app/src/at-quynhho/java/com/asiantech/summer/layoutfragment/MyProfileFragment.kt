package com.asiantech.summer.layoutfragment

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.asiantech.summer.R
import kotlinx.android.synthetic.`at-quynhho`.fragment_my_profile.*

class MyProfileFragment : Fragment() {
    private var myProfile: UserProfile? = null

    companion object {
        private const val USER_PROFILE = "user"
        fun newInstance(userProfile: UserProfile): MyProfileFragment {
            return MyProfileFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(USER_PROFILE, userProfile)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        myProfile = arguments?.getParcelable(USER_PROFILE)
        return inflater.inflate(R.layout.fragment_my_profile, container, false)
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
                tvUserName.text = userName
                tvPhone.text = phone
                tvGender.text = gender
                tvBith.text = birthDay
                tvLanguage.text = language
            }
            tvButton.setOnClickListener {
                fragmentManager?.beginTransaction()
                    ?.replace(R.id.profileFragment, EditProfileFragment.newInstance(myProfile!!))
                    ?.addToBackStack(null)
                    ?.commit()
            }
        }
    }
}


