package com.weather.data.util

import com.weather.data.R
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

suspend fun <T> safeApiCall(
        dispatcher: CoroutineDispatcher,
        apiCall: suspend () -> T
): Resource<T> {
    return withContext(dispatcher) {
        try {
            Resource.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> Resource.Error(message = R.string.network_error)
                is HttpException -> {

                    Resource.Error(messageText = throwable.localizedMessage)
                }
                else -> Resource.Error(message = R.string.service_error)
            }
        }
    }
}
