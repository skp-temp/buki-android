package com.example.skptemp.feature.home

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.skptemp.R
import com.example.skptemp.common.constants.CharmType
import com.example.skptemp.common.constants.NotificationType
import com.example.skptemp.common.ui.GridRecyclerViewItemDecoration
import com.example.skptemp.common.ui.component.Toolbar
import com.example.skptemp.databinding.ActivityNotificationBinding
import com.example.skptemp.feature.home.adapter.NotificationListAdapter
import com.example.skptemp.model.Notification
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationActivity : AppCompatActivity() {

    private var _binding: ActivityNotificationBinding? = null
    private val binding get() = _binding!!

    private val mContext: Context by lazy { this }

    // TODO: 서버 통신
    private val mNotifications = listOf(
        Notification(NotificationType.CHARM_GROWTH, "김혜민", "24분전", CharmType.MONEY),
        Notification(NotificationType.MESSAGE_WITH_GIFT, "윤다혜", "24분전", CharmType.HUSTLE),
        Notification(NotificationType.FRIEND_ACCEPT, "강동훈", "24분전"),
        Notification(NotificationType.CHARM_GROWTH, "김혜민", "5시간전"),
        Notification(NotificationType.CHARM_STAMP, "김혜민", "5시간전"),
        Notification(NotificationType.FRIEND_REQUEST, "최영진", "2023.11.06"),
        Notification(NotificationType.MESSAGE, "최태규", "2023.11.02", CharmType.HAPPY)
    )
    private val mNotificationListAdapter by lazy { NotificationListAdapter(mNotifications, mContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()

        binding.toolbar.setButtonOnClickListener(Toolbar.BACK_BUTTON) { finish() }
        setRecyclerView()
    }

    private fun setRecyclerView() = with(binding.notificationRecyclerView) {
        adapter = mNotificationListAdapter
        addItemDecoration(
            GridRecyclerViewItemDecoration(
                topSpaceId = R.dimen.notification_list_start_margin,
                rowSpaceId = R.dimen.notification_list_row_margin,
                endSpaceId = R.dimen.scroll_end_margin,
                context = mContext
            )
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}