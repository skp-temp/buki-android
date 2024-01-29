package com.example.skptemp.feature.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.skptemp.R
import com.example.skptemp.common.ui.inf.OnBottomDialogListener
import com.example.skptemp.databinding.CharmEditDialogBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CharmEditDialog : BottomSheetDialogFragment() {

    private var _binding: CharmEditDialogBinding? = null
    private val binding get() = _binding!!
    var onBottomDialogListener: OnBottomDialogListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CharmEditDialogBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        // 드래그 시 다이얼로그 크기 변경 막기
        val behavior = BottomSheetBehavior.from(binding.root.parent as View)
        behavior.isHideable = false

        setOnClickListener()
    }

    private fun setOnClickListener() = with(binding) {
        editButton.setOnClickListener {
            onBottomDialogListener?.onItemClick(CharmEditDialogItem.EDIT)
        }
        imageDownloadButton.setOnClickListener {
            onBottomDialogListener?.onItemClick(CharmEditDialogItem.IMAGE_DOWNLOAD)
        }
        deleteButton.setOnClickListener {
            onBottomDialogListener?.onItemClick(CharmEditDialogItem.DELETE)
        }
        cancelButton.setOnClickListener {
            onBottomDialogListener?.onItemClick(CharmEditDialogItem.CANCEL)
        }
    }

    override fun getTheme() = R.style.BottomDialogTheme
}