package com.projects.chat_app.ui.addRoom

import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.projects.chat_app.database.models.Room
import com.projects.chat_app.repositories.rooms.RoomsDataSourceImpl
import com.projects.chat_app.repositories.rooms.RoomsRepositoryImpl
import com.projects.chat_app.repositoriesContract.rooms.OnRoomTaskCompleted
import com.projects.chat_app.repositoriesContract.rooms.RoomsDataSource
import com.projects.chat_app.repositoriesContract.rooms.RoomsRepository
import com.projects.chat_app.ui.UserProvider
import com.projects.chat_app.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class AddRoomViewModel : BaseViewModel<Navigator>() {
    val roomName = ObservableField<String>()
    val roomDescription = ObservableField<String>()
    val roomNameError = ObservableField<String>()
    val roomDescriptionError = ObservableField<String>()
    var selectedRoomCategory: RoomCategory = RoomCategory.getListRoomCategories()[0]
    private val roomsDataSource: RoomsDataSource = RoomsDataSourceImpl(dataBase)
    private val roomsRepository: RoomsRepository = RoomsRepositoryImpl(roomsDataSource)

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
            roomsRepository.insertRoom(room, object : OnRoomTaskCompleted {
                override fun onComplete() {
                    navigator?.hideLoading()
                }

                override fun onSuccess(room: Room?) {
                    navigator?.showMessage("Room created successfully", "Ok",
                        posAction = {
                            navigator?.goToHome()
                        })
                }

                override fun onFail(error: String?) {
                    navigator?.showMessage(error ?: "Error, can't add room")
                }
            })
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