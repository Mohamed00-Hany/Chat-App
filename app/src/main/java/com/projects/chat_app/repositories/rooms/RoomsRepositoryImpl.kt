package com.projects.chat_app.repositories.rooms

import com.projects.chat_app.database.models.Room
import com.projects.chat_app.repositoriesContract.TaskStates
import com.projects.chat_app.repositoriesContract.rooms.RoomsDataSource
import com.projects.chat_app.repositoriesContract.rooms.RoomsRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class RoomsRepositoryImpl(val dataSource: RoomsDataSource) : RoomsRepository {
    override suspend fun insertRoom(room: Room): Flow<TaskStates>  = callbackFlow {
        dataSource.insertRoom(room).collect { task ->
            task.addOnCompleteListener {
                trySend(TaskStates.TaskCompleted(null))
                if (task.isSuccessful) {
                    trySend(TaskStates.TaskSucceed(room))
                } else {
                    val error = task.exception?.localizedMessage
                    trySend(TaskStates.TaskFailed(error))
                }
            }
        }
        awaitClose()
    }

    override suspend fun getAllRooms(): Flow<TaskStates>  = callbackFlow {
        dataSource.getAllRooms().collect { task ->
            task.addOnCompleteListener {
                if (task.isSuccessful) {
                    val rooms = task.result.toObjects(Room::class.java)
                    trySend(TaskStates.TaskSucceed(rooms))
                } else {
                    trySend(TaskStates.TaskFailed("error loading rooms"))
                }
            }
        }
        awaitClose()
    }
}