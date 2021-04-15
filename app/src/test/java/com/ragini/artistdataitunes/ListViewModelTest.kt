package com.ragini.artistdataitunes

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.ragini.artistdataitunes.di.AppModule
import com.ragini.artistdataitunes.di.DaggerViewModelComponent
import com.ragini.artistdataitunes.model.ITunes
import com.ragini.artistdataitunes.model.ITunesApiService
import com.ragini.artistdataitunes.util.SharedPreferencesHelper
import com.ragini.artistdataitunes.util.Status.*
import com.ragini.artistdataitunes.viewmodel.ListViewModel
import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations


@Suppress("DEPRECATION")
class ListViewModelTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    lateinit var iTuneService: ITunesApiService

    @Mock
    lateinit var prefs: SharedPreferencesHelper

    private val application = mock(Application::class.java)!!
    private val listViewModel = ListViewModel(application, true)
    private val lifecycleOwner: LifecycleOwner = mock(LifecycleOwner::class.java)

    private val lifecycle = LifecycleRegistry(mock(LifecycleOwner::class.java))

    private var key = "Test key"

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        DaggerViewModelComponent.builder()
            .appModule(AppModule(application))
            .apiModule(ApiModuleTest(iTuneService))
            .prefsModule(PrefsModuleTest(prefs))
            .build()
            .inject(listViewModel)
        `when`(lifecycleOwner.lifecycle).thenReturn(lifecycle)
    }

    @Test
    fun getITunesSuccess() {
        runBlocking {
            `when`(prefs.getApiKey()).thenReturn(key)
            val iTune = ITunes("selena gomez", null, null, null, null, null)
            key = iTune.artistName.toString()
            prefs.saveApiKey(key)

            listViewModel.getListITunes(key).observe(lifecycleOwner, {
                it?.let { resource ->
                    if (resource.status == SUCCESS) {
                        Assert.assertEquals(1, listViewModel.iTunes.value?.size)
                        Assert.assertEquals(true, listViewModel.loading.value)
                    }
                }
            })
        }
    }

    @Test
    fun getITunesFailure() {
        runBlocking {
            `when`(prefs.getApiKey()).thenReturn(key)

            listViewModel.getListITunes(key).observe(lifecycleOwner, {
            it?.let { resource ->
                if (resource.status == ERROR) {
                    Assert.assertEquals(null, listViewModel.iTunes.value?.size)
                    Assert.assertEquals(false, listViewModel.loading.value)
                }
            }
        })
        }
    }

    @Before
    fun setupRxSchedulers() {
        val immediate = object : Scheduler() {
            override fun createWorker(): Worker {
                return ExecutorScheduler.ExecutorWorker({ it.run() }, true)
            }
        }

        RxJavaPlugins.setInitNewThreadSchedulerHandler { immediate }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { immediate }
    }
}