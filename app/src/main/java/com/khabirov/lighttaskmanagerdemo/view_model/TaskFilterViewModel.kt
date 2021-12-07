package com.niww.lighttaskmanager.view_model

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.niww.lighttaskmanager.utils.TaskFilter
import org.koin.core.inject


class TaskFilterViewModel(appContext: Application) : BaseViewModel(appContext) {

    val taskFilter: TaskFilter by inject()

    val taskFilterLiveData: MutableLiveData<TaskFilter> by lazy {
        MutableLiveData<TaskFilter>()
    }

    fun setTaskFilter(_name: String?, _time: String?, _implementer: String?, _employer: String?){
        val name: String
        val time: String
        val implementer: String
        val employer: String
        if(_name.isNullOrBlank()) name="" else name=_name
        if(_time.isNullOrBlank()) time="" else time=_time
        if(_employer.isNullOrBlank()) employer="" else employer=_employer
        if(_implementer.isNullOrBlank()) implementer="" else implementer=_implementer

        taskFilter.setTaskFilter(name, time, implementer, employer)
    }

    fun clearTaskFilter(){
        taskFilter.clear()
        taskFilterLiveData.value=taskFilter
    }

    fun setDueDate(){
        taskFilterLiveData.value=taskFilter
    }


    fun setLiveData(){
        taskFilterLiveData.value=taskFilter
    }



}