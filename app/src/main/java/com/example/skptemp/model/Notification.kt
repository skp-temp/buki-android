package com.example.skptemp.model

import com.example.skptemp.common.constants.CharmType
import com.example.skptemp.common.constants.NotificationType

data class Notification(
    val type: NotificationType,
    val name: String,
    val postedDate: String,
    val charmType: CharmType? = null
)