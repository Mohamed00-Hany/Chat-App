package com.projects.domain.repositoriesContract.rooms

import com.projects.domain.models.Room
import com.projects.domain.repositoriesContract.TaskStates
import kotlinx.coroutines.flow.Flow


interface RoomsRepository {
    suspend fun insertRoom(room: Room): Flow<TaskStates>
    suspend fun getAllRooms(): Flow<TaskStates>
}

interface RoomsDataSource {
    suspend fun insertRoom(room: Room): Flow<TaskStates>
    suspend fun getAllRooms(): Flow<TaskStates>
}