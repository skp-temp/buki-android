package com.example.skptemp.feature.signup

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import com.example.skptemp.databinding.TermsListItemBinding
import com.example.skptemp.feature.signup.data.Terms
import com.example.skptemp.feature.signup.CheckCircleColorPicker.setChecked
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class TermsRecyclerViewAdapter(
    private val context: Context,
    private val viewLifecycleOwner: LifecycleOwner,
    private val termsViewModel: TermsViewModel
) : RecyclerView.Adapter<TermsRecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(
        private val binding: TermsListItemBinding,
        private val context: Context,
        private val viewLifecycleOwner: LifecycleOwner,
        private val termsViewModel: TermsViewModel
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int): Unit = with(binding) {
            val terms = termsViewModel.termsList[position]

            termsTitle.text = terms.getTitleString()
            checkCircle.setOnClickListener {
                termsViewModel.agreeTerms(position)
            }
            chevronRight.setOnClickListener {
                Intent(Intent.ACTION_VIEW, Uri.parse(terms.landingUrl)).let { intent ->
                    context.startActivity(intent)
                }
            }

            // viewmodel isChecked가 update된 값을 받음
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    terms.isChecked.collectLatest { isChecked ->
                        checkCircle.setChecked(context, isChecked)
                    }
                }
            }
        }

        private fun Terms.getTitleString() = "$title${if (isRequired) "(필수)" else "(선택)"}"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = TermsListItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return ViewHolder(binding, context, viewLifecycleOwner, termsViewModel)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(position)

    override fun getItemCount() = termsViewModel.termsList.size

    override fun getItemViewType(position: Int) = position
}