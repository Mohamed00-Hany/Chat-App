package com.projects.chat_app.repositoriesContract.messages

import com.projects.chat_app.database.models.Message

interface OnReceiveMessagesTaskCompleted {
    fun onSuccess(message: Message?)
    fun onFail(error:String?)
}