package com.ahmed.elmoselhy.hayati.network.config

data class NetworkConfig(

    val defaultBaseUrl: String,

    val connectTimeoutSeconds: Long = 30,

    val readTimeoutSeconds: Long = 30,

    val writeTimeoutSeconds: Long = 30,

    val defaultHeaders: Map<String, String> = emptyMap(),

    val enableLogging: Boolean = true
)