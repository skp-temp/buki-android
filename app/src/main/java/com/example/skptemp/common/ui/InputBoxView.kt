package com.example.skptemp.common.ui

import android.content.Context
import android.content.res.TypedArray
import android.os.Build
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.example.skptemp.R
import com.example.skptemp.databinding.InputBoxBinding

class InputBoxView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleArr: Int = 0,
) : LinearLayout(context, attrs, defStyleArr) {

    private var _binding: InputBoxBinding? = null
    private val binding get() = _binding!!

    private var mIsCorrectInput = true

    // styleAttr, styleRes, defStyleAttr, defStyleRes
    private val mTypedArray = context.theme.obtainStyledAttributes(
        attrs, R.styleable.InputBoxView, 0, 0
    )

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        _binding = InputBoxBinding.inflate(LayoutInflater.from(context), this, true)

        initializeViewAttrs(mTypedArray)
        addOnClickListenerClearButton()
    }

    private fun initializeViewAttrs(typedArray: TypedArray) = with(typedArray) {
        getString(R.styleable.InputBoxView_hint)?.let { hint ->
            binding.editText.hint = hint
        }
        getInt(R.styleable.InputBoxView_maxLength, 4).let { maxLength ->
            binding.editText.filters = arrayOf(InputFilter.LengthFilter(maxLength))
        }
    }

    private fun addOnClickListenerClearButton() = with(binding) {
        clearButton.setOnClickListener {
            editText.text.clear()
        }
    }

    fun addOnInputFocusListener(inputFocusListener: OnInputFocusListener) = with(binding.editText) {
        setOnFocusChangeListener { _, hasFocus ->
            val isEmpty = text.isEmpty()
            inputFocusListener.onChangeFocus(hasFocus, isEmpty)

            if (!hasFocus) {
                setColor(DEFAULT)
                return@setOnFocusChangeListener
            }

            if (mIsCorrectInput || isEmpty) {
                setColor(SEMANTIC_BLUE)
                return@setOnFocusChangeListener
            }

            setColor(SEMANTIC_RED, CURSOR_RED)
        }
    }

    private fun setColor(lineColorId: Int, cursorColorId: Int = CURSOR_BLUE) {
        setLineColor(lineColorId)
        setCursorColor(cursorColorId)
    }

    private fun setLineColor(colorId: Int) = with(binding) {
        ContextCompat.getColor(context, colorId).let { color ->
            lineLeft.setColorFilter(color)
            lineMiddle.setColorFilter(color)
            lineRight.setColorFilter(color)
        }
    }

    private fun setCursorColor(colorId: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            binding.editText.textCursorDrawable = ContextCompat.getDrawable(context, colorId)
        }
    }

    fun addOnInputRegexListener(
        regex: Regex,
        inputRegexListener: OnInputRegexListener
    ) {
        binding.editText.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val isEmpty = s.isEmpty()
                setVisibilityClearButton(isEmpty)

                mIsCorrectInput = s.matches(regex)
                inputRegexListener.onChangeCorrect(mIsCorrectInput, isEmpty)

                if (mIsCorrectInput || isEmpty) {
                    setColor(SEMANTIC_BLUE)
                    return
                }

                setColor(SEMANTIC_RED, CURSOR_RED)
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(editable: Editable) {}
        })
    }

    private fun setVisibilityClearButton(isEmpty: Boolean) {
        binding.clearButton.visibility = if (isEmpty) GONE else VISIBLE
    }

    fun outOfFocus() = binding.editText.clearFocus()

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        _binding = null
    }

    companion object {
        private val DEFAULT = R.color.gray_300
        private val SEMANTIC_BLUE = R.color.blue
        private val SEMANTIC_RED = R.color.red

        private val CURSOR_BLUE = R.drawable.cursor_color_blue
        private val CURSOR_RED = R.drawable.cursor_color_red
    }
}