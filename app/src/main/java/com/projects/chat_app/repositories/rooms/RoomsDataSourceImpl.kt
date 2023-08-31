package com.projects.chat_app.repositories.rooms

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
import com.projects.chat_app.database.FireStoreUtils
import com.projects.chat_app.database.models.Room
import com.projects.chat_app.repositoriesContract.rooms.RoomsDataSource

class RoomsDataSourceImpl(private val dataBase: FireStoreUtils):RoomsDataSource {

    override suspend fun insertRoom(room: Room): Task<Void> {
        return dataBase.insertRoom(room)
    }

    override suspend fun getAllRooms(): Task<QuerySnapshot> {
        return dataBase.getAllRooms()
    }
}