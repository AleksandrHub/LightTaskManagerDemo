package com.niww.lighttaskmanager.model

import com.niww.lighttaskmanager.model.datasource.Task

sealed class AppState {

    data class Success(val data: List<Task>?) : AppState()
    data class Error(val error: Throwable) : AppState()
    data class Loading(val progress: Int?) : AppState()
}
