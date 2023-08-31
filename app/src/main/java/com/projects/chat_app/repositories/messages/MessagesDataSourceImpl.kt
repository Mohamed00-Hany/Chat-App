package com.projects.chat_app.repositories.messages

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.Query
import com.projects.chat_app.database.FireStoreUtils
import com.projects.chat_app.database.models.Message
import com.projects.chat_app.repositoriesContract.messages.MessagesDataSource

class MessagesDataSourceImpl(private val dataBase: FireStoreUtils) : MessagesDataSource {
    override suspend fun sendMessage(message: Message): Task<Void> {
        return dataBase.sendMessage(message)
    }

    override suspend fun receiveMessages(roomId: String): Query {
        return dataBase.getRoomMessages(roomId)
    }
}