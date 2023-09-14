package com.projects.data.repositories.messages

import com.projects.data.database.FireStoreUtils
import com.projects.domain.models.Message
import com.projects.domain.repositoriesContract.TaskStates
import com.projects.domain.repositoriesContract.messages.MessagesDataSource
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class MessagesDataSourceImpl @Inject constructor(private val dataBase: FireStoreUtils) : MessagesDataSource {
    override suspend fun sendMessage(message: Message) = callbackFlow {
        val task = dataBase.sendMessage(message)
        task.addOnCompleteListener {
            if (!task.isSuccessful) {
                trySend(TaskStates.TaskFailed("Error sending your message"))
            }
        }
        awaitClose()
    }

    override suspend fun receiveMessages(roomId: String) = callbackFlow {
        val query = dataBase.getRoomMessages(roomId)
        val listener = query.addSnapshotListener { value, error ->
            if (error != null) {
                trySend(TaskStates.TaskFailed(error.localizedMessage))
            } else {
                value?.documentChanges?.forEach {
                    val message = it.document.toObject(Message::class.java)
                    trySend(TaskStates.TaskSucceed(message))
                }
            }
        }
        awaitClose { listener.remove() }
    }
}