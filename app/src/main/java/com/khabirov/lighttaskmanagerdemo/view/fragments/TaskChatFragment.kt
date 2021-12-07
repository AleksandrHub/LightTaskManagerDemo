package com.niww.lighttaskmanager.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.niww.lighttaskmanager.R
import com.niww.lighttaskmanager.model.ChatMessage
import com.niww.lighttaskmanager.view.adapters.MessageRVAdapter
import kotlinx.android.synthetic.main.fragment_task_chat.view.*

class TaskChatFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var chatAdapter: MessageRVAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_task_chat, container, false)
        init(view)
        return view
    }

    private fun init(view: View) {
        recyclerView = view.chat_recycler_view
        val messages = mutableListOf(
            ChatMessage("Когда начнёшь?","12:00","08.08.2020","fasfasgavf"),
            ChatMessage("Сегодня","12:06","06.08.2020","asfasfasfasf")
        )
        chatAdapter = MessageRVAdapter(messages)
        recyclerView.adapter = chatAdapter
    }

}