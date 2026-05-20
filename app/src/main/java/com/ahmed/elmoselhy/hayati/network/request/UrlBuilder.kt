package com.ahmed.elmoselhy.hayati.network.request

import java.net.URLEncoder
import java.nio.charset.StandardCharsets

object UrlBuilder {

    fun build(
        baseUrl: String,
        endpoint: String,
        pathParameters: Map<String, String> = emptyMap()
    ): String {

        var url = join(baseUrl, endpoint)

        pathParameters.forEach { (key, value) ->
            url = url.replace("{$key}", encode(value))
        }

        return url
    }

    private fun join(base: String, path: String): String {
        return base.trimEnd('/') + "/" + path.trimStart('/')
    }

    private fun encode(value: String): String {
        return URLEncoder.encode(value, StandardCharsets.UTF_8.toString())
            .replace("+", "%20")
    }
}