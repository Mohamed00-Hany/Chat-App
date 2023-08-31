package com.projects.chat_app.repositories.user

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.projects.chat_app.database.FireStoreUtils
import com.projects.chat_app.database.models.User
import com.projects.chat_app.repositoriesContract.user.UserDataSource

class UserDataSourceImpl(private val dataBase: FireStoreUtils):UserDataSource{
    override suspend fun insertUser(user: User): Task<Void> {
        return dataBase.insertUser(user)
    }

    override suspend fun getUser(userId: String): Task<DocumentSnapshot> {
        return dataBase.getUser(userId)
    }
}