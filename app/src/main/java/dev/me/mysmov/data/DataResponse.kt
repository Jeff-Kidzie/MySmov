package dev.me.mysmov.data

import com.google.gson.annotations.SerializedName

data class DataResponse<T>(
    val page: Int,
    @SerializedName("results", alternate = ["cast"])
    val results: List<T>,
)
