package com.example.skptemp.common.util

import com.example.skptemp.common.constants.CharmConstants

object CharmUtil {
    fun getProgressRatio(progressDays: Int) =
        (progressDays / CharmConstants.TOTAL_DAYS.number.toFloat() * 100).toInt()

    fun getTextLineByLetter(text: String) =
        text.replace(" ", "\u00A0")
}