package com.projects.data.repositories.user

import com.projects.domain.models.User
import com.projects.domain.repositoriesContract.TaskStates
import com.projects.domain.repositoriesContract.user.UserDataSource
import com.projects.domain.repositoriesContract.user.UserRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val dataSource: UserDataSource) : UserRepository {
    override suspend fun insertUser(user: User): Flow<TaskStates> {
        return dataSource.insertUser(user)
    }
    override suspend fun getUser(userId: String): Flow<TaskStates> {
        return dataSource.getUser(userId)
    }
}