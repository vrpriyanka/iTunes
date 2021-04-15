package com.ragini.artistdataitunes.model

import retrofit2.http.*

interface ITunesApi {
    // Get list of iTunes from api
    @GET("search")
    suspend fun getListITunes(@Query("term") searchParam: String): SearchResultModel
}