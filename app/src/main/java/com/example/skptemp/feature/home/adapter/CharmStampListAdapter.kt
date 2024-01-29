package com.example.skptemp.feature.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.skptemp.R
import com.example.skptemp.common.constants.CharmType
import com.example.skptemp.common.util.ViewUtil.setWidthPx
import com.example.skptemp.databinding.CharmStampListItemBinding
import com.example.skptemp.model.CharmStamp

class CharmStampListAdapter(
    private val charmStamps: List<CharmStamp>,
    private val charmType: CharmType,
    private val widthPx: Int,
    private val dateColor: Int
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class ViewHolder(
        private val binding: CharmStampListItemBinding,
        private val charmType: CharmType,
        private val widthPx: Int,
        private val dateColor: Int,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(charmStamp: CharmStamp) = with(binding) {
//            val emotionImage = charmStamp.emotion.getEmotionImageByCharmType(charmType)
//            emotion.setImageResource(emotionImage)
            emotion.setImageResource(R.drawable.temp_stamp)
            date.text = charmStamp.date

            root.setWidthPx(widthPx)
            date.setTextColor(dateColor)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = CharmStampListItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding, charmType, widthPx, dateColor)
    }

    override fun getItemCount() = charmStamps.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(charmStamps[position])
    }

    override fun getItemViewType(position: Int) = position

}