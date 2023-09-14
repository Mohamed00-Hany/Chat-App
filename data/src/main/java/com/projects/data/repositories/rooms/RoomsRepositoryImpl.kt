package com.projects.data.repositories.rooms

import com.projects.domain.models.Room
import com.projects.domain.repositoriesContract.TaskStates
import com.projects.domain.repositoriesContract.rooms.RoomsDataSource
import com.projects.domain.repositoriesContract.rooms.RoomsRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class RoomsRepositoryImpl @Inject constructor(private val dataSource: RoomsDataSource) : RoomsRepository {
    override suspend fun insertRoom(room: Room): Flow<TaskStates> {
        return dataSource.insertRoom(room)
    }
    override suspend fun getAllRooms(): Flow<TaskStates> {
        return dataSource.getAllRooms()
    }
}