package com.ahmed.elmoselhy.hayati.network.providers

import com.ahmed.elmoselhy.hayati.network.models.ApiRequest

class CustomBaseUrlProvider(
    private val request: ApiRequest
) : BaseUrlProvider {

    override fun getBaseUrl(): String? {
        return request.customUrl
    }
}