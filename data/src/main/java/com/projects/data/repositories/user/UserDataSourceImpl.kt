package com.projects.data.repositories.user

import com.projects.data.database.FireStoreUtils
import com.projects.domain.models.User
import com.projects.domain.repositoriesContract.TaskStates
import com.projects.domain.repositoriesContract.user.UserDataSource
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(private val dataBase: FireStoreUtils) : UserDataSource {
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