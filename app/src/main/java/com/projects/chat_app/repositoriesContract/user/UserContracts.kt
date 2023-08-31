package com.projects.chat_app.repositoriesContract.user

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.projects.chat_app.database.models.User

interface UserRepository {
    suspend fun insertUser(user: User,onUserTaskCompletedListener: OnUserTaskCompleted)
    suspend fun getUser(userId:String,onUserTaskCompletedListener: OnUserTaskCompleted)
}
interface UserDataSource {
    suspend fun insertUser(user: User):Task<Void>
    suspend fun getUser(userId:String): Task<DocumentSnapshot>
}