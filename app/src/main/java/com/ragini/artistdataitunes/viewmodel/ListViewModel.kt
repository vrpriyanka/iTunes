package com.ragini.artistdataitunes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.ragini.artistdataitunes.di.AppModule
import com.ragini.artistdataitunes.di.CONTEXT_APP
import com.ragini.artistdataitunes.di.DaggerViewModelComponent
import com.ragini.artistdataitunes.di.TypeOfContext
import com.ragini.artistdataitunes.model.ITunes
import com.ragini.artistdataitunes.model.ITunesApiService
import com.ragini.artistdataitunes.util.Resource
import com.ragini.artistdataitunes.util.SharedPreferencesHelper
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class ListViewModel(application: Application) : AndroidViewModel(application) {

    constructor(application: Application, test: Boolean) : this(application) {
        injected = true
    }

    @Inject
    @TypeOfContext(CONTEXT_APP)
    lateinit var prefs: SharedPreferencesHelper
    private var injected = false
    private lateinit var iTunesService: ITunesApiService

    val iTunes by lazy { MutableLiveData<List<ITunes>>() }
    val loading by lazy { MutableLiveData<Boolean>() }

    private val disposable = CompositeDisposable()

    private fun inject() {
        if (!injected) {
            DaggerViewModelComponent.builder()
                .appModule(AppModule(getApplication()))
                .build()
                .inject(this)
        }
    }

    fun refresh() {
        inject()
        loading.value = true
        val key: String? = prefs.getApiKey()
        if (key.isNullOrEmpty()) {
            getListITunes(key.toString())
        } else {
            prefs.saveApiKey(key)
            getListITunes(key)
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    fun setApiService(iTunesApiService: ITunesApiService) {
        this.iTunesService = iTunesApiService
    }

    fun getListITunes(searchValue: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = iTunesService.getListITunes(searchValue).results))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}