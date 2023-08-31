package com.projects.chat_app.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.projects.chat_app.database.models.Room
import com.projects.chat_app.repositories.rooms.RoomsDataSourceImpl
import com.projects.chat_app.repositories.rooms.RoomsRepositoryImpl
import com.projects.chat_app.repositoriesContract.rooms.OnAllRoomsTaskCompleted
import com.projects.chat_app.repositoriesContract.rooms.RoomsDataSource
import com.projects.chat_app.repositoriesContract.rooms.RoomsRepository
import com.projects.chat_app.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class HomeViewModel : BaseViewModel<Navigator>() {

    val roomsLiveData = MutableLiveData<List<Room>?>()
    private val roomsDataSource: RoomsDataSource = RoomsDataSourceImpl(dataBase)
    private val roomsRepository: RoomsRepository = RoomsRepositoryImpl(roomsDataSource)

    fun addRoomAction() {
        navigator?.openAddRoom()
    }

    fun loadRooms() {
        viewModelScope.launch {
            roomsRepository.getAllRooms(object : OnAllRoomsTaskCompleted {
                override fun onSuccess(rooms: List<Room>?) {
                    roomsLiveData.value = rooms
                }

                override fun onFail(error: String?) {
                    navigator?.showMessage(
                        message = error ?: "error loading rooms",
                        posActionTitle = "try again",
                        posAction = { loadRooms() })
                }
            })
        }
    }
}