package com.example.skptemp.feature.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import com.example.skptemp.R
import com.example.skptemp.common.constants.CharmType
import com.example.skptemp.common.constants.EmotionType
import com.example.skptemp.common.ui.GridRecyclerViewItemDecoration
import com.example.skptemp.common.ui.component.StampWeek
import com.example.skptemp.common.ui.inf.BottomDialogItem
import com.example.skptemp.common.ui.inf.OnBottomDialogListener
import com.example.skptemp.common.ui.component.Toolbar
import com.example.skptemp.common.ui.setOnSingleClickListener
import com.example.skptemp.common.util.ColorUtil
import com.example.skptemp.common.util.ViewUtil.setHeightByRatio
import com.example.skptemp.databinding.ActivityCharmDetailBinding
import com.example.skptemp.feature.home.adapter.CharmMessageListAdapter
import com.example.skptemp.model.CharmMessage
import com.example.skptemp.model.CharmStamp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharmDetailActivity : AppCompatActivity() {

    private var _binding: ActivityCharmDetailBinding? = null
    private val binding get() = _binding!!

    private val mContext: Context by lazy { this }

    private var mDialog: CharmEditDialog? = null

    lateinit var mCharmType: CharmType
    private lateinit var mCharmTitle: String
    private lateinit var mStampWeeks: List<StampWeek>

    private var mRecordFragment: CharmRecordFragment? = null
    private var mToolbarTitle = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityCharmDetailBinding.inflate(layoutInflater)

        // TODO: home_fragment에서 bundle 값 가져오기
        mCharmType = CharmType.WORKOUT
        mCharmTitle = "하루 한번 플랭크 하기"

        mStampWeeks = listOf(binding.week1, binding.week2, binding.week3)

        setContentView(binding.root)
        setRecyclerViewItemDecoration()
    }

    private fun setRecyclerViewItemDecoration() {
        binding.messageRecyclerView.addItemDecoration(
            GridRecyclerViewItemDecoration(
                spanCount = 2,
                topSpaceId = R.dimen.message_list_top_space,
                rowSpaceId = R.dimen.message_list_row_space,
                colSpaceId = R.dimen.message_list_col_space,
                context = mContext
            )
        )
    }

    override fun onResume() {
        super.onResume()
        composeUI()
    }

    private fun composeUI() = with(binding) {
        charmTypeTag.setTagType(mCharmType)
        charmTitle.text = mCharmTitle

        setLayoutHeightByRatio()
        setCharmEditDialogClickListener()
        setToolbarAction()
        setupStampList()
        setupMessageList()

        charmEditButton.setOnSingleClickListener {
            Log.d(TAG, "Charm edit button onClick()")
        }

        largeButton.setOnSingleClickListener {
            startRecordFragment()
        }
    }

    // TODO: item 별로 레이아웃 분리 (뷰홀더)
    private fun setLayoutHeightByRatio() = with(binding) {
        space1.setHeightByRatio(mContext, CHARM_TITLE_TOP_RATIO)
        space2.setHeightByRatio(mContext, CHARM_TITLE_MIDDLE_RATIO)
        space3.setHeightByRatio(mContext, CHARM_TITLE_BOTTOM_RATIO)

        space4.setHeightByRatio(mContext, PROGRESS_TOP_RATIO)
        space5.setHeightByRatio(mContext, PROGRESS_MIDDLE_RATIO)
        space6.setHeightByRatio(mContext, PROGRESS_BOTTOM_RATIO)

        space7.setHeightByRatio(mContext, TITLE_SPACE_RATIO)
        space8.setHeightByRatio(mContext, TITLE_SPACE_RATIO)

        stampSpace.setHeightByRatio(mContext, TITLE_SPACE_RATIO)
        messageEmptyLayout.setHeightByRatio(mContext, MESSAGE_EMPTY_LAYOUT_RATIO)
        messageSpace.setHeightByRatio(mContext, MESSAGE_SPACE_RATIO)
    }

    private fun setCharmEditDialogClickListener() {
        mDialog = CharmEditDialog()

        mDialog?.onBottomDialogListener = object : OnBottomDialogListener {
            override fun onItemClick(item: BottomDialogItem) {
                Log.d(TAG, "Charm Edit Bottom Dialog click: $item")

                when (item) {
                    CharmEditDialogItem.EDIT -> {

                    }

                    CharmEditDialogItem.IMAGE_DOWNLOAD -> {

                    }

                    CharmEditDialogItem.DELETE -> {

                    }

                    CharmEditDialogItem.CANCEL -> {
                        mDialog?.dismiss()
                    }
                }
            }
        }
    }

    private fun setToolbarAction() = with(binding.toolbar) {
        setButtonOnClickListener(Toolbar.BACK_BUTTON) { finish() }

        visibleButton(Toolbar.MEATBALL_BUTTON)
        setButtonOnClickListener(Toolbar.MEATBALL_BUTTON) {
            mDialog?.show(supportFragmentManager, DIALOG_TAG)
        }

        binding.scrollLayout.setOnScrollChangeListener { _, _, _, _, _ ->
            val charmTitleHeight = IntArray(2).run {
                binding.charmTitle.getLocationOnScreen(this)
                this[1]
            }

            setTitleText(
                if (charmTitleHeight - height < 0) mCharmTitle
                else ""
            )
        }

        setTitleOnClickListener {
            binding.scrollLayout.fullScroll(NestedScrollView.FOCUS_UP)
        }
    }

    private fun setupStampList() {
        val backgroundColor = ColorUtil.getColor(mContext, mCharmType.color.background)
        binding.stampLayout.setBackgroundColor(backgroundColor)

        val charmStamps = listOf(
            CharmStamp("23. 10.1"),
            CharmStamp("23. 10.2"),
            CharmStamp("23. 10.3"),
            CharmStamp("23. 10.4")
        )
        val fillCharmStamps = charmStamps + List(WEEK * 3 - charmStamps.size) { CharmStamp() }

        var week = 0
        mStampWeeks.forEach { stampWeek ->
            stampWeek.setAdapter(fillCharmStamps.slice(week * WEEK until ++week * WEEK), mCharmType)
        }
    }

    private fun setupMessageList() = with(binding.messageRecyclerView) {
        val charmMessages = listOf(
            CharmMessage("", "김혜민", "테스트", "2024.01.10", false),
            CharmMessage("", "김혜민", "테스트", "2024.01.10", false),
            CharmMessage("", "김혜민", "테스트", "2024.01.10", true),
            CharmMessage(
                "",
                "김혜민",
                "글자수테스트글자수테스트글자수테스트글자수테스트글자수테스트글자수테스트글자수테스트글자수테스트글자수테스트글자수테스트글자수테스트글자수테스트글자수테스트글자수테스트글자수테스트글자수테스트글자수테스트글자수테스트",
                "2024.01.10",
                true
            ),
            CharmMessage(
                "",
                "김혜민",
                "글자수 테스트 글자수 테스트 글자수 테스트 글자수 테스트 글자수테스트글자수테스트글자수테스트글자수테스트글자수테스트글자수테스트글자수테스트글자수테스트글자수테스트글자수테스트글자수테스트글자수테스트글자수테스트글자수테스트",
                "2024.01.10",
                false
            )
        )

        if (charmMessages.isEmpty()) {
            visibility = View.GONE
            binding.messageEmptyLayout.visibility = View.VISIBLE
            return@with
        }

        val backgroundColor = ColorUtil.getColor(mContext, mCharmType.color.subBackground)
        val textColor = ColorUtil.getColor(mContext, mCharmType.color.subText)

        adapter = CharmMessageListAdapter(charmMessages, backgroundColor, textColor)
    }

    fun setBackButtonOnClickListener(onClickListener: (View) -> Unit) {
        binding.toolbar.setButtonOnClickListener(Toolbar.BACK_BUTTON, onClickListener)
    }

    private fun startRecordFragment() {
        mRecordFragment = mRecordFragment ?: CharmRecordFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.record_fragment, mRecordFragment!!)
            .commit()

        binding.scrollLayout.scrollable = false
        mToolbarTitle = binding.toolbar.title
        binding.toolbar.title = ""
    }

    fun finishFragment() {
        mRecordFragment ?: return
        supportFragmentManager
            .beginTransaction()
            .remove(mRecordFragment!!)
            .commit()
        mRecordFragment = null

        binding.toolbar.setButtonOnClickListener(Toolbar.BACK_BUTTON) { finish() }
        binding.scrollLayout.scrollable = true
        binding.toolbar.title = mToolbarTitle
    }

    fun finishRecord(selectedEmotionType: EmotionType, recordMessage: String?) {
        finishFragment()
        // TODO: (스탬프 레이아웃에 반영, 서버 통신)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        mDialog = null
    }

    companion object {
        private val TAG = CharmDetailActivity::class.simpleName
        private const val DIALOG_TAG = "dialog"

        private const val CHARM_TITLE_TOP_RATIO = 16
        private const val CHARM_TITLE_MIDDLE_RATIO = 10
        private const val CHARM_TITLE_BOTTOM_RATIO = 20

        private const val PROGRESS_TOP_RATIO = 20
        private const val PROGRESS_MIDDLE_RATIO = 8
        private const val PROGRESS_BOTTOM_RATIO = 46

        private const val TITLE_SPACE_RATIO = 48
        private const val MESSAGE_EMPTY_LAYOUT_RATIO = 200
        private const val MESSAGE_SPACE_RATIO = 152

        private const val WEEK = 7
    }
}