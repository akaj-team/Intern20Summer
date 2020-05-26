package com.asiantech.summer

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MainFood(var id: Int,
                    var avatar: String,
                    var name: String,
                    var title: String,
                    var price: Double,
                    var quantum:Int?=0) :
    Parcelable