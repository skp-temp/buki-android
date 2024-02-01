package com.example.skptemp.feature.home.adapter

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.skptemp.R
import com.example.skptemp.common.constants.NotificationType
import com.example.skptemp.common.util.CharmUtil.getTextLineByLetter
import com.example.skptemp.common.util.ColorUtil
import com.example.skptemp.common.util.ViewUtil.convertDPtoPX
import com.example.skptemp.databinding.NotiListItemBinding
import com.example.skptemp.model.Notification

class NotificationListAdapter(
    private val notifications: List<Notification>,
    private val context: Context
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val acceptButtonBackground by lazy {
        GradientDrawable().apply {
            cornerRadius = ACCEPT_BUTTON_RADIUS_DP.convertDPtoPX(context)
            setColor(ColorUtil.getColor(context, ACCEPT_BUTTON_BACKGROUND_COLOR))
        }
    }

    class ViewHolder(
        private val binding: NotiListItemBinding,
        private val acceptButtonBackground: GradientDrawable,
        private val context: Context
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(notification: Notification) = with(binding) {
            acceptButton.setAcceptButton(notification)

            image.setImageResource(notification.type.iconId)
            title.text = notification.type.title
            date.text = notification.postedDate

            detail.text = notification.type.getDetailString(
                context, notification.name, notification.charmType
            ).let { getTextLineByLetter(it) }

            notification.type.getActionString(context)?.let { actionText ->
                action.visibility = View.VISIBLE
                action.text = actionText
            }
        }

        private fun TextView.setAcceptButton(notification: Notification) {
            if (notification.type != NotificationType.FRIEND_REQUEST) {
                return
            }

            visibility = View.VISIBLE
            background = acceptButtonBackground

            var textId = R.string.noti_accept_button_disabled
            if (notification.isAccept == false) { // enable
                setTextColor(ColorUtil.getColor(context, R.color.blue))
                textId = R.string.noti_accept_button_enabled
                //acceptButton.setOnSingleClickListener {  }
            }

            text = context.resources.getString(textId)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = NotiListItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding, acceptButtonBackground, context)
    }

    override fun getItemCount() = notifications.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(notifications[position])
    }

    override fun getItemViewType(position: Int) = position

    companion object {
        private const val ACCEPT_BUTTON_RADIUS_DP = 8f
        private val ACCEPT_BUTTON_BACKGROUND_COLOR = R.color.gray_150
    }
}