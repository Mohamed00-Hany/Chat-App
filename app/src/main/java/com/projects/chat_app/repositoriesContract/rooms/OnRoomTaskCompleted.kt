package com.projects.chat_app.repositoriesContract.rooms

import com.projects.chat_app.database.models.Room

interface OnRoomTaskCompleted {
    fun onComplete()
    fun onSuccess(room: Room?)
    fun onFail(error:String?)
}