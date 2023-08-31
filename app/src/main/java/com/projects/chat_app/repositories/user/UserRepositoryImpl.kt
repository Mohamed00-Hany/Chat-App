package com.projects.chat_app.repositories.user

import com.projects.chat_app.database.models.User
import com.projects.chat_app.repositoriesContract.user.OnUserTaskCompleted
import com.projects.chat_app.repositoriesContract.user.UserDataSource
import com.projects.chat_app.repositoriesContract.user.UserRepository

class UserRepositoryImpl(private val dataSource: UserDataSource) : UserRepository {

    override suspend fun insertUser(user: User, onUserTaskCompletedListener: OnUserTaskCompleted) {
        dataSource.insertUser(user).addOnCompleteListener { task ->
            onUserTaskCompletedListener.onComplete()
            if (task.isSuccessful) {
                onUserTaskCompletedListener.onSuccess(user)
            } else {
                val error = task.exception?.localizedMessage
                onUserTaskCompletedListener.onFail(error)
            }
        }
    }

    override suspend fun getUser(userId: String, onUserTaskCompletedListener: OnUserTaskCompleted) {
        dataSource.getUser(userId).addOnCompleteListener { task ->
            onUserTaskCompletedListener.onComplete()
            if (task.isSuccessful) {
                val user = task.result.toObject(User::class.java)
                onUserTaskCompletedListener.onSuccess(user)
            } else {
                val error = task.exception?.localizedMessage
                onUserTaskCompletedListener.onFail(error)
            }
        }
    }

}