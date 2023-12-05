package com.example.skptemp.feature

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.databinding.DataBindingUtil
import com.example.skptemp.R
import com.example.skptemp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        _binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        startSplashScreen()
    }

    private fun startSplashScreen() {
        Log.d(TAG, "Splash screen ${SPLASH_DURATION}ms delayed")

        var isReady = false

        Handler(Looper.getMainLooper()).postDelayed({
            isReady = true
        }, SPLASH_DURATION)

        val content = findViewById<View>(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    return isReady.also {
                        if (it) content.viewTreeObserver.removeOnPreDrawListener(this)
                    }
                }
            }
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private val TAG = MainActivity::class.simpleName
        private const val SPLASH_DURATION: Long = 700
    }
}