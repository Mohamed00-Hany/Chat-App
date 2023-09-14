package com.projects.domain.useCases

import com.projects.domain.repositoriesContract.TaskStates
import com.projects.domain.repositoriesContract.messages.MessagesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReceiveMessages @Inject constructor(private val messagesRepository: MessagesRepository) {
    suspend operator fun invoke(roomId:String):Flow<TaskStates>
    {
        return messagesRepository.receiveMessages(roomId)
    }
}