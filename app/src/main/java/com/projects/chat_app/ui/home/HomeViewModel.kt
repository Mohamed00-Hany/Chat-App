package com.projects.chat_app.ui.home

import androidx.lifecycle.viewModelScope
import com.projects.chat_app.database.models.Room
import com.projects.chat_app.database.models.User
import com.projects.chat_app.repositories.rooms.RoomsDataSourceImpl
import com.projects.chat_app.repositories.rooms.RoomsRepositoryImpl
import com.projects.chat_app.repositoriesContract.TaskStates
import com.projects.chat_app.repositoriesContract.rooms.RoomsDataSource
import com.projects.chat_app.repositoriesContract.rooms.RoomsRepository
import com.projects.chat_app.ui.UserProvider
import com.projects.chat_app.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel : BaseViewModel<Navigator>() {

    val roomsStateFlow = MutableStateFlow<List<Room>?>(null)
    private val roomsDataSource: RoomsDataSource = RoomsDataSourceImpl(dataBase)
    private val roomsRepository: RoomsRepository = RoomsRepositoryImpl(roomsDataSource)

    fun addRoomAction() {
        navigator?.openAddRoom()
    }

    fun loadRooms() {
        viewModelScope.launch {
            roomsRepository.getAllRooms().collect {
                when (it) {
                    is TaskStates.TaskSucceed<*> -> {
                        val rooms = it.data as List<Room>
                        roomsStateFlow.value = rooms
                    }
                    else -> {
                        val taskFailed = it as TaskStates.TaskFailed
                        navigator?.showMessage(
                            message = taskFailed.error ?: "error loading rooms",
                            posActionTitle = "try again",
                            posAction = { loadRooms() })
                    }
                }
            }
        }
    }

}