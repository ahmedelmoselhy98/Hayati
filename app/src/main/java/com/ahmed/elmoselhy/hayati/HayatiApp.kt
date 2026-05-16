package com.ahmed.elmoselhy.hayati

import android.app.Application
import com.ahmed.elmoselhy.hayati.network.config.NetworkConfig
import com.ahmed.elmoselhy.hayati.network.di.NetworkModule

class HayatiApp : Application() {
    override fun onCreate() {
        super.onCreate()
        NetworkModule.init(
            NetworkConfig(
                defaultBaseUrl = "https://api.github.com/"
            ), context = this
        )
    }
}