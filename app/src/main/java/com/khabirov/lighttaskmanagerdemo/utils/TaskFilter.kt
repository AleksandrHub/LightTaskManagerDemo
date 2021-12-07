package com.niww.lighttaskmanager.utils

import com.niww.lighttaskmanager.model.datasource.Task
import java.util.*


data class TaskFilter(
    var filterTaskName: String ="",
    var filterDueDate: String ="",
    var filterImplementer: String="",
    var filterEmployer: String=""
){

    fun setTaskFilter(filterTaskName: String, filterDueDate: String, filterImplementer: String, filterEmployer: String){
        this.filterTaskName = filterTaskName
        this.filterDueDate = filterDueDate
        this.filterEmployer = filterEmployer
        this.filterImplementer = filterImplementer

    }

    fun getTaskFilter(): (Task) -> Boolean{

        return { task -> (filterTaskName.toLowerCase(Locale.getDefault()) in task.name.toLowerCase(
            Locale.getDefault()
        ))
                &&(filterDueDate in task.timing)&&(filterEmployer.toLowerCase(Locale.getDefault()) in task.employer.toLowerCase(
            Locale.getDefault()
        ))
                &&(filterImplementer.toLowerCase(Locale.getDefault()) in task.performer.toLowerCase(
            Locale.getDefault()
        ))
        }
    }

    fun clear(){
        filterTaskName=""
        filterDueDate=""
        filterEmployer=""
        filterImplementer=""
    }


}
