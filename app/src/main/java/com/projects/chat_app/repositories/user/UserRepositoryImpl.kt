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
    override suspend fun insertUser(user: User): Flow<TaskStates> {
        return dataSource.insertUser(user)
    }
    override suspend fun getUser(userId: String): Flow<TaskStates> {
        return dataSource.getUser(userId)
    }
}