package com.projects.chat_app.repositoriesContract.rooms

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
import com.projects.chat_app.database.models.Room


interface RoomsRepository {
    suspend fun insertRoom(room: Room,onRoomTaskCompletedListener: OnRoomTaskCompleted)
    suspend fun getAllRooms(onAllRoomsTaskCompletedListener: OnAllRoomsTaskCompleted)
}

interface RoomsDataSource {
    suspend fun insertRoom(room: Room):Task<Void>
    suspend fun getAllRooms(): Task<QuerySnapshot>
}