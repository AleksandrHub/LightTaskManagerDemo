package com.niww.lighttaskmanager.model

data class ChatMessage(
    var text: String,
    var time: String,
    var date: String,
    val authorUid: String
)