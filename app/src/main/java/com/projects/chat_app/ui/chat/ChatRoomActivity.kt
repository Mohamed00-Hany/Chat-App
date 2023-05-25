package com.projects.chat_app.ui.chat

import android.os.Build
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ListenerRegistration
import com.projects.chat_app.Constants
import com.projects.chat_app.R
import com.projects.chat_app.database.models.Message
import com.projects.chat_app.database.models.Room
import com.projects.chat_app.databinding.ActivityChatRoomBinding
import com.projects.chat_app.ui.base.BaseActivity


class ChatRoomActivity : BaseActivity<ActivityChatRoomBinding,ChatRoomViewModel>(),Navigator {
    private var activeRoom: Room?=null
    lateinit var messagesRecyclerView: RecyclerView
    lateinit var messagesAdapter: MessagesAdapter
    lateinit var layoutManager: LinearLayoutManager
    var listener:ListenerRegistration?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeRoom()
        viewModel.navigator=this
        viewBinding.vm=viewModel
        viewBinding.activityToolBar.vm=viewModel
        initializeMessagesRecycler()
    }

    override fun onStart() {
        super.onStart()
        subscribeToMessagesChange()
    }

    override fun onStop() {
        super.onStop()
        viewBinding.contentChatRoom.message.clearFocus()
    }

    fun subscribeToMessagesChange()
    {
        if(listener==null)
        {
            listener=viewModel.dataBase.getRoomMessages(activeRoom?.id?:"").addSnapshotListener{value,error->
                if(error!=null)
                {
                    showMessage(error.localizedMessage, posActionTitle = "Try again",posAction = {subscribeToMessagesChange()})
                }
                else
                {
                    value?.documentChanges?.forEach {
                        val message=it.document.toObject(Message::class.java)
                        messagesAdapter.addMessage(message)
                        messagesRecyclerView.scrollToPosition(messagesAdapter.itemCount-1)
                    }
                }
            }
        }
    }

    private fun initializeMessagesRecycler() {
        messagesRecyclerView=viewBinding.contentChatRoom.messagesRecycler
        messagesAdapter= MessagesAdapter(mutableListOf<Message>())
        layoutManager=LinearLayoutManager(this)
        messagesRecyclerView.adapter=messagesAdapter
        messagesRecyclerView.layoutManager=layoutManager
    }

    private fun initializeRoom()
    {
        activeRoom = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(Constants.EXTRA_ROOM,Room::class.java)
        } else{
            intent.getParcelableExtra(Constants.EXTRA_ROOM)
        }
        viewModel.activeRoom=activeRoom
    }

    override fun getLayoutId(): Int =R.layout.activity_chat_room

    override fun generateViewModel(): ChatRoomViewModel {
        return ViewModelProvider(this)[ChatRoomViewModel::class.java]
    }

    override fun backToPreviousScreen() {
        super.backToPreviousScreen()
        finish()
    }
}