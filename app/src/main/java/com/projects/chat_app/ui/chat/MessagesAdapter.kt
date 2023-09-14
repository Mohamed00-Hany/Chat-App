package com.projects.chat_app.ui.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.projects.chat_app.R
import com.projects.domain.models.Message
import com.projects.chat_app.databinding.ItemReceivedMessageBinding
import com.projects.chat_app.databinding.ItemSentMessageBinding
import com.projects.chat_app.ui.UserProvider

enum class MessageType(val value: Int) {
    Sent(0), Received(1)
}

class MessagesAdapter(var messages: MutableList<Message?>?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        super.getItemViewType(position)

        val message = messages?.get(position)

        if (message?.senderId == UserProvider.user?.id) {
            return MessageType.Sent.value
        }
        return MessageType.Received.value
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return if (viewType == MessageType.Sent.value) {
            val messageViewBinding = DataBindingUtil.inflate<ItemSentMessageBinding>(
                LayoutInflater.from(parent.context),
                R.layout.item_sent_message,
                parent,
                false
            )
            SentViewHolder(messageViewBinding)
        } else {
            val messageViewBinding = DataBindingUtil.inflate<ItemReceivedMessageBinding>(
                LayoutInflater.from(parent.context),
                R.layout.item_received_message,
                parent,
                false
            )
            ReceivedViewHolder(messageViewBinding)
        }
    }

    override fun getItemCount(): Int = messages?.size ?: 0

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = messages?.get(position)
        if (holder is SentViewHolder) {
            holder.bind(message)
        } else if (holder is ReceivedViewHolder) {
            holder.bind(message)
        }
    }

    fun addMessage(newMessage: Message?) {
        messages?.add(newMessage)
        notifyItemInserted(itemCount - 1)
    }

    class SentViewHolder(private val viewBinding: ItemSentMessageBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(message: Message?) {
            viewBinding.message = message
            viewBinding.invalidateAll()
        }
    }

    class ReceivedViewHolder(private val viewBinding: ItemReceivedMessageBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(message: Message?) {
            viewBinding.message = message
            viewBinding.invalidateAll()
        }
    }
}