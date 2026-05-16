package com.ahmed.elmoselhy.hayati.network.models

import com.ahmed.elmoselhy.hayati.network.errors.NetworkError

sealed interface ApiResult<out T> {

    data class Success<T>(
        val data: T,
        val statusCode: Int
    ) : ApiResult<T>

    data class Failure(
        val error: NetworkError
    ) : ApiResult<Nothing>
}