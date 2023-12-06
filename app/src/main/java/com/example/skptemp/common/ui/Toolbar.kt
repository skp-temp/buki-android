package com.example.skptemp.common.ui

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.skptemp.R
import com.example.skptemp.common.util.ViewUtil.convertPXtoDP
import com.example.skptemp.databinding.ToolbarBinding

class Toolbar @JvmOverloads constructor(
    private val context: Context,
    attrs: AttributeSet? = null,
    defStyleArr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleArr) {

    private val binding = ToolbarBinding.inflate(LayoutInflater.from(context), this, true)
    private val mButtonMap = mapOf(
        BACK_BUTTON to binding.backButton,
        BELL_BUTTON to binding.bellButton,
        MEATBALL_BUTTON to binding.meatballButton,
        ACTION_BUTTON to binding.actionButton
    )

    init {
        val typedArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.Toolbar,
            0,
            0
        )

        initializeView(typedArray)
    }

    private fun initializeView(typedArray: TypedArray) = with(typedArray) {
        getString(R.styleable.Toolbar_title_text)?.let { titleText ->
            setTitleText(titleText)
        }

        getBoolean(R.styleable.Toolbar_title_center, false).let { visibility ->
            if (!visibility) return@let
            binding.title.setGravityCenter()
        }

        getBoolean(R.styleable.Toolbar_back_button_visibility, false).let { visibility ->
            if (!visibility) return@let
            visibleButton(BACK_BUTTON)
        }
    }

    private fun TextView.setGravityCenter() {
        gravity = Gravity.CENTER
        textSize = resources.getDimension(R.dimen.text_medium_size).convertPXtoDP(context)
    }

    fun setTitleText(text: String) {
        binding.title.text = text
    }

    fun visibleButton(buttonType: Int) = with(binding) {
        mButtonMap[buttonType]?.visibility = View.VISIBLE

        when (buttonType) {
            // 백 버튼과 타이틀이 연속적으로 있는 경우 마진 설정
            BACK_BUTTON -> {
                if (title.gravity == Gravity.CENTER) {
                    return@with
                }

                val marginDp = resources.getDimension(R.dimen.toolbar_title_margin) +
                        resources.getDimension(R.dimen.toolbar_button)
                title.setMarginStart(marginDp.toInt())
            }

            // 벨 버튼과 미트볼 버튼이 연속적으로 있는 경우 마진 설정
            BELL_BUTTON, MEATBALL_BUTTON -> {
                if (mButtonMap[MEATBALL_BUTTON]?.visibility == View.INVISIBLE ||
                    mButtonMap[BELL_BUTTON]?.visibility == View.INVISIBLE) {
                    return@with
                }

                val marginDp = resources.getDimension(R.dimen.toolbar_button_margin).toInt()
                meatballButton.setMarginStart(marginDp)
            }

            else -> return@with
        }
    }

    fun setButtonOnClickListener(buttonType: Int, listener: (View) -> Unit) {
        mButtonMap[buttonType]?.setOnClickListener(listener)
    }

    private fun View.setMarginStart(marginDp: Int) {
        layoutParams = (layoutParams as MarginLayoutParams).apply {
            marginStart = marginDp
        }
    }

    companion object {
        const val BACK_BUTTON = 1
        const val BELL_BUTTON = 2
        const val MEATBALL_BUTTON = 3
        const val ACTION_BUTTON = 4
    }
}