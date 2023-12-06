package com.example.skptemp.common.util

import android.content.Context
import android.util.DisplayMetrics
import kotlin.math.round

object ViewUtil {
    fun convertPXtoDP(context: Context, px: Int): Int {
        val metrics = context.resources.displayMetrics
        return round(px / (metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT.toFloat()))
            .toInt()
    }

    fun convertDPtoPX(context: Context, dp: Int): Int {
        val density = context.resources.displayMetrics.density
        return round(dp * density).toInt()
    }
}