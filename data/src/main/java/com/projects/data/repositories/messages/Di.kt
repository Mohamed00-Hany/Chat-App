package com.projects.data.repositories.messages

import com.projects.domain.repositoriesContract.messages.MessagesDataSource
import com.projects.domain.repositoriesContract.messages.MessagesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class MessagesRepositoryModule {
    @Binds
    abstract fun getMessagesRepo(messagesRepositoryImpl: MessagesRepositoryImpl): MessagesRepository
}

@Module
@InstallIn(SingletonComponent::class)
abstract class MessagesDataSourceModule {
    @Binds
    abstract fun getMessagesDataSource(messagesDataSourceImpl: MessagesDataSourceImpl): MessagesDataSource
}