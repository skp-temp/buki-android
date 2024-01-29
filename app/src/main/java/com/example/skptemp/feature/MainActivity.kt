package com.example.skptemp.feature

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.forEach
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.example.skptemp.R
import com.example.skptemp.common.util.showToast
import com.example.skptemp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private var lastBackPressedTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        startSplashScreen()
        addOnBackPressedCallback()
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

    private fun addOnBackPressedCallback() {
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val backPressedTime = System.currentTimeMillis()

                if (backPressedTime - lastBackPressedTime < BACK_INTERVAL) {
                    finish()
                }

                lastBackPressedTime = backPressedTime
                showToast(this@MainActivity, resources.getString(R.string.back_pressed))
            }
        }

        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    override fun onResume() {
        super.onResume()
        setUpBottomNavigationBar()
    }

    private fun setUpBottomNavigationBar() = with(binding.bottomNavigationBar) {
        val navController = findNavController(R.id.nav_host_fragment)
        setupWithNavController(navController)

        itemIconTintList = null

        menu.forEach { item ->
            findViewById<View>(item.itemId).setOnLongClickListener { true }
        }

        setOnItemSelectedListener { item ->
            Log.d(TAG, "Bottom Navigation Bar selected $item")
            lastBackPressedTime = 0
            onNavDestinationSelected(item, navController)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private val TAG = MainActivity::class.simpleName
        private const val SPLASH_DURATION: Long = 700
        private const val BACK_INTERVAL = 2000
    }
}