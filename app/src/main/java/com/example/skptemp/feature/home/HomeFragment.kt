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
import com.example.skptemp.common.constants.CharmType
import com.example.skptemp.common.ui.GridRecyclerViewItemDecoration
import com.example.skptemp.common.ui.component.Toolbar
import com.example.skptemp.common.util.ColorUtil
import com.example.skptemp.feature.home.CharmViewPagerManager.setHorizontalPadding
import com.example.skptemp.feature.home.CharmViewPagerManager.setPageChangeAnimation
import com.example.skptemp.common.util.ViewUtil.getHeightPxByRatio
import com.example.skptemp.common.util.ViewUtil.setHeightByRatio
import com.example.skptemp.databinding.FragmentHomeBinding
import com.example.skptemp.feature.home.adapter.CharmImageListAdapter
import com.example.skptemp.feature.home.adapter.CharmInfoListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val mCheckedTextColor by lazy { ColorUtil.getColor(mContext, CHECKED_TEXT_COLOR) }
    private val mUncheckedTextColor by lazy { ColorUtil.getColor(mContext, UNCHECKED_TEXT_COLOR) }

    private lateinit var mContext: Context

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        mContext = requireContext()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        setLayoutHeightByRatio()

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

    private fun setLayoutHeightByRatio() = with(binding) {
        viewPagerLayout.setHeightByRatio(mContext, VIEW_PAGER_LAYOUT_RATIO)
        indicatorLayout.setHeightByRatio(mContext, INDICATOR_LAYOUT_RATIO)
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
        setHorizontalPadding(getHeightPxByRatio(mContext, VIEW_PAGER_RATIO - STROKE_WIDTH * 2))
        //setSwipeAction(binding.scrollView)
        setPageChangeAnimation()

        adapter = CharmImageListAdapter(
            mContext,
            listOf(
                CharmType.WORKOUT,
                CharmType.HAPPY,
                CharmType.STUDY
            )
        )

        offscreenPageLimit = 3
        getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        binding.viewPagerIndicator.attachTo(this)
    }

    private fun composeRecyclerView() = with(binding.charmRecyclerView) {
        layoutManager = LinearLayoutManager(mContext)
        adapter = CharmInfoListAdapter(listOf("1번째", "두번째"))
        addItemDecoration(
            GridRecyclerViewItemDecoration(
                topSpaceId = R.dimen.charm_list_top_space,
                rowSpaceId = R.dimen.charm_list_row_space,
                endSpaceId = R.dimen.scroll_end_margin,
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

        private const val STROKE_WIDTH = 2
        private const val VIEW_PAGER_RATIO = 300
        private const val VIEW_PAGER_LAYOUT_RATIO = 332
        private const val INDICATOR_LAYOUT_RATIO = 30
    }
}