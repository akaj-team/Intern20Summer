package com.asiantech.summer.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataMessage(var user:String, var message: String, var time:Long): Parcelable