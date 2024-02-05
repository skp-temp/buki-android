package com.example.skptemp.common.ui.component

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.core.widget.NestedScrollView

class LockableNestedScrollView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleArr: Int = 0,
) : NestedScrollView(context, attrs, defStyleArr) {

    var scrollable = true

    override fun onTouchEvent(ev: MotionEvent) =
        scrollable && super.onTouchEvent(ev)

    override fun onInterceptHoverEvent(ev: MotionEvent) =
        scrollable && super.onInterceptHoverEvent(ev)
}