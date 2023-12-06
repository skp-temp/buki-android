package com.example.skptemp.common.ui

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.skptemp.R
import com.example.skptemp.databinding.ToolbarBinding
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class Toolbar @Inject constructor(@ActivityContext context: Context) : ConstraintLayout(context) {

    private val binding = ToolbarBinding.inflate(LayoutInflater.from(context), this, true)
    private val mButtonMap = mapOf(
        BACK_BUTTON to binding.backButton,
        BELL_BUTTON to binding.bellButton,
        MEATBALL_BUTTON to binding.meatballButton,
        ACTION_BUTTON to binding.actionButton
    )

    fun setTitleCenter(gravity: Int) = with(binding) {
        title.gravity = Gravity.CENTER_VERTICAL or gravity
        title.textSize = resources.getDimension(R.dimen.text_medium_size)
    }

    fun setTitleText(text: String) {
        binding.title.text = text
    }

    fun visibleButton(buttonType: Int) = mButtonMap[buttonType]?.let { button ->
        button.visibility = View.VISIBLE

        when (buttonType) {
            // 백 버튼과 타이틀이 연속적으로 있는 경우 마진 설정
            BACK_BUTTON -> {
                if (binding.title.gravity == Gravity.CENTER) return@let

                val marginDp = resources.getDimension(R.dimen.toolbar_title_margin) +
                        resources.getDimension(R.dimen.toolbar_button)
                binding.title.layoutParams =
                    (binding.title.layoutParams as MarginLayoutParams).apply {
                        marginStart = marginDp.toInt()
                    }
            }

            // 벨 버튼과 미트볼 버튼이 연속적으로 있는 경우 마진 설정
            BELL_BUTTON -> {
                if (mButtonMap[MEATBALL_BUTTON]?.visibility == View.VISIBLE) return@let

                button.layoutParams = (button.layoutParams as MarginLayoutParams).apply {
                    marginEnd = 0
                }
            }

            MEATBALL_BUTTON -> {
                if (mButtonMap[BELL_BUTTON]?.visibility == View.INVISIBLE) return@let

                button.layoutParams = (button.layoutParams as MarginLayoutParams).apply {
                    marginStart = resources.getDimension(R.dimen.toolbar_button_margin).toInt()
                }
            }

            else -> return@let
        }
    }

    fun setButtonOnClickListener(buttonType: Int, listener: (View) -> Unit) {
        mButtonMap[buttonType]?.setOnClickListener(listener)
    }

    companion object {
        const val BACK_BUTTON = 1
        const val BELL_BUTTON = 2
        const val MEATBALL_BUTTON = 3
        const val ACTION_BUTTON = 4
    }
}