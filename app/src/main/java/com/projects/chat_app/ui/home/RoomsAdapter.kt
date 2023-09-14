package com.projects.chat_app.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.projects.chat_app.R
import com.projects.domain.models.Room
import com.projects.chat_app.databinding.ItemRoomBinding

class RoomsAdapter(private var itemsList:List<Room>?=null) : RecyclerView.Adapter<RoomsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewBinding=DataBindingUtil.inflate<ItemRoomBinding>(LayoutInflater.from(parent.context), R.layout.item_room,parent,false)
        return ViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item= itemsList!![position]
        holder.bind(item)
        holder.itemView.setOnClickListener{
            itemClickListener?.onItemClick(position,item)
        }
    }

    override fun getItemCount(): Int = itemsList?.size ?: 0


    fun changeData(newList:List<Room>?) {
        itemsList=newList
        notifyDataSetChanged()
    }

    var itemClickListener:ItemClick?=null

    interface ItemClick
    {
        fun onItemClick(position:Int,item: Room)
    }

    class ViewHolder(private val viewBinding:ItemRoomBinding) : RecyclerView.ViewHolder(viewBinding.root)
    {
        fun bind(item: Room)
        {
            viewBinding.item=item
            viewBinding.invalidateAll()
        }
    }
}