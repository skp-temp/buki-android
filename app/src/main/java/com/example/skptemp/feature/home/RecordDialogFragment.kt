package com.example.skptemp.feature.home

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.skptemp.R
import com.example.skptemp.common.constants.CharmType
import com.example.skptemp.common.ui.setOnSingleClickListener
import com.example.skptemp.databinding.DialogFragmentRecordBinding
import com.example.skptemp.feature.home.adapter.CharmStampListAdapter
import com.example.skptemp.model.CharmStamp

class RecordDialogFragment(
    private val parentView: CharmStampListAdapter.ViewHolder,
    charmType: CharmType,
    private val charmStamp: CharmStamp
) : DialogFragment() {

    private var _binding: DialogFragmentRecordBinding? = null
    private val binding get() = _binding!!

    private val emotionDrawableId =
        charmType.emotion.getEmotionDrawableId(charmStamp.emotionType)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogFragmentRecordBinding.inflate(inflater, container, false)
        isCancelable = false

        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?) =
        Dialog(requireContext(), R.style.dialog_background_dim_700_style)

    override fun onResume() {
        super.onResume()

        binding.date.text = resources.getString(R.string.date_record, charmStamp.date)

        binding.emotion.setImageResource(emotionDrawableId)
        charmStamp.message?.run { binding.recordEdit.setText(this) }

        binding.close.setOnSingleClickListener {
            val modifiedMessage = binding.recordEdit.text.toString()
            if (charmStamp.message != modifiedMessage) {
                charmStamp.message = modifiedMessage
                parentView.updateStampRecord(modifiedMessage)
            }
            parentView.dismissRecordDialog()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}