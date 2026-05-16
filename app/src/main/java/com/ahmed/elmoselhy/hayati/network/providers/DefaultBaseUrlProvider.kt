package com.ahmed.elmoselhy.hayati.network.providers

import com.ahmed.elmoselhy.hayati.network.config.NetworkConfig

class DefaultBaseUrlProvider(
    private val config: NetworkConfig
) : BaseUrlProvider {

    override fun getBaseUrl(): String {
        return config.defaultBaseUrl
    }
}