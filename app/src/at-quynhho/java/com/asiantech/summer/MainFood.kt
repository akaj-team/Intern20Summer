package com.asiantech.summer

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MainFood(val id: Int,
                    val avatar: String,
                    val name: String,
                    val title: String,
                    val price: Double,
                    var quantum:Int?=0) :
    Parcelable