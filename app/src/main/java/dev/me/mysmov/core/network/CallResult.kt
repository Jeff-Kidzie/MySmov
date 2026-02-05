package dev.me.mysmov.core.network

sealed class CallResult<out T> {
    data class Success<out T>(val data: T) : CallResult<T>()
    data class Error(val errorCode: Int, val message: String) : CallResult<Nothing>()
    data class Exception(val exception: Throwable) : CallResult<Nothing>()
}