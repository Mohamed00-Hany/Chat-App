package com.projects.domain.models

import com.google.firebase.Timestamp
import java.text.SimpleDateFormat

data class Message(
    var id:String?=null,
    var content:String?=null,
    var dateTime:Timestamp?=null,
    var roomId:String?=null,
    var senderId:String?=null,
    var senderName:String?=null
)
{
    fun formatDateTime():String
    {
        val date=dateTime?.toDate()
        val dateFormatter=SimpleDateFormat("hh:mm a")
        return dateFormatter.format(date)
    }
}
