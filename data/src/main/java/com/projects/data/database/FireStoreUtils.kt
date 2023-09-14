package com.projects.data.database

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.projects.domain.models.Message
import com.projects.domain.models.Room
import com.projects.domain.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FireStoreUtils(private val db: FirebaseFirestore) {

    suspend fun insertUser(user: User):Task<Void> {
        val task:Task<Void>
        withContext(Dispatchers.IO)
        {
            task=db.collection("users").document(user.id!!).set(user)
        }
        return task
    }

    suspend fun getUser(userId:String):Task<DocumentSnapshot>
    {
        val task:Task<DocumentSnapshot>
        withContext(Dispatchers.IO)
        {
            task=db.collection("users").document(userId).get()
        }
        return task
    }

    suspend fun insertRoom(room: Room):Task<Void>
    {
        val docRef= db.collection("rooms").document()
        room.id=docRef.id
        val task:Task<Void>
        withContext(Dispatchers.IO)
        {
            task=docRef.set(room)
        }
        return task
    }

    suspend fun getAllRooms(): Task<QuerySnapshot>
    {
        val task:Task<QuerySnapshot>
        withContext(Dispatchers.IO)
        {
            task=db.collection("rooms").get()
        }
        return task
    }

    suspend fun sendMessage(message: Message):Task<Void>
    {
        val roomRef=db.collection("rooms").document(message.roomId?:"")
        val messageRef=roomRef.collection("messages").document()
        message.id=messageRef.id
        val task:Task<Void>
        withContext(Dispatchers.IO)
        {
            task=messageRef.set(message)
        }
        return task
    }

    suspend fun getRoomMessages(roomId:String) : Query
    {
        val query:Query
        withContext(Dispatchers.IO)
        {
            query=db.collection("rooms").document(roomId).collection("messages").orderBy("dateTime")
        }
        return query
    }

}