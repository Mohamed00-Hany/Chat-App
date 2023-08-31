package com.projects.chat_app.repositoriesContract.messages

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import com.projects.chat_app.database.models.Message

interface MessagesRepository {
    suspend fun sendMessage(message: Message,onSendMessagesTaskCompletedListener: OnSendMessagesTaskCompleted)
    suspend fun receiveMessages(roomId: String,onReceiveMessagesTaskCompleted: OnReceiveMessagesTaskCompleted): ListenerRegistration
}

interface MessagesDataSource {
    suspend fun sendMessage(message: Message): Task<Void>
    suspend fun receiveMessages(roomId: String): Query
}