package com.projects.chat_app.ui.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.ktx.toObjects
import com.projects.chat_app.database.models.Room
import com.projects.chat_app.ui.base.BaseViewModel

class HomeViewModel:BaseViewModel<Navigator>() {

    val roomsLiveData=MutableLiveData<List<Room>>()

    fun addRoomAction()
    {
        navigator?.openAddRoom()
    }

    fun loadRooms()
    {
        dataBase.getAllRooms().addOnCompleteListener {task->
            if(task.isSuccessful)
            {
                roomsLiveData.value=task.result.toObjects(Room::class.java)
            }
            else
            {
                navigator?.showMessage("error loading rooms")
            }
        }
    }

}