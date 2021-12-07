package com.niww.lighttaskmanager.view_model

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.niww.lighttaskmanager.interactors.TasksInteractor
import com.niww.lighttaskmanager.model.AppState
import com.niww.lighttaskmanager.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import org.koin.core.inject

class PageViewModel(appContext: Application) : BaseViewModel(appContext) {

    private val interactor: TasksInteractor by inject()
    private val schedulerProvider: SchedulerProvider by inject()

    var currentIndex : Int? = null

    private val compositeDisposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    val toDoTasksLiveData: MutableLiveData<AppState> by lazy {
        MutableLiveData<AppState>()
    }

    val inProgressTasksLiveData: MutableLiveData<AppState> by lazy {
        MutableLiveData<AppState>()
    }

    val doneTasksLiveData: MutableLiveData<AppState> by lazy {
        MutableLiveData<AppState>()
    }

    fun getData(index: Int) {
        currentIndex = index
        interactor.getTasks(index)?.let {
            compositeDisposable.add(
                it
                    .subscribeOn(schedulerProvider.io())
                    .observeOn(schedulerProvider.ui())
                    .doOnSubscribe(doOnSubscribe(index))
                    .subscribeWith(getObserver(index))
            )
        }
    }

    private fun getObserver(index:Int): DisposableObserver<AppState> {
        return object : DisposableObserver<AppState>() {

            override fun onNext(data:AppState) {
                when(index){
                    0 -> toDoTasksLiveData.value= data
                    1 -> inProgressTasksLiveData.value= data
                    2 -> doneTasksLiveData.value= data
                }
            }

            override fun onError(e: Throwable) {

            }

            override fun onComplete() {
            }
        }
    }

    private fun doOnSubscribe(index:Int): (Disposable) -> Unit =
        {
            when(index){
                0 -> toDoTasksLiveData.value = AppState.Loading(null)
                1 -> inProgressTasksLiveData.value = AppState.Loading(null)
                2 -> doneTasksLiveData.value = AppState.Loading(null)
            }
        }

    fun getLiveData(index:Int) =
        when(index){
            0 -> toDoTasksLiveData
            1 -> inProgressTasksLiveData
            2 -> doneTasksLiveData
            else -> throw IllegalArgumentException("invalid index")
        }
}