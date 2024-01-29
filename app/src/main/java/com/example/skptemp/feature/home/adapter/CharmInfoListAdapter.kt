package com.example.skptemp.feature.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.skptemp.common.constants.CharmType
import com.example.skptemp.databinding.CharmInfoListItemBinding

// TODO: List 타입을 서버에서 내려오는 DTO로 변경
class CharmInfoListAdapter(private val list: List<String>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class ViewHolder(private val binding: CharmInfoListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(text: String) = with(binding) {
            title.text = "글자수제한테스트글자수제한테스트"
            charmTypeTag.setTagType(CharmType.HAPPY)

            continuousText.text = "3일째 도전중"
            progressBar.progress(50)
        }

        private fun String.skip() =
            if (length <= TITLE_MAX_LENGTH) this
            else replaceRange(TITLE_MAX_LENGTH + 1, length, TITLE_SKIP_REPLACE)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = CharmInfoListItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(list[position])
    }

    override fun getItemViewType(position: Int) = position

    companion object {
        private const val TITLE_MAX_LENGTH = 12
        private const val TITLE_SKIP_REPLACE = "..."
    }
}