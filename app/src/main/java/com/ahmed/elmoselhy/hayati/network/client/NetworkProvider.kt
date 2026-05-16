package com.ahmed.elmoselhy.hayati.network.client

import com.ahmed.elmoselhy.hayati.network.interceptors.CustomInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import kotlin.collections.forEach

object NetworkProvider {
    val client: OkHttpClient = getOkHttpClient()
    private fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BODY
        return logger
    }

    private fun provideOkHttpClientBuilder(
        list: List<Interceptor>
    ): OkHttpClient.Builder {
        val okHttpClientBuilder = OkHttpClient.Builder()
        list.forEach {
            okHttpClientBuilder.addInterceptor(it)
        }
        okHttpClientBuilder.addInterceptor(provideHttpLoggingInterceptor())
        return okHttpClientBuilder
    }

    private fun getOkHttpClient(): OkHttpClient {
        val okHttpClient = provideOkHttpClientBuilder(list = listOf(CustomInterceptor("Ahmed"))).build()

        return okHttpClient
    }
}