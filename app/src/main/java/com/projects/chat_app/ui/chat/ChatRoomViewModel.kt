package com.projects.chat_app.ui.chat

import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import com.projects.chat_app.database.models.Message
import com.projects.chat_app.database.models.Room
import com.projects.chat_app.repositories.messages.MessagesDataSourceImpl
import com.projects.chat_app.repositories.messages.MessagesRepositoryImpl
import com.projects.chat_app.repositoriesContract.messages.MessagesDataSource
import com.projects.chat_app.repositoriesContract.messages.MessagesRepository
import com.projects.chat_app.repositoriesContract.messages.OnSendMessagesTaskCompleted
import com.projects.chat_app.ui.UserProvider
import com.projects.chat_app.ui.base.BaseViewModel
import kotlinx.coroutines.launch


class ChatRoomViewModel : BaseViewModel<Navigator>() {
    var activeRoom:Room?=null
    val messageContent=ObservableField<String>()
    val messagesDataSource:MessagesDataSource=MessagesDataSourceImpl(dataBase)
    val messagesRepository:MessagesRepository=MessagesRepositoryImpl(messagesDataSource)

    fun sendMessage()
    {
        if (messageContent.get().isNullOrBlank())
            return

        val message=Message(content = messageContent.get()?.trim(), roomId = activeRoom?.id, senderId = UserProvider.user?.id,
            senderName = UserProvider.user?.userName, dateTime = Timestamp.now())

        messageContent.set("")

        viewModelScope.launch {
            messagesRepository.sendMessage(message,object :OnSendMessagesTaskCompleted {
                override fun onFail(error: String?) {
                    messageContent.set(message.content)
                    navigator?.showMessage(error ?: "Error sending your message", posActionTitle = "Try again", posAction = { sendMessage() },
                        negActionTitle = "Ok")
                }
            })
        }
    }

}