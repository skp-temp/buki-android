package com.example.skptemp.common.ui.component

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.skptemp.R
import com.example.skptemp.common.constants.CharmType
import com.example.skptemp.common.util.ColorUtil
import com.example.skptemp.common.util.ViewUtil.convertDPtoPX
import com.example.skptemp.databinding.CharmTypeTagBinding

class CharmTypeTag @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleArr: Int = 0,
) : LinearLayout(context, attrs, defStyleArr) {

    private val binding by lazy {
        CharmTypeTagBinding.inflate(LayoutInflater.from(context), this, true)
    }

    private val mRectangleBackground = GradientDrawable().apply {
        cornerRadius = BACKGROUND_RADIUS_DP.convertDPtoPX(context)
    }

    // styleAttr, styleRes, defStyleAttr, defStyleRes
    private val mTypedArray = context.theme.obtainStyledAttributes(
        attrs, R.styleable.CharmTypeTag, 0, 0
    )

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        initializeViewAttrs(mTypedArray)
    }

    private fun initializeViewAttrs(typedArray: TypedArray): Unit = with(typedArray) {
        val isSmall = getBoolean(R.styleable.CharmTypeTag_small, false)
        if (isSmall) setBadgeSmallSize()

        val isIncompleteType = getBoolean(R.styleable.CharmTypeTag_incomplete, false)
        if (!isIncompleteType) return
        setIncompleteTag()
    }

    private fun setBadgeSmallSize() = with(binding) {
        rectangle.layoutParams = rectangle.layoutParams.apply {
            height = SMALL_HEIGHT_DP.convertDPtoPX(context).toInt()
        }
        title.setTextSize(TypedValue.COMPLEX_UNIT_DIP, SMALL_TEXT_SIZE_DP);
    }

    private fun setIncompleteTag() {
        setTagBackgroundColor(INCOMPLETE_BACKGROUND_COLOR)
        setTagTextColor(INCOMPLETE_TEXT_COLOR)
        setTagTitle(INCOMPLETE_TITLE)
    }

    fun setTagType(type: CharmType) = with(binding) {
        setTagBackgroundColor(type.subBackgroundColor)
        setTagTextColor(type.textColor)
        setTagTitle(type.title)
    }

    private fun setTagBackgroundColor(color: Int) {
        binding.rectangle.background = mRectangleBackground.apply {
            setColor(ColorUtil.getColor(context, color))
        }
    }

    private fun setTagTextColor(color: Int) =
        binding.title.setTextColor(ColorUtil.getColor(context, color))

    private fun setTagTitle(text: String) {
        binding.title.text = text
    }

    companion object {
        private const val BACKGROUND_RADIUS_DP = 4f

        private const val SMALL_TEXT_SIZE_DP = 10f
        private const val SMALL_HEIGHT_DP = 16f

        private val INCOMPLETE_BACKGROUND_COLOR = R.color.gray_300
        private val INCOMPLETE_TEXT_COLOR = R.color.gray_500
        private const val INCOMPLETE_TITLE = "미완료"
    }
}