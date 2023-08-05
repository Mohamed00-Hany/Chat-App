package com.projects.chat_app.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.projects.chat_app.database.models.Room
import com.projects.chat_app.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class HomeViewModel:BaseViewModel<Navigator>() {

    val roomsLiveData=MutableLiveData<List<Room>>()

    fun addRoomAction()
    {
        navigator?.openAddRoom()
    }

    fun loadRooms()
    {
        viewModelScope.launch {
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

}