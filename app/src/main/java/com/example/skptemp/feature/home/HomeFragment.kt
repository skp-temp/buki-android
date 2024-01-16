package com.example.skptemp.feature.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.skptemp.R
import com.example.skptemp.common.ui.Toolbar
import com.example.skptemp.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.abs
import kotlin.math.max

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        updateViewPagerSize()
        return binding.root
    }

    private fun updateViewPagerSize() = with(binding.charmImageViewPager) {
        post { setHorizontalPadding(height) }
    }

    private fun setHorizontalPadding(viewPagerHeightPx: Int) = with(binding.charmImageViewPager) {
        val screenWidthPx = resources.displayMetrics.widthPixels
        val widthRatio = resources.getDimension(R.dimen.charm_pager_width) /
                resources.getDimension(R.dimen.charm_pager_height)
        val widthPx = widthRatio * viewPagerHeightPx

        val padding = ((screenWidthPx - widthPx) / 2).toInt()
        setPadding(padding, 0, padding, 0)
    }

    override fun onResume() {
        super.onResume()
        composeToolbar()
        composeViewPager()
    }

    private fun composeToolbar() = with(binding.toolbar) {
        visibleButton(Toolbar.BELL_BUTTON)
        visibleButton(Toolbar.GIFT_BUTTON)

        setButtonOnClickListener(Toolbar.BELL_BUTTON) {
            Log.d(TAG, "Toolbar Bell Button click")
        }
        setButtonOnClickListener(Toolbar.GIFT_BUTTON) {
            Log.d(TAG, "Toolbar Gift Button click")
        }
    }

    private fun composeViewPager() = with(binding.charmImageViewPager) {
        adapter = CharmImageListAdapter(
            requireContext(),
            listOf(
                R.color.yellowgreen_200,
                R.color.purple_200,
                R.color.brown_200
            )
        )

        offscreenPageLimit = 3
        getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        binding.viewPagerIndicator.attachTo(this)

        setPageChangeAnimation()
    }

    private fun ViewPager2.setPageChangeAnimation() {
        val marginPx = resources.getDimensionPixelOffset(R.dimen.charm_pager_margin)

        val transform = CompositePageTransformer().apply {
            addTransformer(MarginPageTransformer(marginPx))
            addTransformer { view, position ->
                val scaleFactor = max(MIN_SCALE, 1 - abs(position))

                view.scaleX = scaleFactor
                view.scaleY = scaleFactor

                view.alpha = (MIN_ALPHA +
                        (((scaleFactor - MIN_SCALE) / (1 - MIN_SCALE)) * (1 - MIN_ALPHA)))
            }
        }

        setPageTransformer(transform)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private val TAG = HomeFragment::class.simpleName
        private const val MIN_SCALE = 0.9f
        private const val MIN_ALPHA = 0.5f
    }
}