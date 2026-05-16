package com.ahmed.elmoselhy.hayati.network.interceptors

import com.ahmed.elmoselhy.hayati.network.constants.Headers
import okhttp3.Interceptor
import okhttp3.Response

class HeadersInterceptor(val name: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()
            .addHeader(Headers.APP_VERSION, "1.0")
            .addHeader(Headers.PLATFORM, "Android")
            .addHeader(Headers.LANGUAGE, "en")
            .addHeader(Headers.CONTENT_TYPE, "application/json")
            .addHeader(Headers.ACCEPT, "application/json")
        if (originalRequest.header("hasName") == "true") {
            requestBuilder.addHeader("Name", name)
            requestBuilder.addHeader(Headers.AUTHORIZATION, "Bearer your_token_here")
        }
        val newRequest = requestBuilder.build()
        return chain.proceed(newRequest)
    }
}