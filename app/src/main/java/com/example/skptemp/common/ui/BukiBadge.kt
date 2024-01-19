package com.example.skptemp.common.ui

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.skptemp.R
import com.example.skptemp.common.constants.BukiBadgeType
import com.example.skptemp.common.util.ColorUtil
import com.example.skptemp.common.util.ViewUtil.convertDPtoPX
import com.example.skptemp.databinding.BukiBadgeBinding

class BukiBadge @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleArr: Int = 0,
) : LinearLayout(context, attrs, defStyleArr) {

    private val binding by lazy {
        BukiBadgeBinding.inflate(LayoutInflater.from(context), this, true)
    }

    private val mRectangleBackground = GradientDrawable().apply {
        cornerRadius = BACKGROUND_RADIUS_DP.convertDPtoPX(context)
    }

    // styleAttr, styleRes, defStyleAttr, defStyleRes
    private val mTypedArray = context.theme.obtainStyledAttributes(
        attrs, R.styleable.BukiBadge, 0, 0
    )

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        initializeViewAttrs(mTypedArray)
    }

    private fun initializeViewAttrs(typedArray: TypedArray): Unit = with(typedArray) {
        val isSmall = getBoolean(R.styleable.BukiBadge_small, false)
        if (isSmall) setBadgeSmallSize()

        val isIncompleteType = getBoolean(R.styleable.BukiBadge_incomplete, false)
        if (!isIncompleteType) return
        setBadgeType(BukiBadgeType.INCOMPLETE)
    }

    private fun setBadgeSmallSize() = with(binding) {
        rectangle.layoutParams = rectangle.layoutParams.apply {
            height = SMALL_HEIGHT_DP.convertDPtoPX(context).toInt()
        }
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, SMALL_TEXT_SIZE_DP);
    }

    fun setBadgeType(type: BukiBadgeType) = with(binding) {
        rectangle.background = mRectangleBackground.apply {
            setColor(ColorUtil.getColor(context, type.backgroundColor))
        }
        textView.setTextColor(ColorUtil.getColor(context, type.textColor))
        textView.text = type.title
    }

    companion object {
        private const val BACKGROUND_RADIUS_DP = 4f

        private const val SMALL = "small"
        private const val SMALL_TEXT_SIZE_DP = 10f
        private const val SMALL_HEIGHT_DP = 16f
    }
}