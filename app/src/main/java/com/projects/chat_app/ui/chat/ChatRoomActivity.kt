package com.projects.chat_app.ui.chat

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ListenerRegistration
import com.projects.chat_app.ui.Constants
import com.projects.chat_app.R
import com.projects.chat_app.database.models.Message
import com.projects.chat_app.database.models.Room
import com.projects.chat_app.databinding.ActivityChatRoomBinding
import com.projects.chat_app.repositoriesContract.messages.OnReceiveMessagesTaskCompleted
import com.projects.chat_app.ui.base.BaseActivity
import kotlinx.coroutines.launch


class ChatRoomActivity : BaseActivity<ActivityChatRoomBinding, ChatRoomViewModel>(), Navigator {
    private var activeRoom: Room? = null
    lateinit var messagesRecyclerView: RecyclerView
    lateinit var messagesAdapter: MessagesAdapter
    lateinit var layoutManager: LinearLayoutManager
    var listener: ListenerRegistration? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeRoom()
        viewModel.navigator = this
        viewBinding.vm = viewModel
        viewBinding.activityToolBar.vm = viewModel
        initializeMessagesRecycler()
    }

    override fun onStart() {
        super.onStart()
        lifecycleScope.launch {
            subscribeToMessagesChange()
        }
    }

    override fun onStop() {
        super.onStop()
        viewBinding.contentChatRoom.message.clearFocus()
    }

    private suspend fun subscribeToMessagesChange() {
        if (listener == null) {
            listener = viewModel.messagesRepository.receiveMessages(activeRoom?.id ?: "",
                object : OnReceiveMessagesTaskCompleted {
                    override fun onSuccess(message: Message?) {
                        messagesAdapter.addMessage(message)
                        messagesRecyclerView.scrollToPosition(messagesAdapter.itemCount - 1)
                    }

                    override fun onFail(error: String?) {
                        showMessage(
                            error ?: "Error, can't get messages",
                            posActionTitle = "Try again",
                            posAction = {
                                lifecycleScope.launch {
                                    messagesAdapter.messages?.clear()
                                    if (messagesAdapter.messages == null) {
                                        messagesAdapter.messages = mutableListOf()
                                    }
                                    listener?.remove()
                                    listener = null
                                    subscribeToMessagesChange()
                                }
                            })
                    }
                })
        }
    }

    private fun initializeMessagesRecycler() {
        messagesRecyclerView = viewBinding.contentChatRoom.messagesRecycler
        messagesAdapter = MessagesAdapter(mutableListOf())
        layoutManager = LinearLayoutManager(this)
        layoutManager.stackFromEnd = true
        messagesRecyclerView.layoutManager = layoutManager
        messagesRecyclerView.adapter = messagesAdapter
    }

    private fun initializeRoom() {
        activeRoom = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(Constants.EXTRA_ROOM, Room::class.java)
        } else {
            intent.getParcelableExtra(Constants.EXTRA_ROOM)
        }
        viewModel.activeRoom = activeRoom
    }

    override fun getLayoutId(): Int = R.layout.activity_chat_room

    override fun generateViewModel(): ChatRoomViewModel {
        return ViewModelProvider(this)[ChatRoomViewModel::class.java]
    }

    override fun onDestroy() {
        super.onDestroy()
        listener?.remove()
        listener = null
    }

    override fun backToPreviousScreen() {
        super.backToPreviousScreen()
        finish()
    }
}