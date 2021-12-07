package com.niww.lighttaskmanager.view_model

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.niww.lighttaskmanager.model.datasource.Task
import com.niww.lighttaskmanager.repository.FirebaseRepository
import org.koin.core.inject

class ArchiveTasksViewModel(appContext: Application) : BaseViewModel(appContext) {

    private val firebaseRepository: FirebaseRepository by inject()

    val archiveTasksLiveData: MutableLiveData<List<Task>> by lazy {
        MutableLiveData<List<Task>>()
    }

    fun updateTasksLiveData() {

    }
}