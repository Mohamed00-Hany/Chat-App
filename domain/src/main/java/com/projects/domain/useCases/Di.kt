package com.projects.domain.useCases

import com.projects.domain.repositoriesContract.messages.MessagesRepository
import com.projects.domain.repositoriesContract.rooms.RoomsRepository
import com.projects.domain.repositoriesContract.user.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class GetAllRoomsModule
{
    @Provides
    fun getAllRooms(roomsRepository: RoomsRepository): GetAllRooms
    {
        return GetAllRooms(roomsRepository)
    }
}

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

@Module
@InstallIn(SingletonComponent::class)
class InsertRoomModule
{
    @Provides
    fun insertRoom(roomsRepository: RoomsRepository): InsertRoom
    {
        return InsertRoom(roomsRepository)
    }
}

@Module
@InstallIn(SingletonComponent::class)
class GetUserModule
{
    @Provides
    fun getUser(userRepository: UserRepository):GetUser
    {
        return GetUser(userRepository)
    }
}

@Module
@InstallIn(SingletonComponent::class)
class InsertUserModule
{
    fun insertUser(userRepository: UserRepository): InsertUser
    {
        return InsertUser(userRepository)
    }
}