package com.projects.chat_app.repositories.messages

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.Query
import com.projects.chat_app.database.FireStoreUtils
import com.projects.chat_app.database.models.Message
import com.projects.chat_app.repositoriesContract.messages.MessagesDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MessagesDataSourceImpl(private val dataBase: FireStoreUtils) : MessagesDataSource {
    override suspend fun sendMessage(message: Message): Flow<Task<Void>> {
        return flow{
            emit(dataBase.sendMessage(message))
        }
    }

    override suspend fun receiveMessages(roomId: String): Flow<Query> {
        return flow {
            emit(dataBase.getRoomMessages(roomId))
        }
    }
}