package com.example.skptemp.common.constants

import com.example.skptemp.R

// TODO: (타입별 drawable 추후 수정)
enum class CharmTypeEmotion(
    private val happy: Int,
    private val pleasure: Int,
    private val clam: Int,
    private val sad: Int,
    private val anger: Int,
    private val nothing: Int
) {
    WORKOUT(
        R.drawable.emotion_workout_happy,
        R.drawable.emotion_workout_pleasure,
        R.drawable.emotion_workout_clam,
        R.drawable.emotion_workout_sad,
        R.drawable.emotion_workout_anger,
        R.drawable.emotion_workout_nothing
    ),
    MONEY(
        R.drawable.emotion_workout_happy,
        R.drawable.emotion_workout_pleasure,
        R.drawable.emotion_workout_clam,
        R.drawable.emotion_workout_sad,
        R.drawable.emotion_workout_anger,
        R.drawable.emotion_workout_nothing
    ),
    DIET(
        R.drawable.emotion_workout_happy,
        R.drawable.emotion_workout_pleasure,
        R.drawable.emotion_workout_clam,
        R.drawable.emotion_workout_sad,
        R.drawable.emotion_workout_anger,
        R.drawable.emotion_workout_nothing
    ),
    BEAUTY(
        R.drawable.emotion_workout_happy,
        R.drawable.emotion_workout_pleasure,
        R.drawable.emotion_workout_clam,
        R.drawable.emotion_workout_sad,
        R.drawable.emotion_workout_anger,
        R.drawable.emotion_workout_nothing
    ),
    HAPPY(
        R.drawable.emotion_workout_happy,
        R.drawable.emotion_workout_pleasure,
        R.drawable.emotion_workout_clam,
        R.drawable.emotion_workout_sad,
        R.drawable.emotion_workout_anger,
        R.drawable.emotion_workout_nothing
    ),
    STUDY(
        R.drawable.emotion_workout_happy,
        R.drawable.emotion_workout_pleasure,
        R.drawable.emotion_workout_clam,
        R.drawable.emotion_workout_sad,
        R.drawable.emotion_workout_anger,
        R.drawable.emotion_workout_nothing
    ),
    HUSTLE(
        R.drawable.emotion_workout_happy,
        R.drawable.emotion_workout_pleasure,
        R.drawable.emotion_workout_clam,
        R.drawable.emotion_workout_sad,
        R.drawable.emotion_workout_anger,
        R.drawable.emotion_workout_nothing
    ),
    PET(
        R.drawable.emotion_workout_happy,
        R.drawable.emotion_workout_pleasure,
        R.drawable.emotion_workout_clam,
        R.drawable.emotion_workout_sad,
        R.drawable.emotion_workout_anger,
        R.drawable.emotion_workout_nothing
    );

    fun getEmotionDrawableId(emotionType: EmotionType) =
        when (emotionType) {
            EmotionType.HAPPY -> happy
            EmotionType.PLEASURE -> pleasure
            EmotionType.CLAM -> clam
            EmotionType.SAD -> sad
            EmotionType.ANGER -> anger
            EmotionType.NOTHING -> nothing
        }
}