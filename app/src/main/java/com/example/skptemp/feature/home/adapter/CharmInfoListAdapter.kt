package com.example.skptemp.feature.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.skptemp.R
import com.example.skptemp.common.util.CharmUtil.getProgressRatio
import com.example.skptemp.databinding.CharmInfoListItemBinding
import com.example.skptemp.model.CharmInfo

// TODO: List 타입을 서버에서 내려오는 DTO로 변경
class CharmInfoListAdapter(private var charmInfos: MutableList<CharmInfo>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class ViewHolder(private val binding: CharmInfoListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(charmInfo: CharmInfo) = with(binding) {
            charmTypeTag.setTagType(charmInfo.type)
            if (!charmInfo.isDoneToday) incompleteTag.visibility = View.VISIBLE
            title.text = charmInfo.title
            continuousText.text =
                continuousText.resources.getString(R.string.progress_day, charmInfo.progress)
            progressBar.progress(getProgressRatio(charmInfo.progress))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = CharmInfoListItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount() = charmInfos.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(charmInfos[position])
    }

    override fun getItemViewType(position: Int) = position

    fun updateAll(updatedCharmInfos: List<CharmInfo>) {
        charmInfos.clear()
        charmInfos.addAll(updatedCharmInfos)
        notifyDataSetChanged()
    }
}