package com.projects.chat_app.repositories.user

import com.projects.chat_app.database.FireStoreUtils
import com.projects.chat_app.database.models.User
import com.projects.chat_app.repositoriesContract.TaskStates
import com.projects.chat_app.repositoriesContract.user.UserDataSource
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

class UserDataSourceImpl(private val dataBase: FireStoreUtils) : UserDataSource {
    override suspend fun insertUser(user: User) = callbackFlow {
        val task = dataBase.insertUser(user)
        task.addOnCompleteListener {
            trySend(TaskStates.TaskCompleted(null))
            if (task.isSuccessful) {
                trySend(TaskStates.TaskSucceed(user))
            } else {
                val error = task.exception?.localizedMessage
                trySend(TaskStates.TaskFailed(error))
            }
        }
        awaitClose()
    }

    override suspend fun getUser(userId: String) = callbackFlow {
        val task = dataBase.getUser(userId)
        task.addOnCompleteListener {
            trySend(TaskStates.TaskCompleted(null))
            if (task.isSuccessful) {
                val user = task.result.toObject(User::class.java)
                trySend(TaskStates.TaskSucceed(user))
            } else {
                val error = task.exception?.localizedMessage
                trySend(TaskStates.TaskFailed(error))
            }
        }
        awaitClose()
    }
}