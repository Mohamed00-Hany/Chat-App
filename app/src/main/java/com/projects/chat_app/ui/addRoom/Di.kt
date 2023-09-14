package com.projects.chat_app.ui.addRoom

import com.projects.domain.repositoriesContract.rooms.RoomsRepository
import com.projects.domain.useCases.GetAllRooms
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