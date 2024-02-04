package com.example.skptemp.common.constants

import com.example.skptemp.R

enum class CharmTypeColor(
    val background: Int,
    val subBackground: Int,
    val subText: Int,
    val text: Int
) {
    WORKOUT(R.color.yellowgreen_100, R.color.yellowgreen_200, R.color.yellowgreen_300, R.color.yellowgreen_400),
    MONEY(R.color.yellow_100, R.color.yellow_200, R.color.yellow_300, R.color.yellow_400),
    DIET(R.color.orange_100, R.color.orange_200, R.color.orange_300, R.color.orange_400),
    BEAUTY(R.color.pink_100, R.color.pink_200, R.color.pink_300, R.color.pink_400),
    HAPPY(R.color.green_100, R.color.green_200, R.color.green_300, R.color.green_400),
    STUDY(R.color.purple_100, R.color.purple_200, R.color.purple_300, R.color.purple_400),
    HUSTLE(R.color.blue_100, R.color.blue_200, R.color.blue_300, R.color.blue_400),
    PET(R.color.brown_100, R.color.brown_200, R.color.brown_300, R.color.brown_400)
}