package com.example.skptemp.feature.signup

import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.skptemp.R
import com.example.skptemp.common.ui.Toolbar
import com.example.skptemp.common.ui.ViewPagerAdapter
import com.example.skptemp.databinding.ActivitySignUpBinding
import com.example.skptemp.feature.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {

    private var _binding: ActivitySignUpBinding? = null
    private val binding get() = _binding!!

    private val mViewPager by lazy { binding.viewPager }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
    }

    override fun onResume() {
        super.onResume()

        composeUI()
        addOnBackPressedCallback()
    }

    private fun composeUI() {
        setupViewPager()
        setupToolbar()

        binding.nextButton.setOnClickListener {
            moveToNextPage()
        }
    }

    private fun setupViewPager() {
        val fragments = listOf(
            SignUpTermsFragment(),
            SignUpNameFragment()
        )

        val adapter = ViewPagerAdapter(
            fragments,
            supportFragmentManager,
            lifecycle
        )

        mViewPager.adapter = adapter
        mViewPager.isUserInputEnabled = false // 스와이프 막기
    }

    private fun setupToolbar() {
        binding.toolbar.setButtonOnClickListener(Toolbar.BACK_BUTTON) {
            moveToPreviousPage()
        }
    }

    private fun addOnBackPressedCallback() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                moveToPreviousPage()
            }
        }

        this.onBackPressedDispatcher.addCallback(this, callback)
    }

    private fun moveToNextPage() {
        if (mViewPager.currentItem++ < mViewPager.childCount - 1) return

        // TODO: 서버 통신
        startActivity(Intent(this@SignUpActivity, MainActivity::class.java))
    }

    private fun moveToPreviousPage() {
        if (mViewPager.currentItem-- > 0) return
        finish()
    }

    fun setToolbarTitleText(text: String) = binding.toolbar.setTitleText(text)

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private val TAG = SignUpActivity::class.simpleName
    }
}