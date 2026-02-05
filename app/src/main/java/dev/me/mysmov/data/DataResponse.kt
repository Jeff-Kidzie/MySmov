package dev.me.mysmov.data

data class DataResponse<T>(
    val page: Int,
    val results: List<T>,
)
