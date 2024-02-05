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
        emotionLayout.setCharmType(mCharmType)
        emotionLayout.setOnSelectEmotionListener(
            object : OnSelectEmotionListener {
                override fun onSelectEmotion(selectedEmotion: EmotionType) {
                    mSelectedEmotionType = selectedEmotion
                    updateLayout()
                }
            }
        )

        mActivity.setBackButtonOnClickListener {
            pressedBackButton()
        }

        largeButton.setEnabledButton(mSelectedEmotionType != null)
        largeButton.setOnSingleClickListener {
            mSelectedEmotionType ?: return@setOnSingleClickListener
            mActivity.finishRecord(mSelectedEmotionType!!, mRecordMessage)
        }

        val emotionTypes = EmotionType.entries
        val emotionSize = emotionTypes.size

        selectedEmotion.setOnClickListener {
            mSelectedEmotionType?.run {
                mSelectedEmotionType = emotionTypes[(ordinal + 1) % emotionSize]
                binding.selectedEmotion.setImageResource(selectedEmotionDrawableId)
            }
        }

        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                pressedBackButton()
            }
        }
        mActivity.onBackPressedDispatcher.addCallback(this@CharmRecordFragment, onBackPressedCallback)
    }

    private fun updateLayout() {
        mSelectedEmotionType?.run {
            binding.largeButton.setEnabledButton(true)
            binding.emotionLayout.visibility = View.GONE
            binding.selectedEmotionLayout.visibility = View.VISIBLE
            binding.selectedEmotion.setImageResource(selectedEmotionDrawableId)
            binding.recordLayout.visibility = View.VISIBLE
            mRecordMessage?.run { binding.recordEdit.setText(this) }
            Unit
        } ?: run {
            binding.largeButton.setEnabledButton(false)
            binding.emotionLayout.visibility = View.VISIBLE
            binding.selectedEmotionLayout.visibility = View.GONE
            binding.recordLayout.visibility = View.GONE
        }
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