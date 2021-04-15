package com.ragini.artistdataitunes.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class ITunes(
    @SerializedName("artistName")
    @Expose
    var artistName: String? = null,

    @SerializedName("trackName")
    @Expose
    var trackName: String? = null,

    @SerializedName("trackPrice")
    @Expose
    var trackPrice: Double? = 0.0,

    @SerializedName("releaseDate")
    @Expose
    var releaseDate: String? = null,

    @SerializedName("primaryGenreName")
    @Expose
    var primaryGenreName: String? = null,

    @SerializedName("artworkUrl100")
    @Expose
    var artworkUrl100: String? = null,
) : Parcelable

class SearchResultModel
{
    @SerializedName("results")
    @Expose
    var results: List<ITunes>? = null
}