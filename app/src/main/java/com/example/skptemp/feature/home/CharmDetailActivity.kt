package com.example.skptemp.feature.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.skptemp.R
import com.example.skptemp.common.constants.CharmType
import com.example.skptemp.common.ui.GridRecyclerViewItemDecoration
import com.example.skptemp.common.ui.component.StampWeek
import com.example.skptemp.common.ui.inf.BottomDialogItem
import com.example.skptemp.common.ui.inf.OnBottomDialogListener
import com.example.skptemp.common.ui.component.Toolbar
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
    private lateinit var mCharmType: CharmType
    private lateinit var mStampWeeks: List<StampWeek>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityCharmDetailBinding.inflate(layoutInflater)

        mCharmType = CharmType.WORKOUT // TODO: home_fragment에서 bundle 값 가져오기
        mStampWeeks = listOf(binding.week1, binding.week2, binding.week3)

        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()

        composeUI()
    }

    private fun composeUI() = with(binding) {
        charmTypeTag.setTagType(mCharmType)

        setCharmEditDialogClickListener()

        toolbar.visibleButton(Toolbar.MEATBALL_BUTTON)
        toolbar.setButtonOnClickListener(Toolbar.MEATBALL_BUTTON) {
            if (mDialog?.isAdded == true) {
                supportFragmentManager.beginTransaction().remove(mDialog!!)
                mDialog = CharmEditDialog()
            }

            mDialog?.show(supportFragmentManager, DIALOG_TAG)
        }

        setLayoutHeightByRatio()
        setupStampList()
        setupMessageList()
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

    private fun setLayoutHeightByRatio() = with(binding) {
        charmTitleLayout.setHeightByRatio(mContext, CHARM_TITLE_LAYOUT_RATIO)
        charmProgressLayout.setHeightByRatio(mContext, CHARM_PROGRESS_LAYOUT_RATIO)
        stampTitleLayout.setHeightByRatio(mContext, TITLE_LAYOUT_RATIO)
        stampSpace.setHeightByRatio(mContext, STAMP_SPACE_RATIO)
        messageTitleLayout.setHeightByRatio(mContext, TITLE_LAYOUT_RATIO)
        messageSpace.setHeightByRatio(mContext, MESSAGE_SPACE_RATIO)
    }

    private fun setupStampList() {
        val backgroundColor = ColorUtil.getColor(mContext, mCharmType.backgroundColor)
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
        val messageBackgroundColor = ColorUtil.getColor(mContext, mCharmType.subBackgroundColor)
        adapter =
            CharmMessageListAdapter(
                listOf(
                    CharmMessage("", "김혜민", "테스트", "2024.01.26"),
                    CharmMessage("", "김혜민", "테스트", "2024.01.26"),
                    CharmMessage("", "김혜민", "테스트", "2024.01.26"),
                    CharmMessage(
                        "",
                        "김혜민",
                        "글자수테스트글자수테스트글자수테스트글자수테스트글자수테스트글자수테스트글자수테스트글자수테스트글자수테스트글자수테스트글자수테스트글자수테스트글자수테스트",
                        "2024.01.26"
                    ),
                    CharmMessage(
                        "",
                        "김혜민",
                        "글자수테스트글자수테스트글자수테스트글자수테스트글자수테스트글자수테스트글자수테스트글자수테스트글자수테스트글자수테스트글자수테스트글자수테스트글자수테스트",
                        "2024.01.26"
                    )
                ),
                messageBackgroundColor
            )

        addItemDecoration(
            GridRecyclerViewItemDecoration(
                spanCount = 2,
                topSpaceId = R.dimen.message_list_top_space,
                rowSpaceId = R.dimen.message_list_row_space,
                colSpaceId = R.dimen.message_list_col_space,
                context = mContext
            )
        )

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        mDialog = null
    }

    companion object {
        private val TAG = CharmDetailActivity::class.simpleName
        private const val DIALOG_TAG = "dialog"

        private const val CHARM_TITLE_LAYOUT_RATIO = 88
        private const val CHARM_PROGRESS_LAYOUT_RATIO = 98
        private const val TITLE_LAYOUT_RATIO = 72
        private const val STAMP_SPACE_RATIO = 48
        private const val MESSAGE_SPACE_RATIO = 152

        private const val WEEK = 7
    }
}