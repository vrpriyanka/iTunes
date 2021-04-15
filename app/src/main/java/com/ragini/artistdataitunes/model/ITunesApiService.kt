package com.ragini.artistdataitunes.model

import com.ragini.artistdataitunes.di.DaggerApiComponent

class ITunesApiService(private val api: ITunesApi) {

    init {
        DaggerApiComponent.create().inject(this)
    }

    suspend fun getListITunes(searchParam: String) = api.getListITunes(searchParam)
}