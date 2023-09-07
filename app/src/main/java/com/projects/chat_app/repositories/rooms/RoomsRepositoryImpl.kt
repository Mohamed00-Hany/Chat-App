package com.projects.chat_app.repositories.rooms

import com.projects.chat_app.database.models.Room
import com.projects.chat_app.repositoriesContract.TaskStates
import com.projects.chat_app.repositoriesContract.rooms.RoomsDataSource
import com.projects.chat_app.repositoriesContract.rooms.RoomsRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class RoomsRepositoryImpl(val dataSource: RoomsDataSource) : RoomsRepository {
    override suspend fun insertRoom(room: Room): Flow<TaskStates> {
        return dataSource.insertRoom(room)
    }
    override suspend fun getAllRooms(): Flow<TaskStates> {
        return dataSource.getAllRooms()
    }
}