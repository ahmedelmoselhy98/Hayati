package com.ahmed.elmoselhy.hayati.network.providers

import android.content.SharedPreferences

class LocalBaseUrlProvider(
    private val sharedPrefs: SharedPreferences
) : BaseUrlProvider {

    override fun getBaseUrl(): String? {

        return sharedPrefs.getString(
            "base_url",
            null
        )
    }

    fun updateBaseUrl(url: String) {

    }
}