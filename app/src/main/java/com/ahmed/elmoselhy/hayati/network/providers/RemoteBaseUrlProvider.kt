package com.ahmed.elmoselhy.hayati.network.providers

class RemoteBaseUrlProvider : BaseUrlProvider {

    @Volatile
    private var baseUrl: String? = null

    fun update(url: String) {
        baseUrl = url
    }

    override fun getBaseUrl(): String? {
        return baseUrl
    }
}