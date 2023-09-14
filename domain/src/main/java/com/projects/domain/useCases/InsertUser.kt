package com.projects.domain.useCases

import com.projects.domain.models.User
import com.projects.domain.repositoriesContract.TaskStates
import com.projects.domain.repositoriesContract.user.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class InsertUser @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke(user: User): Flow<TaskStates>
    {
        return userRepository.insertUser(user)
    }
}