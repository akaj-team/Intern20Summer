package com.asiantech.summer.layoutfragment

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class UserProfile(
    var avatar: String,
    var userName: String,
    var phone: String,
    var gender: String,
    var birthDay: String,
    var language: String
) : Parcelable
