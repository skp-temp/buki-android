package com.example.skptemp.common.util

import android.content.Context
import android.content.res.ColorStateList
import androidx.core.content.ContextCompat

object ColorUtil {
    fun getColor(context: Context, colorId: Int) =
        ContextCompat.getColor(context, colorId)

    fun getColorStateList(context: Context, colorId: Int) =
        ColorStateList.valueOf(getColor(context, colorId))
}