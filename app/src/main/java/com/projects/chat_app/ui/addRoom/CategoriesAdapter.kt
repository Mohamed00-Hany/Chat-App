package com.projects.chat_app.ui.addRoom

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.databinding.DataBindingUtil
import com.projects.chat_app.R
import com.projects.chat_app.databinding.ItemRoomCategoryBinding

class CategoriesAdapter(private val listItems: List<RoomCategory>) :BaseAdapter() {
    override fun getCount(): Int {
        return listItems.size
    }

    override fun getItem(position: Int): RoomCategory {
        return listItems[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var currentItemView=convertView
        var viewHolder:ViewHolder
        if(currentItemView==null)
        {
            val viewBinding:ItemRoomCategoryBinding=DataBindingUtil.inflate(LayoutInflater.from(parent?.context),
                R.layout.item_room_category,parent,false)
            viewHolder= ViewHolder(viewBinding)
            currentItemView=viewHolder.viewBinding.root
            currentItemView.tag=viewHolder
        }
        else
        {
            viewHolder=currentItemView.tag as ViewHolder
        }

        viewHolder.bind(getItem(position))

        return currentItemView
    }

    class ViewHolder(val viewBinding:ItemRoomCategoryBinding)
    {
        fun bind(item:RoomCategory)
        {
            viewBinding.item=item
            viewBinding.invalidateAll()
        }
    }
}