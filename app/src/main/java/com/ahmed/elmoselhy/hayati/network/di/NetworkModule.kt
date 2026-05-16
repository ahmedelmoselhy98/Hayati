package com.ahmed.elmoselhy.hayati.network.di

import android.content.Context
import com.ahmed.elmoselhy.hayati.network.client.NetworkClient
import com.ahmed.elmoselhy.hayati.network.client.RetrofitNetworkClient
import com.ahmed.elmoselhy.hayati.network.config.NetworkConfig
import com.ahmed.elmoselhy.hayati.network.providers.RemoteBaseUrlProvider
import com.ahmed.elmoselhy.hayati.network.service.RetrofitApiService
import com.chuckerteam.chucker.api.ChuckerInterceptor
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit


object NetworkModule {
    private var networkClient: NetworkClient? = null
    val remoteBaseUrlProvider: RemoteBaseUrlProvider =
        RemoteBaseUrlProvider()

    fun init(
        config: NetworkConfig,
        context: Context
    ) {

        if (networkClient != null) return

        networkClient = createClient(config, context)
    }

    fun getClient(): NetworkClient {
        return requireNotNull(networkClient)
    }

    private fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BODY
        return logger
    }

    fun createClient(
        config: NetworkConfig,
        context: Context
    ): NetworkClient {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(provideHttpLoggingInterceptor())
            .addInterceptor(ChuckerInterceptor(context.applicationContext))
            .connectTimeout(
                config.connectTimeoutSeconds,
                TimeUnit.SECONDS
            )
            .readTimeout(
                config.readTimeoutSeconds,
                TimeUnit.SECONDS
            )
            .writeTimeout(
                config.writeTimeoutSeconds,
                TimeUnit.SECONDS
            )
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(config.defaultBaseUrl)
            .client(okHttpClient)
            .build()

        val service = retrofit.create(
            RetrofitApiService::class.java
        )

        val json = Json {
            ignoreUnknownKeys = true
            isLenient = true
        }

        return RetrofitNetworkClient(
            service = service,
            json = json,
            config = config,
            remoteBaseUrlProvider = remoteBaseUrlProvider,

            )
    }
}