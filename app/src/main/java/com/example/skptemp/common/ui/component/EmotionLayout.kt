package com.example.skptemp.common.ui.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.skptemp.common.constants.CharmType
import com.example.skptemp.common.constants.EmotionType
import com.example.skptemp.common.ui.inf.OnSelectEmotionListener
import com.example.skptemp.common.ui.setOnSingleClickListener
import com.example.skptemp.databinding.EmotionLayoutBinding

class EmotionLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleArr: Int = 0,
) : LinearLayout(context, attrs, defStyleArr) {

    private val binding by lazy {
        EmotionLayoutBinding.inflate(LayoutInflater.from(context), this, true)
    }

    fun setCharmType(charmType: CharmType) = with(charmType) {
        binding.run {
            happy.setImageResource(getCharmTypeEmotion(EmotionType.HAPPY))
            pleasure.setImageResource(getCharmTypeEmotion(EmotionType.PLEASURE))
            clam.setImageResource(getCharmTypeEmotion(EmotionType.CLAM))
            sad.setImageResource(getCharmTypeEmotion(EmotionType.SAD))
            anger.setImageResource(getCharmTypeEmotion(EmotionType.ANGER))
        }
    }

    private fun CharmType.getCharmTypeEmotion(emotionType: EmotionType) =
        emotion.getEmotionDrawableId(emotionType)

    fun setOnSelectEmotionListener(onSelectEmotionListener: OnSelectEmotionListener) =
        with(binding) {
            happy.setOnSingleClickListener { onSelectEmotionListener.onSelectEmotion(EmotionType.HAPPY) }
            pleasure.setOnSingleClickListener { onSelectEmotionListener.onSelectEmotion(EmotionType.PLEASURE) }
            clam.setOnSingleClickListener { onSelectEmotionListener.onSelectEmotion(EmotionType.CLAM) }
            sad.setOnSingleClickListener { onSelectEmotionListener.onSelectEmotion(EmotionType.SAD) }
            anger.setOnSingleClickListener { onSelectEmotionListener.onSelectEmotion(EmotionType.ANGER) }
        }
}