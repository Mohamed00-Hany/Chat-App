package com.projects.chat_app.repositories.user

import com.projects.chat_app.database.models.User
import com.projects.chat_app.repositoriesContract.TaskStates
import com.projects.chat_app.repositoriesContract.user.UserDataSource
import com.projects.chat_app.repositoriesContract.user.UserRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect

class UserRepositoryImpl(private val dataSource: UserDataSource) : UserRepository {

    override suspend fun insertUser(user: User): Flow<TaskStates>  = callbackFlow {
        dataSource.insertUser(user).collect{ task ->
            task.addOnCompleteListener {
                trySend(TaskStates.TaskCompleted(null))
                if (task.isSuccessful) {
                    trySend(TaskStates.TaskSucceed(user))
                } else {
                    val error = task.exception?.localizedMessage
                    trySend(TaskStates.TaskFailed(error))
                }
            }
        }
        awaitClose()
    }

    override suspend fun getUser(userId: String): Flow<TaskStates> = callbackFlow {
        dataSource.getUser(userId).collect { task ->
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
        }
        awaitClose()
    }

}