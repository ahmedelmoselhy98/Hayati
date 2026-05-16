package com.ahmed.elmoselhy.hayati.network.errors

sealed interface NetworkError {

    data object NoInternet : NetworkError

    data object Timeout : NetworkError

    data class HttpError(
        val code: Int,
        val message: String?
    ) : NetworkError

    data class SerializationError(
        val message: String?
    ) : NetworkError

    data class Unknown(
        val throwable: Throwable
    ) : NetworkError
}