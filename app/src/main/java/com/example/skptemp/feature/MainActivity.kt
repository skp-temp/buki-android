package com.example.skptemp.feature

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewTreeObserver
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.TooltipCompat
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.forEach
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.skptemp.R
import com.example.skptemp.common.util.ViewUtil.convertPXtoDP
import com.example.skptemp.databinding.ActivityMainBinding
import com.example.skptemp.feature.friends.FriendsFragment
import com.example.skptemp.feature.home.HomeFragment
import com.example.skptemp.feature.my.MyFragment
import com.example.skptemp.feature.report.ReportFragment
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private val mFragments = mapOf(
        R.id.navigation_home to HomeFragment(),
        R.id.navigation_report to ReportFragment(),
        R.id.navigation_friends to FriendsFragment(),
        R.id.navigation_my to MyFragment()
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
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

    override fun onResume() {
        super.onResume()
        setUpBottomNavigationBar()
    }

    private fun setUpBottomNavigationBar() = with(binding.bottomNavigationBar) {
        findNavController(R.id.nav_host_fragment).let { navController ->
            setupWithNavController(navController)
        }
        itemIconTintList = null

        menu.forEach { item ->
            findViewById<View>(item.itemId).setOnLongClickListener { true }
        }
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