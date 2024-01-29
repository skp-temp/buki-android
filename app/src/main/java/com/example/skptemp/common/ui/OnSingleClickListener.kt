package com.example.skptemp.common.ui

import android.view.View

class OnSingleClickListener(
    private val clickListener: (view: View) -> Unit
) : View.OnClickListener {

    private var lastClickedTime = 0L

    override fun onClick(view: View) {
        val clickedTime = System.currentTimeMillis()

        if (clickedTime - lastClickedTime < CLICKED_INTERVAL) {
            return
        }

        lastClickedTime = clickedTime
        clickListener(view)
    }

    companion object {
        private const val CLICKED_INTERVAL = 800L
    }
}

fun View.setOnSingleClickListener(
    clickListener: (view: View) -> Unit
) {
    setOnClickListener(OnSingleClickListener(clickListener))
}