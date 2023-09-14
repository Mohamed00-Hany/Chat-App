package com.projects.chat_app.ui.chat

import com.projects.domain.repositoriesContract.messages.MessagesRepository
import com.projects.domain.useCases.ReceiveMessages
import com.projects.domain.useCases.SendMessages
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class ReceiveMessagesModule {
    @Provides
    fun bindReceiveMessages(messagesRepository: MessagesRepository): ReceiveMessages {
        return ReceiveMessages(messagesRepository)
    }
}

@Module
@InstallIn(SingletonComponent::class)
class SendMessagesModule {
    @Provides
    fun bindSendMessages(messagesRepository: MessagesRepository): SendMessages {
        return SendMessages(messagesRepository)
    }
}