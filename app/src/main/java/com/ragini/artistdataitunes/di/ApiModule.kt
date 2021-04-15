package com.ragini.artistdataitunes.di

import com.ragini.artistdataitunes.model.ITunesApi
import com.ragini.artistdataitunes.model.ITunesApiService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
open class ApiModule {
    private var client = OkHttpClient()

    @Provides
    fun provideITunesApi(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://itunes.apple.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()
    }

    val api: ITunesApi = provideITunesApi().create(ITunesApi::class.java)
    open fun provideITunesApiService(): ITunesApiService {
        return ITunesApiService(api)
    }
}