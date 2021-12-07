package com.niww.lighttaskmanager.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.niww.lighttaskmanager.model.datasource.Task
import io.reactivex.Observable

class FirebaseRepository(
    private val database: FirebaseDatabase,
    private val auth: FirebaseAuth
) {

    companion object {
        const val ALL_TASKS = "all_tasks"
        const val MEMBERS = "members"
        const val TASK = "task"
        const val CUSTOMER = "customer"
        const val PERFORMER = "performer"
    }

    private val mainReference by lazy {
        database.getReference(ALL_TASKS)
    }

    fun getData(): Observable<List<Task>> {
        return Observable.create { emitter ->
            mainReference.addValueEventListener(object : ValueEventListener {

                override fun onCancelled(error: DatabaseError) {

                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    val tasksSnapshot = snapshot.children
                    val taskList = mutableListOf<Task>()
                        for (nextTaskSnapshot in tasksSnapshot) {
                            val nextTask = nextTaskSnapshot.getValue(Task::class.java)
                            nextTask?.let {
                                taskList.add(it)
                            }
                        }
                    emitter.onNext(taskList)
                }
            })
        }
    }

    fun addNewTask(task: Task) {
        mainReference.push().setValue(task)
    }
}