package com.projects.chat_app.repositories.rooms

import com.projects.chat_app.database.models.Room
import com.projects.chat_app.repositoriesContract.rooms.OnAllRoomsTaskCompleted
import com.projects.chat_app.repositoriesContract.rooms.OnRoomTaskCompleted
import com.projects.chat_app.repositoriesContract.rooms.RoomsDataSource
import com.projects.chat_app.repositoriesContract.rooms.RoomsRepository

class RoomsRepositoryImpl(val dataSource: RoomsDataSource) : RoomsRepository {
    override suspend fun insertRoom(room: Room, onRoomTaskCompletedListener: OnRoomTaskCompleted) {
        dataSource.insertRoom(room).addOnCompleteListener { task ->
            onRoomTaskCompletedListener.onComplete()
            if (task.isSuccessful) {
                onRoomTaskCompletedListener.onSuccess(room)
            } else {
                val error = task.exception?.localizedMessage
                onRoomTaskCompletedListener.onFail(error)
            }
        }
    }

    override suspend fun getAllRooms(onAllRoomsTaskCompletedListener: OnAllRoomsTaskCompleted) {
        dataSource.getAllRooms().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val rooms = task.result.toObjects(Room::class.java)
                onAllRoomsTaskCompletedListener.onSuccess(rooms)
            } else {
                onAllRoomsTaskCompletedListener.onFail("error loading rooms")
            }
        }
    }
}