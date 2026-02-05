package dev.me.mysmov.core.network

import retrofit2.Response

suspend fun <T> callApi(
    apiCall: suspend () -> Response<T>
): CallResult<T> {
    return try {
        val response = apiCall()
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                CallResult.Success(body)
            } else {
                CallResult.Error(
                    errorCode = response.code(),
                    message = "Empty response body"
                )
            }
        } else {
            CallResult.Error(response.code(), response.message())
        }
    } catch (e: Exception) {
        CallResult.Exception(e)
    }
}

fun <T,R> CallResult<T>.transform(transform : (T) -> R) : CallResult<R> {
    return when(this) {
        is CallResult.Success -> CallResult.Success(transform(this.data))
        is CallResult.Error -> CallResult.Error(this.errorCode, this.message)
        is CallResult.Exception -> CallResult.Exception(this.exception)
    }

}
