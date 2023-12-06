package com.example.skptemp.common.util

import android.content.Context
import android.util.DisplayMetrics
import kotlin.math.round

object ViewUtil {
    fun Float.convertPXtoDP(context: Context): Float {
        val metrics = context.resources.displayMetrics
        return round(this / (metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT.toFloat()))
    }

    fun Float.convertDPtoPX(context: Context): Float {
        val density = context.resources.displayMetrics.density
        return round(this * density)
    }
}