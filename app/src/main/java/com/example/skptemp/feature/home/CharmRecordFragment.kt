package com.example.skptemp.feature.home

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.example.skptemp.common.constants.EmotionType
import com.example.skptemp.common.ui.inf.OnSelectEmotionListener
import com.example.skptemp.common.ui.setOnSingleClickListener
import com.example.skptemp.databinding.FragmentCharmRecordBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharmRecordFragment : Fragment() {

    private var _binding: FragmentCharmRecordBinding? = null
    private val binding get() = _binding!!

    private lateinit var mActivity: CharmDetailActivity
    private val mCharmType by lazy { mActivity.mCharmType }

    private var mSelectedEmotionType: EmotionType? = null
    private val selectedEmotionDrawableId: Int
        get() = mCharmType.emotion.getEmotionDrawableId(mSelectedEmotionType!!)

    private val mRecordMessage: String?
        get() = binding.recordEdit.text.toString().run {
            if (TextUtils.isEmpty(this)) null else this
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharmRecordBinding.inflate(inflater, container, false)
        mActivity = requireActivity() as CharmDetailActivity

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        composeUI()
    }

    private fun composeUI() = with(binding) {
        setEmotionLayout()
        setOnClickListenerSelectedEmotion()
        setOnBackPressedCallback()

        largeButton.setEnabledButton(mSelectedEmotionType != null)
        largeButton.setOnSingleClickListener {
            mSelectedEmotionType ?: return@setOnSingleClickListener
            mActivity.finishRecord(mSelectedEmotionType!!, mRecordMessage)
        }
    }

    private fun setEmotionLayout() = with(binding.emotionLayout) {
        setCharmType(mCharmType)
        setOnSelectEmotionListener(
            object : OnSelectEmotionListener {
                override fun onSelectEmotion(selectedEmotion: EmotionType) {
                    mSelectedEmotionType = selectedEmotion
                    updateLayout()
                }
            }
        )
    }

    private fun setOnClickListenerSelectedEmotion() = with(binding.selectedEmotion) {
        val emotionTypes = EmotionType.entries
        val emotionSize = emotionTypes.size

        setOnClickListener {
            mSelectedEmotionType?.run {
                mSelectedEmotionType = emotionTypes[(ordinal + 1) % emotionSize]
                setImageResource(selectedEmotionDrawableId)
            }
        }
    }

    private fun setOnBackPressedCallback() {
        // toolbar back button
        mActivity.setToolbarBackButtonOnClickListener {
            pressedBackButton()
        }

        // device nav bar back button
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                pressedBackButton()
            }
        }
        mActivity.onBackPressedDispatcher.addCallback(
            this@CharmRecordFragment,
            onBackPressedCallback
        )
    }

    private fun updateLayout() = with(binding) {
        var isEnabledLargeButton = true
        var selectedEmotionVisibility = View.VISIBLE
        var emotionLayoutVisibility = View.GONE

        mSelectedEmotionType?.run {
            selectedEmotion.setImageResource(selectedEmotionDrawableId)
            mRecordMessage?.run { binding.recordEdit.setText(this) }
            Unit
        } ?: run {
            isEnabledLargeButton = false
            selectedEmotionVisibility = View.GONE
            emotionLayoutVisibility = View.VISIBLE
        }

        largeButton.setEnabledButton(isEnabledLargeButton)
        emotionLayout.visibility = emotionLayoutVisibility
        selectedEmotionLayout.visibility = selectedEmotionVisibility
        recordLayout.visibility = selectedEmotionVisibility
    }

    private fun pressedBackButton() {
        mSelectedEmotionType?.run {
            mSelectedEmotionType = null
            updateLayout()
        } ?: run {
            mActivity.finishFragment()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}