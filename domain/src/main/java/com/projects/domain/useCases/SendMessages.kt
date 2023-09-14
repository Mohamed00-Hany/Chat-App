package com.projects.domain.useCases

import com.projects.domain.models.Message
import com.projects.domain.repositoriesContract.TaskStates
import com.projects.domain.repositoriesContract.messages.MessagesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SendMessages @Inject constructor(private val messagesRepository: MessagesRepository) {
    suspend operator fun invoke(message: Message): Flow<TaskStates>
    {
        return messagesRepository.sendMessage(message)
    }
}