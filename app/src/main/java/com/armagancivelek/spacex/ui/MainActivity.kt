package com.armagancivelek.spacex.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.armagancivelek.spacex.R
import com.armagancivelek.spacex.utils.isConnected
import com.armagancivelek.spacex.utils.showInternetDialog
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.SplashScreen)

        if (isConnected(this)) {
            GlobalScope.launch {
                delay(2000)
                withContext(Dispatchers.Main)
                {
                    setContentView(R.layout.activity_main)
                }
            }

        } else {
            showInternetDialog(this)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}