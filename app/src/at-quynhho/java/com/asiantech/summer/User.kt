package com.asiantech.summer

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class User(val id: Int, var foodUser: ArrayList<MainFood>) : Parcelable