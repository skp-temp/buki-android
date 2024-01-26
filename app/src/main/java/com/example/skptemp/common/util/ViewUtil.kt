package com.example.skptemp.common.util

import android.content.Context
import android.util.DisplayMetrics
import android.view.View
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

    fun Context.getDeviceWidthPx() =
        applicationContext.resources.displayMetrics.widthPixels

    // device height - navigation bar - status bar
    fun Context.getDeviceHeightPx() =
        applicationContext.resources.displayMetrics.heightPixels - getNavigationBarHeightPx()

    fun Context.getStatusBarHeightPx(): Int {
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        return resources.getDimensionPixelSize(resourceId)
    }

    private fun Context.getNavigationBarHeightPx(): Int {
        val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
        return resources.getDimensionPixelSize(resourceId)
    }

    fun View.setWidthPx(widthPx: Int) {
        layoutParams = layoutParams.apply {
            width = widthPx
        }
    }

    fun View.setHeightPx(heightPx: Int) {
        layoutParams = layoutParams.apply {
            height = heightPx
        }
    }

}