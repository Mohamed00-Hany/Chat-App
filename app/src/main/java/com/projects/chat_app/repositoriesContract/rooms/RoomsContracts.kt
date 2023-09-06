package com.projects.chat_app.repositoriesContract.rooms

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
import com.projects.chat_app.database.models.Room
import com.projects.chat_app.repositoriesContract.TaskStates
import kotlinx.coroutines.flow.Flow


interface RoomsRepository {
    suspend fun insertRoom(room: Room): Flow<TaskStates>
    suspend fun getAllRooms(): Flow<TaskStates>
}

interface RoomsDataSource {
    suspend fun insertRoom(room: Room): Flow<Task<Void>>
    suspend fun getAllRooms(): Flow<Task<QuerySnapshot>>
}