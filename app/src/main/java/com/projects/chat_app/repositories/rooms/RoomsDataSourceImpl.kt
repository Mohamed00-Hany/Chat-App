package com.projects.chat_app.repositories.rooms

import com.projects.chat_app.database.FireStoreUtils
import com.projects.chat_app.database.models.Room
import com.projects.chat_app.repositoriesContract.TaskStates
import com.projects.chat_app.repositoriesContract.rooms.RoomsDataSource
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

class RoomsDataSourceImpl(private val dataBase: FireStoreUtils) : RoomsDataSource {

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