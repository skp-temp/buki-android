package com.example.skptemp.common.ui.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.skptemp.R
import com.example.skptemp.common.constants.CharmType
import com.example.skptemp.common.constants.EmotionType
import com.example.skptemp.common.util.ViewUtil.getDeviceWidthPx
import com.example.skptemp.databinding.StampWeekBinding
import com.example.skptemp.feature.home.adapter.CharmStampListAdapter
import com.example.skptemp.model.CharmStamp

class StampWeek @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleArr: Int = 0,
) : LinearLayout(context, attrs, defStyleArr) {

    private val binding by lazy {
        StampWeekBinding.inflate(LayoutInflater.from(context), this, true)
    }

    private var mFirstAdapter: CharmStampListAdapter? = null
    private var mSecondAdapter: CharmStampListAdapter? = null

    fun setAdapter(activity: AppCompatActivity, list: List<CharmStamp>, charmType: CharmType) {
        val deviceWidthPx = context.getDeviceWidthPx()
        val widthPx =
            deviceWidthPx * (resources.getDimensionPixelOffset(R.dimen.stamp_item_width) /
                    resources.getDimension(R.dimen.guide_width))

        mFirstAdapter = CharmStampListAdapter(
            list.slice(FIRST_RANGE).toMutableList(),
            activity,
            charmType,
            widthPx.toInt()
        )
        binding.first.adapter = mFirstAdapter

        mSecondAdapter = CharmStampListAdapter(
            list.slice(SECOND_RANGE).toMutableList(),
            activity,
            charmType,
            widthPx.toInt()
        )
        binding.second.adapter = mSecondAdapter
    }

    fun updateStampRecord(
        position: Int,
        selectedEmotionType: EmotionType,
        recordMessage: String?
    ) {
        val adapter = if (position in FIRST_RANGE) mFirstAdapter else mSecondAdapter
        val pos = if (position in FIRST_RANGE) position else position % FIRST_LIST

        adapter?.updateItem(pos, selectedEmotionType, recordMessage)
    }

    companion object {
        private const val WEEK = 7
        private const val FIRST_LIST = 3
        private val FIRST_RANGE = 0 until FIRST_LIST
        private val SECOND_RANGE = FIRST_LIST until WEEK
    }
}