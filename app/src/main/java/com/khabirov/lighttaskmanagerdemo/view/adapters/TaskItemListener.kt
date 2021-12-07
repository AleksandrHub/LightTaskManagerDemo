package com.niww.lighttaskmanager.view.adapters

import com.niww.lighttaskmanager.model.datasource.Task

interface TaskItemListener {
    fun onTaskClick(task: Task)
    fun onChatClick(task: Task)
}