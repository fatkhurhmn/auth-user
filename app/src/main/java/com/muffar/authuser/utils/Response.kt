package com.muffar.authuser.utils

sealed class Response<out T> {
    object Empty : Response<Nothing>()
    object Loading : Response<Nothing>()

    data class Success<out T>(
        val data: T,
    ) : Response<T>()

    data class Failure(
        val e: Exception?,
    ) : Response<Nothing>()
}