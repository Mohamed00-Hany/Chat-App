package com.projects.chat_app.ui.home

import com.projects.domain.repositoriesContract.rooms.RoomsRepository
import com.projects.domain.useCases.InsertRoom
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

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