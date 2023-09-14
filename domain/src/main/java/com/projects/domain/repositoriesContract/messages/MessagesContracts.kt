package com.projects.domain.repositoriesContract.messages

import com.projects.domain.models.Message
import com.projects.domain.repositoriesContract.TaskStates
import kotlinx.coroutines.flow.Flow

interface MessagesRepository {
    suspend fun sendMessage(message: Message): Flow<TaskStates>
    suspend fun receiveMessages(roomId: String): Flow<TaskStates>
}

interface MessagesDataSource {
    suspend fun sendMessage(message: Message): Flow<TaskStates>
    suspend fun receiveMessages(roomId: String): Flow<TaskStates>
}