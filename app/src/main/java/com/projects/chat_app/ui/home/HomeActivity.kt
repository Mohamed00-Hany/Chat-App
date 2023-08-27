package com.projects.chat_app.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.projects.chat_app.ui.Constants
import com.projects.chat_app.R
import com.projects.chat_app.database.models.Room
import com.projects.chat_app.databinding.ActivityHomeBinding
import com.projects.chat_app.ui.addRoom.AddRoomActivity
import com.projects.chat_app.ui.base.BaseActivity
import com.projects.chat_app.ui.chat.ChatRoomActivity
import kotlinx.coroutines.launch

class HomeActivity:BaseActivity<ActivityHomeBinding,HomeViewModel>(),Navigator {
    lateinit var roomsRecycler:RecyclerView
    lateinit var roomsAdapter:RoomsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.navigator=this
        viewBinding.vm=viewModel
        viewBinding.activityToolBar.vm=viewModel
        initRoomsRecycler()
        subscribeToLiveData()
    }

    override fun onStart() {
        super.onStart()
        viewModel.loadRooms()
    }

    private fun subscribeToLiveData() {
        viewModel.roomsLiveData.observe(this){
            roomsAdapter.changeData(it)
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
        return ViewModelProvider(this)[HomeViewModel::class.java]
    }

    override fun openAddRoom() {
        val intent=Intent(this@HomeActivity,AddRoomActivity::class.java)
        startActivity(intent)
    }

    fun goToChatRoom(room:Room) {
        val intent=Intent(this@HomeActivity,ChatRoomActivity::class.java)
        intent.putExtra(Constants.EXTRA_ROOM,room)
        startActivity(intent)
    }
}