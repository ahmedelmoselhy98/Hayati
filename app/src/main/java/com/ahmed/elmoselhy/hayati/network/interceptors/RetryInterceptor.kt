package com.ahmed.elmoselhy.hayati.network.interceptors

import okhttp3.Interceptor
import okhttp3.Response

class RetryInterceptor(
    private val maxRetries: Int = 3
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var attempt = 0
        var response: Response
        var exception: Exception? = null

        while (attempt < maxRetries) {
            try {
                response = chain.proceed(chain.request())

                if (response.isSuccessful) {
                    return response
                }

            } catch (e: Exception) {
                exception = e
            }

            attempt++
        }

        throw exception ?: RuntimeException("Unknown network error")
    }
}