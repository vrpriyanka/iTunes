package com.ragini.artistdataitunes.di

import com.ragini.artistdataitunes.model.ITunesApiService
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {
    fun inject(service: ITunesApiService)
}