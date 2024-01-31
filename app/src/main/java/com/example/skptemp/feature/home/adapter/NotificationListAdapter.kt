package com.example.skptemp.feature.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.skptemp.common.util.CharmUtil.getTextLineByLetter
import com.example.skptemp.databinding.NotiListItemBinding
import com.example.skptemp.model.Notification

class NotificationListAdapter(
    private val notifications: List<Notification>,
    private val context: Context
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class ViewHolder(
        private val binding: NotiListItemBinding,
        private val context: Context
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(notification: Notification) = with(binding) {
            image.setImageResource(notification.type.iconId)
            title.text = notification.type.title
            date.text = notification.postedDate

            val detailText = notification.type.getDetailString(context, notification.name, notification.charmType)
            detail.text = getTextLineByLetter(detailText)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = NotiListItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding, context)
    }

    override fun getItemCount() = notifications.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(notifications[position])
    }

    override fun getItemViewType(position: Int) = position

}