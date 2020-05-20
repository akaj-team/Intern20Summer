package com.asiantech.summer

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataMedia(
    var path: String,
    val musicName: String,
    val musicArtist: String,
    val imageMusic: String,
    val timePlay: Int
) : Parcelable


