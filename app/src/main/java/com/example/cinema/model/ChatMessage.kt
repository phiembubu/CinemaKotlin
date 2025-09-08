package com.example.cinema.model


import com.google.firebase.auth.FirebaseAuth
import java.io.Serializable
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class ChatMessage : Serializable {

    var messageID: String = ""

    var senderID: String = ""

    var text: String = ""

    var timeSent: Long = 0

    var isMine = false


    constructor() : this("", "", "", 0L)
    constructor(messageID: String, senderID: String, text: String, timeSent: Long) {
        this.messageID = messageID
        this.senderID = senderID
        this.text = text
        this.timeSent = timeSent
        isMine = if (senderID == FirebaseAuth.getInstance().currentUser!!.uid) {
            true
        } else {
            false
        }
    }

    fun toMap(): Map<String, Any> {
        val map: MutableMap<String, Any> = HashMap()
        map["messageID"] = messageID
        map["senderID"] = senderID
        map["timeSent"] = timeSent
        map["text"] = text
        map["isMine"] = isMine
        return map
    }
}