package com.example.skptemp.common.constants

import android.content.Context
import com.example.skptemp.R

enum class NotificationType(val iconId: Int, val title: String, private val detailId: Int) {
    CHARM_GROWTH(R.drawable.ic_noti_charm, "부적", R.string.noti_charm_growth),
    CHARM_STAMP(R.drawable.ic_noti_charm, "부적", R.string.noti_charm_stamp),
    MESSAGE(R.drawable.ic_noti_charm, "응원메시지", R.string.noti_message),
    MESSAGE_WITH_GIFT(R.drawable.ic_noti_charm, "응원메시지", R.string.noti_message_with_gift),
    FRIEND_ACCEPT(R.drawable.ic_noti_friend, "친구맺기", R.string.noti_friend_accept),
    FRIEND_REQUEST(R.drawable.ic_noti_friend, "친구맺기", R.string.noti_friend_request);

    fun getDetailString(context: Context, name: String, charmType: CharmType? = null): String {
        val charmName = charmType?.title ?: ""
        return when (this) {
            CHARM_GROWTH -> context.resources.getString(detailId, name, name, charmName)
            CHARM_STAMP -> context.resources.getString(detailId, name)
            MESSAGE -> context.resources.getString(detailId, name, charmName, name)
            MESSAGE_WITH_GIFT -> context.resources.getString(detailId, name, charmName, name)
            FRIEND_ACCEPT -> context.resources.getString(detailId, name, name)
            FRIEND_REQUEST -> context.resources.getString(detailId, name, name)
        }
    }
}