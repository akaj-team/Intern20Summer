package com.asiantech.summer

import java.sql.Time


class DataSong(
    val id: Int = 0,
    val buttonPlay: Boolean,
    val nameSong: String,
    val nameSinger: String,
    val timePlay: Time
)