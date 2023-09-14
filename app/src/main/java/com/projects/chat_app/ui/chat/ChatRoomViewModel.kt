package com.projects.chat_app.ui.chat

import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import com.projects.domain.models.Message
import com.projects.domain.models.Room
import com.projects.domain.repositoriesContract.TaskStates
import com.projects.chat_app.ui.UserProvider
import com.projects.chat_app.ui.base.BaseViewModel
import com.projects.domain.useCases.ReceiveMessages
import com.projects.domain.useCases.SendMessages
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatRoomViewModel @Inject constructor(private val receiveMessages: ReceiveMessages
,private val sendMessages: SendMessages) : BaseViewModel<Navigator>() {
    var activeRoom: Room? = null
    val messageContent = ObservableField<String>()
    var messagesStateFlow = MutableStateFlow<Message?>(null)
    var errorStateFlow = MutableStateFlow<String?>(null)

    fun sendMessage() {
        if (messageContent.get().isNullOrBlank())
            return

        val message = Message(
            content = messageContent.get()?.trim(),
            roomId = activeRoom?.id,
            senderId = UserProvider.user?.id,
            senderName = UserProvider.user?.userName,
            dateTime = Timestamp.now()
        )

        messageContent.set("")

        viewModelScope.launch {
            sendMessages(message).collect {
                if (it is TaskStates.TaskFailed) {
                    messageContent.set(message.content)
                    navigator?.showMessage(
                        it.error ?: "Error sending your message",
                        posActionTitle = "Try again",
                        posAction = { sendMessage() },
                        negActionTitle = "Ok"
                    )
                }
            }
        }
    }

    suspend fun subscribeToMessagesChange() {
        receiveMessages(activeRoom?.id ?: "").collect {
            when (it) {
                is TaskStates.TaskSucceed<*> -> {
                    val message = it.data as Message
                    messagesStateFlow.value = message
                }
                else -> {
                    val taskFailed = it as TaskStates.TaskFailed
                    errorStateFlow.value = taskFailed.error
                }
            }
        }
    }
}