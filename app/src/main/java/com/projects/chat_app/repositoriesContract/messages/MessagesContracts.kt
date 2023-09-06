package com.projects.chat_app.repositoriesContract.messages

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.Query
import com.projects.chat_app.database.models.Message
import com.projects.chat_app.repositoriesContract.TaskStates
import kotlinx.coroutines.flow.Flow

interface MessagesRepository {
    suspend fun sendMessage(message: Message): Flow<TaskStates>
    suspend fun receiveMessages(roomId: String): Flow<TaskStates>
}

interface MessagesDataSource {
    suspend fun sendMessage(message: Message): Flow<Task<Void>>
    suspend fun receiveMessages(roomId: String): Flow<Query>
}