package com.ahmed.elmoselhy.hayati


import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.ahmed.elmoselhy.hayati.network.di.NetworkModule
import com.chuckerteam.chucker.api.Chucker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userRepository = UserRepository(NetworkModule.getClient())
//        startActivity(
//            Chucker.getLaunchIntent(this)
//        )
        lifecycleScope.launch {

            // Default base url
            val result1 = userRepository.getUser()

            Log.d("NETWORK", result1.toString())

            // Custom base url
            val result2 = userRepository.getCustomUser()

            Log.d("NETWORK", result2.toString())
        }

    }
}