package com.ahmed.elmoselhy.hayati.network.request

object UrlBuilder {

    fun build(
        url: String,
        endpoint: String,
        pathParameters: Map<String, String>
    ): String {
        var finalUrl = url + endpoint
        if (pathParameters.isEmpty()) return finalUrl
        pathParameters.forEach { (key, value) ->
            finalUrl = url.replace("{$key}", value)
        }
        return finalUrl
    }
}