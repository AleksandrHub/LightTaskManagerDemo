package com.niww.lighttaskmanager.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.niww.lighttaskmanager.R
import com.niww.lighttaskmanager.model.ChatMessage
import kotlinx.android.synthetic.main.own_chat_message_item.view.*

class MessageRVAdapter(
    var messages: List<ChatMessage>
) : RecyclerView.Adapter<MessageRVAdapter.ViewHolder>() {

    companion object {
        const val OWN_MESSAGE = 0
        const val FOREIGN_MESSAGE = 1
    }

    private val auth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == FOREIGN_MESSAGE) {
            ViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.foreign_chat_message_item, parent, false)
            )
        } else {
            ViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.own_chat_message_item, parent, false)
            )
        }
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(messages[position])
    }

    override fun getItemViewType(position: Int): Int {
        val message = messages[position]
        return if (message.authorUid == auth.currentUser!!.uid) OWN_MESSAGE else FOREIGN_MESSAGE
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val tvText = view.tv_message_item_text
        private val tvTime = view.tv_message_item_time

        fun bind(message: ChatMessage) {
            tvText.text = message.text
            tvTime.text = message.time
        }
    }
}