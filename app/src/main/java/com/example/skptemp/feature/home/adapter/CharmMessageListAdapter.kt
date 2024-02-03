package com.example.skptemp.feature.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.skptemp.R
import com.example.skptemp.common.util.CharmUtil.getTextLineByLetter
import com.example.skptemp.databinding.CharmMessageListItemBinding
import com.example.skptemp.feature.home.CharmDetailActivity
import com.example.skptemp.model.CharmMessage

class CharmMessageListAdapter(
    private val charmMessages: List<CharmMessage>,
    private val backgroundColor: Int,
    private val textColor: Int
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class ViewHolder(
        private val binding: CharmMessageListItemBinding,
        private val backgroundColor: Int,
        private val textColor: Int
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(charmMessage: CharmMessage) = with(binding) {
            setViewColor()

            friendProfilePicture.setImageResource(R.drawable.temp_profile_picture)
            name.text = charmMessage.name
            message.text = getTextLineByLetter(charmMessage.message)
            date.text = charmMessage.date

            if (!charmMessage.haveGift) return
            giftLayout.visibility = View.VISIBLE
        }

        private fun setViewColor() = with(binding) {
            cardView.setCardBackgroundColor(backgroundColor)

            name.setTextColor(textColor)
            date.setTextColor(textColor)
            gift.setColorFilter(textColor)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = CharmMessageListItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding, backgroundColor, textColor)
    }

    override fun getItemCount() = charmMessages.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(charmMessages[position])
    }

    override fun getItemViewType(position: Int) = position

}