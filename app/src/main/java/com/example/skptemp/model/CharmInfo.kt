package com.example.skptemp.model

import com.example.skptemp.common.constants.CharmConstants
import com.example.skptemp.common.constants.CharmType

// TODO: 추후 프로퍼티 수정
data class CharmInfo(
    val type: CharmType,
    val isDoneToday: Boolean,
    val title: String,
    val progress: Int = CharmConstants.TOTAL_DAYS.number,
)