package com.example.skptemp.common.util

import android.widget.TextView
import kotlin.math.floor

object TextViewUtil {
    fun TextView.setWrapContentMaxLines() = post {
        val maxLinesByHeight = floor(height / (textSize + lineSpacingExtra))
        maxLines = maxLinesByHeight.toInt()
    }

    fun getTextLineByLetter(text: String) =
        text.replace(" ", "\u00A0")
}