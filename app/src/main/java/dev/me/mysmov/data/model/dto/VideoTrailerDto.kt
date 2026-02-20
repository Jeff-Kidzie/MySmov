package dev.me.mysmov.data.model.dto

import com.google.gson.annotations.SerializedName
import dev.me.mysmov.domain.model.ui.VideoTrailer

data class VideoTrailerDto(
    @SerializedName("iso_639_1")
    val iso6391: String,
    @SerializedName("iso_3166_1")
    val iso31661: String,
    val name: String,
    val key: String,
    val site: String,
    val size: Int,
    val type: String,
    val official: Boolean,
    @SerializedName("published_at")
    val publishedAt: String,
    val id: String
)

fun VideoTrailerDto.toVideoTrailer() = VideoTrailer(
    imgUrl = "https://img.youtube.com/vi/${key}/hqdefault.jpg",
    title = name
)
