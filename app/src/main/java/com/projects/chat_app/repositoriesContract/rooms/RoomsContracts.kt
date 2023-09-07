package com.projects.chat_app.repositoriesContract.rooms

import com.projects.chat_app.database.models.Room
import com.projects.chat_app.repositoriesContract.TaskStates
import kotlinx.coroutines.flow.Flow


interface RoomsRepository {
    suspend fun insertRoom(room: Room): Flow<TaskStates>
    suspend fun getAllRooms(): Flow<TaskStates>
}

interface RoomsDataSource {
    suspend fun insertRoom(room: Room): Flow<TaskStates>
    suspend fun getAllRooms(): Flow<TaskStates>
}