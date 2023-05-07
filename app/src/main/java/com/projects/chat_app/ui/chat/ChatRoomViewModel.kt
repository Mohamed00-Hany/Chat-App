package com.projects.chat_app.ui.chat

import androidx.databinding.ObservableField
import com.google.firebase.Timestamp
import com.projects.chat_app.database.models.Message
import com.projects.chat_app.database.models.Room
import com.projects.chat_app.database.models.User
import com.projects.chat_app.ui.UserProvider
import com.projects.chat_app.ui.base.BaseViewModel


class ChatRoomViewModel : BaseViewModel<Navigator>() {
    var activeRoom:Room?=null
    val messageContent=ObservableField<String>()
    val user:User?=UserProvider.user

    fun sendMessage()
    {
        if (messageContent.get().isNullOrBlank())
            return

        val message=Message(content = messageContent.get()?.trim(), roomId = activeRoom?.id, senderId = user?.id,
            senderName = user?.userName, dateTime = Timestamp.now())

        messageContent.set("")

        dataBase.sendMessage(message).addOnCompleteListener {task->
            if(!task.isSuccessful)
            {
                messageContent.set(message.content)
                navigator?.showMessage("Error sending your message", posActionTitle = "Try again", posAction = {
                    sendMessage() },
                    negActionTitle = "Ok")

            }
        }
    }

}