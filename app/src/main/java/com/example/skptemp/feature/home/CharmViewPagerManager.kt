package com.example.skptemp.feature.home

import android.view.MotionEvent
import android.view.View
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.skptemp.R
import com.example.skptemp.common.util.ViewUtil.getDeviceWidthPx
import kotlin.math.abs
import kotlin.math.max

object CharmViewPagerManager {

    private const val MIN_SCALE = 0.9f
    private const val MIN_ALPHA = 0.5f

    fun ViewPager2.setPageChangeAnimation() {
        val marginPx = resources.getDimensionPixelOffset(R.dimen.charm_pager_margin)

        val transform = CompositePageTransformer().apply {
            addTransformer(MarginPageTransformer(marginPx))
            addTransformer { view, position ->
                val scaleFactor = max(MIN_SCALE, 1 - abs(position))

                view.scaleX = scaleFactor
                view.scaleY = scaleFactor

                view.alpha = (MIN_ALPHA +
                        (((scaleFactor - MIN_SCALE) / (1 - MIN_SCALE)) * (1 - MIN_ALPHA)))
            }
        }

        setPageTransformer(transform)
    }

    fun ViewPager2.setHorizontalPadding(viewPagerHeightPx: Int) {
        val screenWidthPx = context.getDeviceWidthPx()
        val widthRatio = resources.getDimension(R.dimen.charm_pager_width) /
                resources.getDimension(R.dimen.charm_pager_height)
        val widthPx = widthRatio * viewPagerHeightPx

        val padding = ((screenWidthPx - widthPx) / 2).toInt()

        setPadding(padding, 0, padding, 0)
    }

    fun ViewPager2.expandSwipeArea(parentView: View) =
        parentView.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    requestDisallowInterceptTouchEvent(true)
                    beginFakeDrag()
                    true
                }
                MotionEvent.ACTION_MOVE -> {
                    if (event.historySize > 0) {
                        fakeDragBy(event.x - event.getHistoricalX(0))
                    }
                    true
                }
                MotionEvent.ACTION_UP -> {
                    endFakeDrag()
                    requestDisallowInterceptTouchEvent(false)
                    performClick()
                    true
                }
                else -> {
                    false
                }
            }
        }
}