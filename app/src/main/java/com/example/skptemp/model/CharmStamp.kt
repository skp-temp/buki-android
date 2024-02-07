package com.example.skptemp.model

import com.example.skptemp.common.constants.EmotionType

data class CharmStamp(
    val emotionType: EmotionType = EmotionType.NOTHING,
    val date: String? = null,
    var message: String? = null
)