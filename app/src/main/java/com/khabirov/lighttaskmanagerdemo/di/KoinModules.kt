package com.niww.lighttaskmanager.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.niww.lighttaskmanager.interactors.TasksInteractor
import com.niww.lighttaskmanager.repository.FirebaseRepository
import com.niww.lighttaskmanager.rx.SchedulerProvider
import com.niww.lighttaskmanager.utils.TaskFilter
import com.niww.lighttaskmanager.view_model.ArchiveTasksViewModel
import com.niww.lighttaskmanager.view_model.ContainerTasksViewModel
import com.niww.lighttaskmanager.view_model.PageViewModel
import com.niww.lighttaskmanager.view_model.TaskFilterViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val application = module {

    single {
        FirebaseRepository(auth = get(), database = get())
    }

    factory {
        TasksInteractor(repo = get(), taskFilter = get())
    }

    single {
        SchedulerProvider()
    }

    single {
        TaskFilter()
    }

    single {
        FirebaseAuth.getInstance()
    }

    single {
        FirebaseDatabase.getInstance()
    }

}

val viewModelDependency = module {

    viewModel {
        ContainerTasksViewModel(androidApplication())
    }

    viewModel {
        PageViewModel(androidApplication())
    }

    viewModel {
        ArchiveTasksViewModel(androidApplication())
    }

    viewModel {
        TaskFilterViewModel(androidApplication())
    }
}

