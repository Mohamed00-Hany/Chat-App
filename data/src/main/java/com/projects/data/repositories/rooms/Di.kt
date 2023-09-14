package com.projects.data.repositories.rooms

import com.projects.domain.repositoriesContract.rooms.RoomsDataSource
import com.projects.domain.repositoriesContract.rooms.RoomsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RoomRepositoryModule
{
    @Binds
    abstract fun bindRoomRepository(roomsRepositoryImpl: RoomsRepositoryImpl): RoomsRepository
}

@Module
@InstallIn(SingletonComponent::class)
abstract class RoomsDataSourceModule
{
    @Binds
    abstract fun bindRoomsDataSource(roomsDataSourceImpl: RoomsDataSourceImpl): RoomsDataSource
}