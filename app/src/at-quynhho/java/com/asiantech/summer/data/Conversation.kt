package com.asiantech.summer.data

data class Conversation(
    val avatarUser: Int,
    val userNameUser: String,
    val messageUser: String,
    var numberMessageUnRead: Int,
    var isLike: Boolean
)

