package com.example.skptemp.common.ui.component

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.example.skptemp.R
import com.example.skptemp.common.ui.setOnSingleClickListener
import com.example.skptemp.common.util.ViewUtil.convertPXtoDP
import com.example.skptemp.databinding.ToolbarBinding

class Toolbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleArr: Int = 0,
) : FrameLayout(context, attrs, defStyleArr) {

    private val binding = ToolbarBinding.inflate(LayoutInflater.from(context), this, true)

    // styleAttr, styleRes, defStyleAttr, defStyleRes
    private val mTypedArray = context.theme.obtainStyledAttributes(
        attrs, R.styleable.Toolbar, 0, 0
    )

    private val mButtonMap by lazy {
        mapOf(
            BACK_BUTTON to binding.backButton,
            ACTION_BUTTON_LEFT to binding.actionButtonLeft,
            BELL_BUTTON to binding.bellButton,
            MEATBALL_BUTTON to binding.meatballButton,
            GIFT_BUTTON to binding.giftButton,
            ACTION_BUTTON_RIGHT to binding.actionButtonRight,
        )
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        initializeViewAttrs(mTypedArray)
    }

    private fun initializeViewAttrs(typedArray: TypedArray) = with(typedArray) {
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
        getBoolean(R.styleable.Toolbar_action_button_left_visibility, false).let { visibility ->
            if (!visibility) return@let
            visibleButton(ACTION_BUTTON_LEFT)
        }
        getBoolean(R.styleable.Toolbar_action_button_right_visibility, false).let { visibility ->
            if (!visibility) return@let
            visibleButton(ACTION_BUTTON_RIGHT)
        }
        getString(R.styleable.Toolbar_action_button_left_text)?.let { titleText ->
            binding.actionButtonLeft.text = titleText
        }
        getString(R.styleable.Toolbar_action_button_right_text)?.let { titleText ->
            binding.actionButtonRight.text = titleText
        }
    }

    private fun TextView.setGravityCenter() {
        layoutParams = LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.MATCH_PARENT
        ).apply {
            gravity = Gravity.CENTER_HORIZONTAL
        }

        setTextSize(
            TypedValue.COMPLEX_UNIT_DIP,
            resources.getDimension(R.dimen.text_medium_size).convertPXtoDP(context)
        )
    }

    fun setTitleText(text: String) {
        binding.title.text = if (text.length <= TITLE_MAX_LENGTH) text
        else text.replaceRange(TITLE_MAX_LENGTH until text.length, TITLE_REPLACEMENT)
    }

    // 툴바 오른쪽 버튼 순서 -> GIFT / BELL / MEATBALL
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
            GIFT_BUTTON -> {
                if (mButtonMap[BELL_BUTTON]?.visibility == View.INVISIBLE) {
                    return@with
                }

                val marginDp = resources.getDimension(R.dimen.toolbar_button_margin).toInt()
                bellButton.setMarginStart(marginDp)
            }

            // 벨 버튼과 미트볼 버튼이 연속적으로 있는 경우 마진 설정
            BELL_BUTTON -> {
                if (mButtonMap[MEATBALL_BUTTON]?.visibility == View.INVISIBLE) {
                    return@with
                }

                val marginDp = resources.getDimension(R.dimen.toolbar_button_margin).toInt()
                meatballButton.setMarginStart(marginDp)
            }

            else -> return@with
        }
    }

    fun setButtonOnClickListener(buttonType: Int, listener: (View) -> Unit) {
        mButtonMap[buttonType]?.setOnSingleClickListener(listener)
    }

    fun setTitleOnClickListener(listener: (View) -> Unit) {
        binding.title.setOnClickListener(listener)
    }

    private fun View.setMarginStart(marginDp: Int) {
        layoutParams = (layoutParams as MarginLayoutParams).apply {
            marginStart = marginDp
        }
    }

    companion object {
        private const val TITLE_MAX_LENGTH = 12
        private const val TITLE_REPLACEMENT = "..."

        const val BACK_BUTTON = 1
        const val ACTION_BUTTON_LEFT = 2
        const val BELL_BUTTON = 3
        const val MEATBALL_BUTTON = 4
        const val GIFT_BUTTON = 5
        const val ACTION_BUTTON_RIGHT = 6
    }
}