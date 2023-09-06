package com.projects.chat_app.repositoriesContract.user

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.projects.chat_app.database.models.User
import com.projects.chat_app.repositoriesContract.TaskStates
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun insertUser(user: User): Flow<TaskStates>
    suspend fun getUser(userId:String): Flow<TaskStates>
}
interface UserDataSource {
    suspend fun insertUser(user: User): Flow<Task<Void>>
    suspend fun getUser(userId:String): Flow<Task<DocumentSnapshot>>
}