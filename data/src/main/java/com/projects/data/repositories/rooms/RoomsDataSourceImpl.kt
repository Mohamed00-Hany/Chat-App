package com.projects.data.repositories.rooms

import com.projects.data.database.FireStoreUtils
import com.projects.domain.models.Room
import com.projects.domain.repositoriesContract.TaskStates
import com.projects.domain.repositoriesContract.rooms.RoomsDataSource
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class RoomsDataSourceImpl @Inject constructor(private val dataBase: FireStoreUtils) : RoomsDataSource {

    override suspend fun insertRoom(room: Room) = callbackFlow {
        val task = dataBase.insertRoom(room)
        task.addOnCompleteListener {
            trySend(TaskStates.TaskCompleted(null))
            if (task.isSuccessful) {
                trySend(TaskStates.TaskSucceed(room))
            } else {
                val error = task.exception?.localizedMessage
                trySend(TaskStates.TaskFailed(error))
            }
        }
        awaitClose()
    }


    override suspend fun getAllRooms() = callbackFlow {
        val task = dataBase.getAllRooms()
        task.addOnCompleteListener {
            if (task.isSuccessful) {
                val rooms = task.result.toObjects(Room::class.java)
                trySend(TaskStates.TaskSucceed(rooms))
            } else {
                trySend(TaskStates.TaskFailed("error loading rooms"))
            }
        }
        awaitClose()
    }
}