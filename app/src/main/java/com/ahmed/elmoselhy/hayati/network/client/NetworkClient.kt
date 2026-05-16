package com.ahmed.elmoselhy.hayati.network.client

import com.ahmed.elmoselhy.hayati.network.models.ApiRequest
import com.ahmed.elmoselhy.hayati.network.models.ApiResult
import kotlinx.serialization.KSerializer

interface NetworkClient {
    suspend fun <T> execute(
        request: ApiRequest,
        serializer: KSerializer<T>
    ): ApiResult<T>
}