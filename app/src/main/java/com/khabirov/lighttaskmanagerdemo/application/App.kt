package com.niww.lighttaskmanager.application

import android.app.Application
import com.niww.lighttaskmanager.di.application
import com.niww.lighttaskmanager.di.viewModelDependency
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(listOf(application, viewModelDependency))
        }
    }
}