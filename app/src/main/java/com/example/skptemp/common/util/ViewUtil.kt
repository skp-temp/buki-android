package com.example.skptemp.common.util

import android.content.Context
import android.util.DisplayMetrics
import android.view.View
import com.example.skptemp.R
import com.example.skptemp.common.util.ViewUtil.setHeightPx
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
    private fun Context.getDeviceHeightPx() =
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

    fun View.setWidthByRatio(context: Context, widthDp: Int) {
        val deviceWidthPx = context.getDeviceWidthPx()
        val widthPx = widthDp.toFloat().convertDPtoPX(context)
        val guideWidthPx = resources.getDimension(R.dimen.guide_width)

        setWidthPx((deviceWidthPx * (widthPx / guideWidthPx)).toInt())
    }

    fun View.setHeightByRatio(context: Context, heightDp: Int) {
        val deviceHeightPx = context.getDeviceHeightPx()
        val heightPx = heightDp.toFloat().convertDPtoPX(context)
        val guideHeightPx = resources.getDimension(R.dimen.guide_height)

        setHeightPx((deviceHeightPx * (heightPx / guideHeightPx)).toInt())
    }

    fun getWidthPxByRatio(context: Context, widthDp: Int): Int {
        val deviceWidthPx = context.getDeviceWidthPx()
        val widthPx = widthDp.toFloat().convertDPtoPX(context)
        val guideWidthPx = context.resources.getDimension(R.dimen.guide_width)

        return (deviceWidthPx * (widthPx / guideWidthPx)).toInt()
    }

    fun getHeightPxByRatio(context: Context, heightDp: Int): Int {
        val deviceHeightPx = context.getDeviceHeightPx()
        val heightPx = heightDp.toFloat().convertDPtoPX(context)
        val guideHeightPx = context.resources.getDimension(R.dimen.guide_height)

        return (deviceHeightPx * (heightPx / guideHeightPx)).toInt()
    }

}