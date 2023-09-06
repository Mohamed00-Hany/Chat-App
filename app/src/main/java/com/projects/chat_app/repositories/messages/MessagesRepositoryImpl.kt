package com.projects.chat_app.repositories.messages

import com.google.firebase.firestore.ListenerRegistration
import com.projects.chat_app.database.models.Message
import com.projects.chat_app.repositoriesContract.TaskStates
import com.projects.chat_app.repositoriesContract.messages.MessagesDataSource
import com.projects.chat_app.repositoriesContract.messages.MessagesRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class MessagesRepositoryImpl(private val messagesDataSource: MessagesDataSource) : MessagesRepository {
    override suspend fun sendMessage(message: Message) : Flow<TaskStates> = callbackFlow {
            messagesDataSource.sendMessage(message).collect {task ->
                task.addOnCompleteListener {
                    if (!task.isSuccessful) {
                        trySend(TaskStates.TaskFailed("Error sending your message"))
                    }
                }
            }
            awaitClose()
        }

    override suspend fun receiveMessages(roomId: String): Flow<TaskStates> = callbackFlow {
            var listener:ListenerRegistration?=null
            messagesDataSource.receiveMessages(roomId).collect { query ->
                listener=query.addSnapshotListener { value, error ->
                    if (error != null) {
                        trySend(TaskStates.TaskFailed(error.localizedMessage))
                    } else {
                        value?.documentChanges?.forEach {
                                val message = it.document.toObject(Message::class.java)
                            trySend(TaskStates.TaskSucceed(message))
                        }
                    }
                }
            }
            awaitClose { listener?.remove() }
        }
}