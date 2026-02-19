package dev.me.mysmov.domain.model

import com.google.gson.annotations.SerializedName

data class MediaItem(
    val id: Int,
    val title: String,
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("vote_average")
    val rating: Double = 0.0,
    @SerializedName("backdrop_path")
    val backdropPath: String = "",
    @SerializedName("release_date")
    val releaseDate: String = "",
)
