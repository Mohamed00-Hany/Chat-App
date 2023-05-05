package com.projects.chat_app.ui.addRoom

import androidx.databinding.ObservableField
import com.projects.chat_app.database.models.Room
import com.projects.chat_app.ui.UserProvider
import com.projects.chat_app.ui.base.BaseViewModel

class AddRoomViewModel:BaseViewModel<Navigator>() {
    val roomName=ObservableField<String>()
    val roomDescription=ObservableField<String>()
    val roomNameError=ObservableField<String>()
    val roomDescriptionError=ObservableField<String>()
    var selectedRoomCategory:RoomCategory=RoomCategory.getListRoomCategories()[0]

    fun createRoom()
    {
        if(!validInputs())
            return
        val room=Room(
            name = roomName.get(),
            description = roomDescription.get(),
            roomCategoryId = selectedRoomCategory.id,
            createdBy = UserProvider.user?.id,
            id = null
        )
        navigator?.showLoading("Creating...")
        dataBase.insertRoom(room).addOnCompleteListener {task->
            navigator?.hideLoading()
            if(task.isSuccessful)
            {
                navigator?.showMessage("Room created successfully","Ok",
                    posAction = {
                    navigator?.goToHome()
                })
            }
            else
            {
                navigator?.showMessage(task.exception?.localizedMessage!!)
            }
        }

    }

    fun validInputs():Boolean
    {
        var isValid=true

        if(roomName.get().isNullOrBlank())
        {
            isValid=false
            roomNameError.set("Please enter room's name")
        }
        else
        {
            roomNameError.set(null)
        }

        if(roomDescription.get().isNullOrBlank())
        {
            isValid=false
            roomDescriptionError.set("Please enter room's description")
        }
        else
        {
            roomDescriptionError.set(null)
        }

        return isValid
    }
}