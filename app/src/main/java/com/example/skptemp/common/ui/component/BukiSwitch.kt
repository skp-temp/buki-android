package com.example.skptemp.common.ui.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.CompoundButton
import android.widget.FrameLayout
import com.example.skptemp.R
import com.example.skptemp.common.util.ColorUtil
import com.example.skptemp.databinding.BukiSwitchBinding

class BukiSwitch @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleArr: Int = 0,
) : FrameLayout(context, attrs, defStyleArr) {

    private val binding by lazy {
        BukiSwitchBinding.inflate(LayoutInflater.from(context), this, true)
    }

    var off: String = ""
        set(value) {
            field = value
            binding.off.text = value
        }

    var on: String = ""
        set(value) {
            field = value
            binding.on.text = value
        }

    val isChecked get() = binding.bukiSwitch.isChecked

    private val mCheckedTextColor by lazy { ColorUtil.getColor(context, CHECKED_TEXT_COLOR) }
    private val mUncheckedTextColor by lazy { ColorUtil.getColor(context, UNCHECKED_TEXT_COLOR) }

    fun updateSwitchTextColor(isChecked: Boolean) = with(binding) {
        if (isChecked) {
            on.setTextColor(mCheckedTextColor)
            off.setTextColor(mUncheckedTextColor)
            return
        }

        off.setTextColor(mCheckedTextColor)
        on.setTextColor(mUncheckedTextColor)
    }

    fun setOnCheckedChangeListener(action: () -> Unit) {
        binding.bukiSwitch.setOnCheckedChangeListener { _, isChecked ->
            updateSwitchTextColor(isChecked)
            action()
        }
    }

    companion object {
        private val CHECKED_TEXT_COLOR = R.color.white
        private val UNCHECKED_TEXT_COLOR = R.color.gray_500
    }
}