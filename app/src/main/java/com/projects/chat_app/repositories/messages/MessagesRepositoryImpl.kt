package com.projects.chat_app.repositories.messages

import com.projects.chat_app.database.models.Message
import com.projects.chat_app.repositoriesContract.TaskStates
import com.projects.chat_app.repositoriesContract.messages.MessagesDataSource
import com.projects.chat_app.repositoriesContract.messages.MessagesRepository
import kotlinx.coroutines.flow.Flow

class MessagesRepositoryImpl(private val messagesDataSource: MessagesDataSource) : MessagesRepository {
    override suspend fun sendMessage(message: Message): Flow<TaskStates> {
        return messagesDataSource.sendMessage(message)
    }

    override suspend fun receiveMessages(roomId: String): Flow<TaskStates> {
        return messagesDataSource.receiveMessages(roomId)
    }
}