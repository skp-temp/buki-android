package com.example.skptemp.common.constants

import com.example.skptemp.R

enum class BukiBadgeType(val title: String, val backgroundColor: Int, val textColor: Int) {
    EATING("식습관", R.color.orange_200, R.color.orange_400),
    HAPPINESS("행복", R.color.green_200, R.color.green_400),
    STUDY("공부", R.color.purple_200, R.color.purple_400),
    EXERCISE("운동", R.color.yellowgreen_200, R.color.yellowgreen_400),
    MONEY("금전", R.color.yellow_200, R.color.yellow_400),
    PET("반려동물", R.color.brown_200, R.color.brown_400),
    INCOMPLETE("미완료", R.color.gray_300, R.color.gray_500)
}