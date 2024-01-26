package com.example.skptemp.feature.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.skptemp.R
import com.example.skptemp.common.ui.Toolbar
import com.example.skptemp.common.util.ColorUtil
import com.example.skptemp.common.util.ViewPagerUtil.setPageChangeAnimation
import com.example.skptemp.common.util.ViewPagerUtil.setSwipeAction
import com.example.skptemp.common.util.ViewUtil.getDeviceHeightPx
import com.example.skptemp.common.util.ViewUtil.setHeightPx
import com.example.skptemp.databinding.FragmentHomeBinding
import com.example.skptemp.feature.home.adapter.CharmImageListAdapter
import com.example.skptemp.feature.home.adapter.CharmInfoListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var mCheckedTextColor = 0
    private var mUncheckedTextColor = 0

    private lateinit var mContext: Context

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        mContext = requireContext()
        mCheckedTextColor = ColorUtil.getColor(mContext, CHECKED_TEXT_COLOR)
        mUncheckedTextColor = ColorUtil.getColor(mContext, UNCHECKED_TEXT_COLOR)

        updateViewPagerSize()
        updateLayoutSize()
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

    private fun updateLayoutSize() = with(binding.root) {
        post { setLayoutHeight(height) }
    }

    private fun setLayoutHeight(fragmentHeightPx: Int) = with(binding) {
        // fragment - empty layout - toolbar
        val heightPx =
            fragmentHeightPx - fragmentHeightPx * EMPTY_LAYOUT_RATIO - resources.getDimensionPixelOffset(
                R.dimen.toolbar_height
            )
        val ratioHeight = resources.getDimension(R.dimen.home_fragment_ratio_height)
        val viewPagerLayoutHeight = resources.getDimension(R.dimen.view_pager_layout_height)
        val switchLayoutHeight = resources.getDimension(R.dimen.switch_layout_height)

        viewPagerLayout.setHeightPx((heightPx * (viewPagerLayoutHeight / ratioHeight)).toInt())
        switchLayout.setHeightPx((heightPx * (switchLayoutHeight / ratioHeight)).toInt())
    }

    override fun onResume() {
        super.onResume()
        composeToolbar()
        composeViewPager()
        composeRecyclerView()

        binding.charmSwitch.setOnCheckedChangeListener { _, isSelectedDone ->
            changeSwitchText(isSelectedDone)
        }
        binding.doneText.text = "도전 완료 12"
        binding.progressText.text = "도전 중 8"
    }

    private fun changeSwitchText(isSelectedDone: Boolean) = with(binding) {
        if (isSelectedDone) {
            binding.doneText.setTextColor(mCheckedTextColor)
            binding.progressText.setTextColor(mUncheckedTextColor)
            return
        }

        binding.progressText.setTextColor(mCheckedTextColor)
        binding.doneText.setTextColor(mUncheckedTextColor)
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
            mContext,
            listOf(
                R.color.yellowgreen_200,
                R.color.purple_200,
                R.color.brown_200
            )
        )

        offscreenPageLimit = 3
        getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        binding.viewPagerIndicator.attachTo(this)

        //setSwipeAction(binding.scrollView)
        setPageChangeAnimation()
    }

    private fun composeRecyclerView() = with(binding.charmRecyclerView) {
        layoutManager = LinearLayoutManager(mContext)
        overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        adapter = CharmInfoListAdapter(listOf("1번째", "두번째"))
        addItemDecoration(
            GridRecyclerViewItemDecoration(
                topSpace = ITEM_TOP_SPACE,
                rowSpace = ITEM_SPACE,
                endSpace = ITEM_END_SPACE,
                context = mContext
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private val TAG = HomeFragment::class.simpleName
        private val CHECKED_TEXT_COLOR = R.color.white
        private val UNCHECKED_TEXT_COLOR = R.color.gray_500
        private const val EMPTY_LAYOUT_RATIO = 0.3f

        private const val ITEM_TOP_SPACE = 16
        private const val ITEM_SPACE = 12
        private const val ITEM_END_SPACE = 56
    }
}