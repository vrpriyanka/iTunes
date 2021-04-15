package com.ragini.artistdataitunes

import com.ragini.artistdataitunes.di.ApiModule
import com.ragini.artistdataitunes.model.ITunesApiService

class ApiModuleTest(private val mockService: ITunesApiService): ApiModule() {
    override fun provideITunesApiService(): ITunesApiService {
        return mockService
    }
}