package com.example.skptemp.common.constants

enum class CharmType(
    val title: String,
    val color: CharmTypeColor,
    val emotion: CharmTypeEmotion
) {
    WORKOUT("운동", CharmTypeColor.WORKOUT, CharmTypeEmotion.WORKOUT),
    MONEY("금전", CharmTypeColor.MONEY, CharmTypeEmotion.MONEY),
    DIET("식습관", CharmTypeColor.DIET, CharmTypeEmotion.DIET),
    BEAUTY("뷰티", CharmTypeColor.BEAUTY, CharmTypeEmotion.BEAUTY),
    HAPPY("행복", CharmTypeColor.HAPPY, CharmTypeEmotion.HAPPY),
    STUDY("공부", CharmTypeColor.STUDY, CharmTypeEmotion.STUDY),
    HUSTLE("갓생", CharmTypeColor.HUSTLE, CharmTypeEmotion.HUSTLE),
    PET("반려동물", CharmTypeColor.PET, CharmTypeEmotion.PET),
}