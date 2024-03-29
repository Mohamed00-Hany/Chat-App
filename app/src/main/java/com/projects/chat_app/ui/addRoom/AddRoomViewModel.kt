package com.projects.chat_app.ui.addRoom

import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.projects.domain.models.Room
import com.projects.domain.repositoriesContract.TaskStates
import com.projects.chat_app.ui.UserProvider
import com.projects.chat_app.ui.base.BaseViewModel
import com.projects.domain.useCases.InsertRoom
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddRoomViewModel  @Inject constructor(private val insertRoom: InsertRoom) : BaseViewModel<Navigator>() {
    val roomName = ObservableField<String>()
    val roomDescription = ObservableField<String>()
    val roomNameError = ObservableField<String>()
    val roomDescriptionError = ObservableField<String>()
    var selectedRoomCategory: RoomCategory = RoomCategory.getListRoomCategories()[0]

    fun createRoom() {
        if (!validInputs())
            return
        val room = Room(
            name = roomName.get(),
            description = roomDescription.get(),
            roomCategoryId = selectedRoomCategory.id,
            createdBy = UserProvider.user?.id,
            id = null
        )
        navigator?.showLoading("Creating...")
        viewModelScope.launch {
            insertRoom(room).collect {
                when (it) {
                    is TaskStates.TaskCompleted<*> -> {
                        navigator?.hideLoading()
                    }
                    is TaskStates.TaskSucceed<*> -> {
                        navigator?.showMessage("Room created successfully", "Ok",
                            posAction = { navigator?.goToHome() })
                    }
                    else -> {
                        val taskFailed = it as TaskStates.TaskFailed
                        navigator?.showMessage(taskFailed.error ?: "Error, can't add room")
                    }
                }
            }
        }
    }


    fun validInputs(): Boolean {
        var isValid = true

        if (roomName.get().isNullOrBlank()) {
            isValid = false
            roomNameError.set("Please enter room's name")
        } else {
            roomNameError.set(null)
        }

        if (roomDescription.get().isNullOrBlank()) {
            isValid = false
            roomDescriptionError.set("Please enter room's description")
        } else {
            roomDescriptionError.set(null)
        }

        return isValid
    }
}