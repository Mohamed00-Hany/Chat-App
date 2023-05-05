package com.projects.chat_app.database

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.projects.chat_app.database.models.Message
import com.projects.chat_app.database.models.Room

import com.projects.chat_app.database.models.User

class FireStoreUtils {

    private val db=FirebaseFirestore.getInstance()

    fun insertUser(user: User):Task<Void> {
        return db.collection("users").document(user.id!!).set(user)
    }

    fun getUser(userId:String):Task<DocumentSnapshot>
    {
        return db.collection("users").document(userId).get()
    }

    fun insertRoom(room: Room):Task<Void>
    {
        val docRef= db.collection("rooms").document()
        room.id=docRef.id
        return docRef.set(room)
    }

    fun getAllRooms(): Task<QuerySnapshot>
    {
        return db.collection("rooms").get()
    }

    fun sendMessage(message: Message):Task<Void>
    {
        val roomRef=db.collection("rooms").document(message.roomId?:"")
        val messageRef=roomRef.collection("messages").document()
        message.id=messageRef.id
        return messageRef.set(message)
    }

    fun getRoomMessages(roomId:String) : Query
    {
        return db.collection("rooms").document(roomId).collection("messages").orderBy("dateTime")
    }

    companion object{
        var fireStoreUtils:FireStoreUtils?=null

        fun getInstance():FireStoreUtils
        {
            if (fireStoreUtils==null)
            {
                fireStoreUtils=FireStoreUtils()
            }
            return fireStoreUtils!!
        }
    }

}