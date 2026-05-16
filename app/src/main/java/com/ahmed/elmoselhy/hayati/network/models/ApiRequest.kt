package com.ahmed.elmoselhy.hayati.network.models

data class ApiRequest(

    val endpoint: String,

    val customUrl: String? = null,

    val method: HttpMethod,

    val pathParameters: Map<String, String> = emptyMap(),

    val queries: Map<String, String> = emptyMap(),

    val headers: Map<String, String> = emptyMap(),

    val body: RequestBodyData<*>? = null
)