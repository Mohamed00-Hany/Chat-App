package com.projects.chat_app.ui.chat

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.projects.chat_app.ui.Constants
import com.projects.chat_app.R
import com.projects.chat_app.database.models.Room
import com.projects.chat_app.databinding.ActivityChatRoomBinding
import com.projects.chat_app.ui.base.BaseActivity
import kotlinx.coroutines.launch


class ChatRoomActivity : BaseActivity<ActivityChatRoomBinding, ChatRoomViewModel>(), Navigator {
    private var activeRoom: Room? = null
    lateinit var messagesRecyclerView: RecyclerView
    lateinit var messagesAdapter: MessagesAdapter
    lateinit var layoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeRoom()
        viewModel.navigator = this
        viewBinding.vm = viewModel
        viewBinding.activityToolBar.vm = viewModel
        initializeMessagesRecycler()
        subscribeToStateFlow()
        lifecycleScope.launch {
            viewModel.subscribeToMessagesChange()
        }
    }

    override fun onStop() {
        super.onStop()
        viewBinding.contentChatRoom.message.clearFocus()
    }

    private fun subscribeToStateFlow() {

        lifecycleScope.launch {
            viewModel.errorStateFlow.collect {error ->
                if (error!=null)
                {
                    showMessage(
                        error,
                        posActionTitle = "Try again",
                        posAction = {
                            lifecycleScope.launch {
                                messagesAdapter.messages?.clear()
                                if (messagesAdapter.messages == null) {
                                    messagesAdapter.messages = mutableListOf()
                                }
                                viewModel.subscribeToMessagesChange()
                            }
                        })
                }
            }
        }

        lifecycleScope.launch {
            viewModel.messagesStateFlow.collect { message ->
                if (message != null) {
                    messagesAdapter.addMessage(message)
                    messagesRecyclerView.scrollToPosition(messagesAdapter.itemCount - 1)
                }
            }
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

    override fun backToPreviousScreen() {
        super.backToPreviousScreen()
        finish()
    }
}