package com.example.skptemp.feature.signup

import android.os.Bundle
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.skptemp.R
import com.example.skptemp.common.ui.Toolbar
import com.example.skptemp.common.ui.ViewPagerAdapter
import com.example.skptemp.databinding.ActivitySignUpBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {

    private var _binding: ActivitySignUpBinding? = null
    private val binding get() = _binding!!

    private val mViewPager by lazy { binding.viewPager }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()

        composeUI()
        addOnBackPressedCallback()
    }

    private fun composeUI() {
        setupViewPager()

        binding.toolbar.setButtonOnClickListener(Toolbar.BACK_BUTTON) {
            moveToPreviousPage()
        }

        binding.nextButton.setOnClickListener {
            moveToNextPage()
        }
    }

    private fun setupViewPager() {
        val adapter = ViewPagerAdapter(
            supportFragmentManager,
            lifecycle
        )

        adapter.addFragment(SignUpTermsFragment(), resources.getString(R.string.terms_agreement))
        adapter.addFragment(SignUpNameFragment(), resources.getString(R.string.sign_up))

        mViewPager.adapter = adapter
        mViewPager.isUserInputEnabled = false // 스와이프 막기

        mViewPager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                Log.d(TAG, "ViewPager changed page $position")
                binding.toolbar.setTitleText(adapter.getTitle(position))
            }
        })
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
        if (mViewPager.currentItem++ < mViewPager.childCount) return
        // TODO: 서버 통신
        setResult(RESULT_OK)
        finish()
    }

    private fun moveToPreviousPage() {
        if (mViewPager.currentItem-- > 0) return
        finish()
    }

    fun enabledNextButton(isEnabled: Boolean) =
        binding.nextButton.setEnabledButton(isEnabled)

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private val TAG = SignUpActivity::class.simpleName
    }
}