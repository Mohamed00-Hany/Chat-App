package com.projects.chat_app.repositoriesContract.user

import com.projects.chat_app.database.models.User

interface OnUserTaskCompleted {
    fun onComplete()
    fun onSuccess(user: User?)
    fun onFail(error:String?)
}