package com.btcturk.data.util

sealed class Resource<out T> {
    data class Success<out T>(val data: T?) : Resource<T>()
    data class Error(val message: Int? = null, val messageText: String? = null) :
        Resource<Nothing>()
}
