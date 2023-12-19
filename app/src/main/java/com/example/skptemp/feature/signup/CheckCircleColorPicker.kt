package com.example.skptemp.feature.signup

import android.content.Context
import android.widget.ImageView
import com.example.skptemp.R
import com.example.skptemp.common.util.ColorUtil.getColorStateList

object CheckCircleColorPicker {
    fun ImageView.setChecked(context: Context, flag: Boolean) {
        val colorId = if (flag) CHECKED else UNCHECKED
        imageTintList = getColorStateList(context, colorId)
    }

    private val CHECKED = R.color.blue
    private val UNCHECKED = R.color.gray_400
}