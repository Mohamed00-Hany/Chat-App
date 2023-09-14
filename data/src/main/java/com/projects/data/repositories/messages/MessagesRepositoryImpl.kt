package com.projects.data.repositories.messages

import com.projects.domain.models.Message
import com.projects.domain.repositoriesContract.TaskStates
import com.projects.domain.repositoriesContract.messages.MessagesDataSource
import com.projects.domain.repositoriesContract.messages.MessagesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MessagesRepositoryImpl @Inject constructor(private val messagesDataSource: MessagesDataSource) :
    MessagesRepository {
    override suspend fun sendMessage(message: Message): Flow<TaskStates> {
        return messagesDataSource.sendMessage(message)
    }

    override suspend fun receiveMessages(roomId: String): Flow<TaskStates> {
        return messagesDataSource.receiveMessages(roomId)
    }
}