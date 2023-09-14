package com.projects.chat_app.ui.home

import androidx.lifecycle.viewModelScope
import com.projects.domain.models.Room
import com.projects.domain.repositoriesContract.TaskStates
import com.projects.chat_app.ui.base.BaseViewModel
import com.projects.domain.useCases.GetAllRooms
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val getAllRooms: GetAllRooms): BaseViewModel<Navigator>() {

    val roomsStateFlow = MutableStateFlow<List<Room>?>(null)

    fun addRoomAction() {
        navigator?.openAddRoom()
    }

    fun loadRooms() {
        viewModelScope.launch {
            getAllRooms().collect {
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