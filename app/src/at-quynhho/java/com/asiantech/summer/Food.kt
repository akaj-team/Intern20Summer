package com.asiantech.summer

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Food(var id: Int,
                var avatar: String,
                var name: String,
                var title: String,
                var price: Double,
                var quantum: Int = 1) :
    Parcelable