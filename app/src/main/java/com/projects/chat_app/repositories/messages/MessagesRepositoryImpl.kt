package com.projects.chat_app.repositories.messages

import com.google.firebase.firestore.ListenerRegistration
import com.projects.chat_app.database.models.Message
import com.projects.chat_app.repositoriesContract.messages.MessagesDataSource
import com.projects.chat_app.repositoriesContract.messages.MessagesRepository
import com.projects.chat_app.repositoriesContract.messages.OnReceiveMessagesTaskCompleted
import com.projects.chat_app.repositoriesContract.messages.OnSendMessagesTaskCompleted

class MessagesRepositoryImpl(private val messagesDataSource: MessagesDataSource) :
    MessagesRepository {
    override suspend fun sendMessage(message: Message,onSendMessagesTaskCompletedListener: OnSendMessagesTaskCompleted) {
        messagesDataSource.sendMessage(message).addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                onSendMessagesTaskCompletedListener.onFail("Error sending your message")
            }
        }
    }

    override suspend fun receiveMessages(roomId: String,onReceiveMessagesTaskCompleted: OnReceiveMessagesTaskCompleted): ListenerRegistration {
        val listener: ListenerRegistration =
            messagesDataSource.receiveMessages(roomId).addSnapshotListener { value, error ->
            if (error != null) {
                val error = error.localizedMessage
                onReceiveMessagesTaskCompleted.onFail(error)
            } else {
                value?.documentChanges?.forEach {
                    val message = it.document.toObject(Message::class.java)
                    onReceiveMessagesTaskCompleted.onSuccess(message)
                }
            }
        }
        return listener
    }
}