package com.example.skptemp.feature.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.skptemp.R
import com.example.skptemp.common.constants.CharmType
import com.example.skptemp.common.ui.GridRecyclerViewItemDecoration
import com.example.skptemp.common.ui.component.Toolbar
import com.example.skptemp.common.ui.setOnSingleClickListener
import com.example.skptemp.feature.home.CharmViewPagerManager.setHorizontalPadding
import com.example.skptemp.feature.home.CharmViewPagerManager.setPageChangeAnimation
import com.example.skptemp.common.util.ViewUtil.getHeightPxByRatio
import com.example.skptemp.common.util.ViewUtil.setHeightByRatio
import com.example.skptemp.databinding.FragmentHomeBinding
import com.example.skptemp.feature.home.adapter.CharmImageListAdapter
import com.example.skptemp.feature.home.adapter.CharmInfoListAdapter
import com.example.skptemp.model.CharmInfo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var mContext: Context

    // TODO: 서버 통신
    private val mProgressCharms = listOf(
        CharmInfo(CharmType.WORKOUT, false, "테스트1", 1),
        CharmInfo(CharmType.HAPPY, false, "테스트2", 20),
        CharmInfo(CharmType.MONEY, true, "테스트3", 15),
        CharmInfo(CharmType.PET, true, "글자수테스트글자수테스트글자수테스트", 3),
        CharmInfo(CharmType.WORKOUT, true, "글자수테스트글자수테스트글자수테스트", 5)
    )
    private val mDoneCharms = listOf<CharmInfo>()
    private val mRecyclerViewCharms
        get() = getRecyclerViewCharms(binding.charmSwitch.isChecked)

    private val mCharmImageListAdapter by lazy {
        CharmImageListAdapter(
            mContext,
            mProgressCharms.toMutableList()
        )
    }
    private val mCharmInfoListAdapter by lazy { CharmInfoListAdapter(mRecyclerViewCharms.toMutableList()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        mContext = requireContext()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerViewItemDecoration()
    }

    private fun setRecyclerViewItemDecoration() {
        binding.charmRecyclerView.addItemDecoration(
            GridRecyclerViewItemDecoration(
                topSpaceId = R.dimen.charm_list_top_space,
                rowSpaceId = R.dimen.charm_list_row_space,
                endSpaceId = R.dimen.scroll_end_margin,
                context = mContext
            )
        )
    }

    override fun onResume() = with(binding) {
        super.onResume()
        composeUI()
    }

    private fun composeUI() = with(binding) {
        setLayoutHeightByRatio()
        updateLayout(UPDATE_ALL)

        composeSwitch()
        composeToolbar()
        composeViewPager()

        charmRecyclerView.adapter = mCharmInfoListAdapter
        emptyCharmImage.setOnSingleClickListener {
            //startActivity(Intent(mContext, CharmCreatActivity::class.java))
        }
    }

    private fun setLayoutHeightByRatio() = with(binding) {
        viewPagerLayout.setHeightByRatio(mContext, VIEW_PAGER_LAYOUT_RATIO)
        indicatorLayout.setHeightByRatio(mContext, INDICATOR_LAYOUT_RATIO)
    }

    private fun composeSwitch() = with(binding.charmSwitch) {
        updateSwitchTextColor(isChecked)
        off = resources.getString(R.string.switch_progress, mProgressCharms.size)
        on = resources.getString(R.string.switch_done, mDoneCharms.size)

        setOnCheckedChangeListener {
            updateRecyclerView()
        }
    }

    private fun getRecyclerViewCharms(isCheckedDone: Boolean) =
        if (isCheckedDone) mDoneCharms else mProgressCharms

    private fun updateRecyclerView() {
        updateLayout(UPDATE_RECYCLER_VIEW)

        if (mRecyclerViewCharms.isEmpty()) return
        mCharmInfoListAdapter.updateAll(mRecyclerViewCharms)
    }

    private fun updateLayout(updateType: Int) {
        val charmsSize = mRecyclerViewCharms.size
        val emptyVisibility = getVisibility(charmsSize == 0)
        val viewPagerVisibility = getVisibility(charmsSize > 0)

        updateRecyclerViewLayout(emptyVisibility, viewPagerVisibility)

        if (updateType == UPDATE_RECYCLER_VIEW) return
        updateViewPagerLayout(
            indicatorVisibility = getVisibility(charmsSize > 1),
            emptyVisibility,
            viewPagerVisibility
        )
    }

    private fun getVisibility(isVisible: Boolean) =
        if (isVisible) View.VISIBLE else View.GONE

    private fun updateViewPagerLayout(
        indicatorVisibility: Int,
        emptyVisibility: Int,
        viewPagerVisibility: Int
    ) = with(binding) {

        indicatorLayout.visibility = indicatorVisibility
        charmImageViewPager.visibility = viewPagerVisibility
        emptyCharmImageLayout.visibility = emptyVisibility
    }

    private fun updateRecyclerViewLayout(
        emptyVisibility: Int,
        viewPagerVisibility: Int
    ) = with(binding) {

        emptyLayout.visibility = emptyVisibility
        charmRecyclerView.visibility = viewPagerVisibility

        if (mRecyclerViewCharms.isNotEmpty()) return
        emptyText.text = resources.getString(
            if (charmSwitch.isChecked) R.string.empty_done_charm else R.string.empty_progress_charm
        )
    }

    private fun composeToolbar() = with(binding.toolbar) {
        visibleButton(Toolbar.BELL_BUTTON)
        visibleButton(Toolbar.GIFT_BUTTON)

        setButtonOnClickListener(Toolbar.BELL_BUTTON) {
            Log.d(TAG, "Toolbar Bell Button click")
            startActivity(Intent(mContext, NotificationActivity::class.java))
        }
        setButtonOnClickListener(Toolbar.GIFT_BUTTON) {
            Log.d(TAG, "Toolbar Gift Button click")
        }
        setTitleOnClickListener {
            binding.scrollLayout.fullScroll(NestedScrollView.FOCUS_UP)
        }
    }

    private fun composeViewPager() = with(binding.charmImageViewPager) {
        setHorizontalPadding(getHeightPxByRatio(mContext, VIEW_PAGER_RATIO - STROKE_WIDTH * 2))
        //setSwipeAction(binding.scrollView)
        setPageChangeAnimation()

        adapter = mCharmImageListAdapter
        offscreenPageLimit = 3
        getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        binding.viewPagerIndicator.attachTo(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private val TAG = HomeFragment::class.simpleName

        private const val UPDATE_RECYCLER_VIEW = 1
        private const val UPDATE_ALL = 2

        private const val STROKE_WIDTH = 2
        private const val VIEW_PAGER_RATIO = 300
        private const val VIEW_PAGER_LAYOUT_RATIO = 332
        private const val INDICATOR_LAYOUT_RATIO = 30
    }
}