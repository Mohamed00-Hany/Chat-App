package com.projects.chat_app.ui.addRoom

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.projects.chat_app.R
import com.projects.chat_app.databinding.ActivityAddRoomBinding
import com.projects.chat_app.ui.base.BaseActivity
import com.projects.chat_app.ui.chat.ChatRoomViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddRoomActivity : BaseActivity<ActivityAddRoomBinding,AddRoomViewModel>(),Navigator {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.navigator=this
        viewBinding.vm=viewModel
        viewBinding.activityToolBar.vm=viewModel
        initializeSpinner()
    }

    private fun initializeSpinner() {
        val spinner=viewBinding.contentAddRoom.categorySpinner
        val adapter=CategoriesAdapter(RoomCategory.getListRoomCategories())
        spinner.adapter=adapter
        spinner.onItemSelectedListener=object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewModel.selectedRoomCategory=adapter.getItem(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }
    }

    override fun getLayoutId(): Int =R.layout.activity_add_room

    override fun generateViewModel(): AddRoomViewModel {
        val viewModel: AddRoomViewModel by viewModels()
        return viewModel
    }

    override fun goToHome()
    {
        finish()
    }

    override fun backToPreviousScreen() {
        goToHome()
    }

}