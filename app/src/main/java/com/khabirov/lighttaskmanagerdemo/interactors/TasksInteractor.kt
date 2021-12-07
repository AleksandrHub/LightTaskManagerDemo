package com.niww.lighttaskmanager.interactors

import com.niww.lighttaskmanager.model.AppState
import com.niww.lighttaskmanager.repository.FirebaseRepository
import com.niww.lighttaskmanager.utils.TaskFilter

class TasksInteractor(
    private val repo: FirebaseRepository,
    val taskFilter: TaskFilter
) {

    companion object {
        const val STATE_TODO = 0
        const val STATE_IN_PROGRESS = 1
        const val STATE_DONE = 2
    }

    private fun getFilteredData() =
        repo.getData().map { it.filter(taskFilter.getTaskFilter()) }


    fun getTasks(state: Int) = when (state) {
        STATE_TODO -> getToDoTasks()
        STATE_IN_PROGRESS -> getInProgressTasks()
        STATE_DONE -> getDoneTasks()
        else -> null
    }

    private fun getToDoTasks() = getFilteredData().map { tasksList ->
        tasksList.filter { it.state == STATE_TODO }
    }
        .map { AppState.Success(it) }

    private fun getInProgressTasks() = getFilteredData().map { tasksList ->
        tasksList.filter { it.state == STATE_IN_PROGRESS }
    }
        .map { AppState.Success(it) }

    private fun getDoneTasks() = getFilteredData().map { tasksList ->
        tasksList.filter { it.state == STATE_DONE }
    }
        .map { AppState.Success(it) }

}