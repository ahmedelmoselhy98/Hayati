package com.ahmed.elmoselhy.hayati.network.models

import kotlinx.serialization.KSerializer

data class RequestBodyData<T>(
    val body: T,
    val serializer: KSerializer<T>
)