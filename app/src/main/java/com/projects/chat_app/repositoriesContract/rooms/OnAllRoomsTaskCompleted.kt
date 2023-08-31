package com.projects.chat_app.repositoriesContract.rooms

import com.projects.chat_app.database.models.Room

interface OnAllRoomsTaskCompleted {
    fun onSuccess(rooms: List<Room>?)
    fun onFail(error:String?)
}