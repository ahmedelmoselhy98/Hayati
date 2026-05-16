package com.ahmed.elmoselhy.hayati.network.extensions

import com.ahmed.elmoselhy.hayati.network.models.RequestBodyData
import kotlinx.serialization.KSerializer

inline fun <reified T> T.toRequestBodyData(
    serializer: KSerializer<T>
): RequestBodyData<T> {

    return RequestBodyData(
        body = this,
        serializer = serializer
    )
}