package com.example.skptemp.common.ui.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.skptemp.R
import com.example.skptemp.common.constants.CharmType
import com.example.skptemp.common.util.ColorUtil
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

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
    }

    fun setAdapter(list: List<CharmStamp>, charmType: CharmType) {
        val deviceWidthPx = context.getDeviceWidthPx()
        val widthPx =
            deviceWidthPx * (resources.getDimensionPixelOffset(R.dimen.stamp_item_width) /
                    resources.getDimension(R.dimen.guide_width))

        val stampDateColor = ColorUtil.getColor(context, charmType.color.subText)

        binding.first.adapter =
            CharmStampListAdapter(
                list.slice(0 until FIRST_LIST),
                charmType,
                widthPx.toInt(),
                stampDateColor
            )

        binding.second.adapter =
            CharmStampListAdapter(
                list.slice(FIRST_LIST until WEEK),
                charmType,
                widthPx.toInt(),
                stampDateColor
            )
    }

    companion object {
        private const val WEEK = 7
        private const val FIRST_LIST = 3
    }
}