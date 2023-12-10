package com.example.skptemp.common.ui

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.example.skptemp.R
import com.example.skptemp.databinding.LargeButtonBinding

class LargeButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleArr: Int = 0,
) : LinearLayout(context, attrs, defStyleArr) {

    private var _binding: LargeButtonBinding? = null
    private val binding get() = _binding!!

    // styleAttr, styleRes, defStyleAttr, defStyleRes
    private val mTypedArray = context.theme.obtainStyledAttributes(
        attrs, R.styleable.LargeButton, 0, 0
    )

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        _binding = LargeButtonBinding.inflate(LayoutInflater.from(context), this, true)

        initializeViewAttrs(mTypedArray)
        setColor()
    }

    private fun initializeViewAttrs(typedArray: TypedArray) = with(typedArray) {
        getString(R.styleable.LargeButton_text)?.let { text ->
            binding.textView.text = text
        }
    }

    // 버튼 활성화/비활성화 될 때마다 컬러 변경
    fun setEnabledButton(isEnabled: Boolean) {
        this.isEnabled = isEnabled
        setColor()
    }

    private fun setColor() {
        setButtonColor(if (isEnabled) ENABLED_BUTTON else DISABLED_BUTTON)
        setTextColor(if (isEnabled) ENABLED_TEXT else DISABLED_TEXT)
    }

    private fun setButtonColor(colorId: Int) = with(binding) {
        val color = getColor(colorId)
        left.setColorFilter(color)
        middle.setColorFilter(color)
        right.setColorFilter(color)
    }

    private fun setTextColor(colorId: Int) {
        val color = getColor(colorId)
        binding.textView.setTextColor(color)
    }

    private fun getColor(colorId: Int) = ContextCompat.getColor(context, colorId)

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        _binding = null
    }

    companion object {
        private val ENABLED_BUTTON = R.color.gray_900
        private val ENABLED_TEXT = R.color.white
        private val DISABLED_BUTTON = R.color.gray_300
        private val DISABLED_TEXT = R.color.gray_400
    }
}