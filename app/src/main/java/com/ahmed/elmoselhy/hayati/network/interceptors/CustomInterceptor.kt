package com.ahmed.elmoselhy.hayati.network.interceptors

import android.util.Log
import com.ahmed.elmoselhy.hayati.network.constants.Headers
import okhttp3.Interceptor
import okhttp3.Response
/**
 * Auth
 * Headers
 * Logging
 * Retry
 * Error Mapping
 * */
class CustomInterceptor(val name: String) : Interceptor {
    private val TAG = "CustomInterceptor"
    override fun intercept(chain: Interceptor.Chain): Response {
        Log.e(TAG, "intercept: Request started")
        val currentRequest = chain.request()
        val hasName = currentRequest.header("hasName")
        Log.e(TAG, "intercept: request url: ${currentRequest.url}")
        val updatedRequest = currentRequest.newBuilder().addHeader("X-App-Version", "1.0")
        if (hasName == "true") {
            updatedRequest.addHeader("Name", name)
        }
        val response = chain.proceed(currentRequest)
        Log.e(TAG, "intercept: Request finished")
        return response
    }
}