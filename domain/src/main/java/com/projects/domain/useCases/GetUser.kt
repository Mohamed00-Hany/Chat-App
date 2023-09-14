package com.projects.domain.useCases

import com.projects.domain.repositoriesContract.TaskStates
import com.projects.domain.repositoriesContract.user.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUser @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke(userId:String): Flow<TaskStates>
    {
        return userRepository.getUser(userId)
    }
}