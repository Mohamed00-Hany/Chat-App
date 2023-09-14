package com.projects.chat_app.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.projects.chat_app.ui.Constants
import com.projects.chat_app.R
import com.projects.domain.models.Room
import com.projects.chat_app.databinding.ActivityHomeBinding
import com.projects.chat_app.ui.addRoom.AddRoomActivity
import com.projects.chat_app.ui.base.BaseActivity
import com.projects.chat_app.ui.chat.ChatRoomActivity
import com.projects.chat_app.ui.chat.ChatRoomViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeActivity:BaseActivity<ActivityHomeBinding,HomeViewModel>(),Navigator {
    lateinit var roomsRecycler:RecyclerView
    lateinit var roomsAdapter:RoomsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.navigator=this
        viewBinding.vm=viewModel
        viewBinding.activityToolBar.vm=viewModel
        initRoomsRecycler()
        lifecycleScope.launch {
            subscribeToStateFlow()
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.loadRooms()
    }

    private suspend fun subscribeToStateFlow() {
        viewModel.roomsStateFlow.collect{rooms->
            if (rooms!=null)
            {
                roomsAdapter.changeData(rooms)
            }
        }
    }

    private fun initRoomsRecycler() {
        roomsRecycler=viewBinding.contentHome.roomsRecycler
        roomsAdapter= RoomsAdapter()
        roomsAdapter.itemClickListener=object :RoomsAdapter.ItemClick{
            override fun onItemClick(position: Int, item: Room) {
                goToChatRoom(item)
            }
        }
        roomsRecycler.adapter=roomsAdapter
    }

    override fun getLayoutId(): Int =R.layout.activity_home

    override fun generateViewModel(): HomeViewModel {
        val viewModel: HomeViewModel by viewModels()
        return viewModel
    }

    override fun openAddRoom() {
        val intent=Intent(this@HomeActivity,AddRoomActivity::class.java)
        startActivity(intent)
    }

    fun goToChatRoom(room: Room) {
        val intent=Intent(this@HomeActivity,ChatRoomActivity::class.java)
        intent.putExtra(Constants.EXTRA_ROOM,room)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.roomsStateFlow.value=null
    }
}