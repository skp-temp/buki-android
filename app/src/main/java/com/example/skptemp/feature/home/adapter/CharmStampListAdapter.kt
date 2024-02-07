package com.example.skptemp.feature.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.skptemp.common.constants.CharmType
import com.example.skptemp.common.constants.EmotionType
import com.example.skptemp.common.ui.setOnSingleClickListener
import com.example.skptemp.common.util.ColorUtil
import com.example.skptemp.common.util.ViewUtil.setWidthPx
import com.example.skptemp.databinding.CharmStampListItemBinding
import com.example.skptemp.feature.home.RecordDialogFragment
import com.example.skptemp.model.CharmStamp
import java.text.SimpleDateFormat
import java.util.Date

class CharmStampListAdapter(
    private val charmStamps: MutableList<CharmStamp>,
    private val activity: AppCompatActivity,
    private val charmType: CharmType,
    private val widthPx: Int
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class ViewHolder(
        private val binding: CharmStampListItemBinding,
        private val activity: AppCompatActivity,
        private val charmType: CharmType,
        private val widthPx: Int
    ) : RecyclerView.ViewHolder(binding.root) {

        private var mDialog: RecordDialogFragment? = null

        fun bind(charmStamp: CharmStamp) = with(binding) {
            val emotionDrawableId = charmType.emotion.getEmotionDrawableId(charmStamp.emotionType)
            emotion.setImageResource(emotionDrawableId)

            charmStamp.date?.run {
                emotion.setOnSingleClickListener {
                    showRecordDialog(charmStamp)
                }
                date.text = this
            }

            root.setWidthPx(widthPx)
            date.setTextColor(ColorUtil.getColor(date.context, charmType.color.subText))
        }

        private fun showRecordDialog(charmStamp: CharmStamp) {
            mDialog = mDialog ?: RecordDialogFragment(this, charmType, charmStamp)
            mDialog?.show(activity.supportFragmentManager, DIALOG_TAG)
        }

        fun dismissRecordDialog() {
            mDialog?.dismiss()
            mDialog = null
        }

        fun updateStampRecord(message: String) {
            // TODO: (서버 통신)
        }

        companion object {
            private const val DIALOG_TAG = "record_dialog"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = CharmStampListItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding, activity, charmType, widthPx)
    }

    override fun getItemCount() = charmStamps.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(charmStamps[position])
    }

    override fun getItemViewType(position: Int) = position

    fun updateItem(position: Int, selectedEmotionType: EmotionType, recordMessage: String?) {
        // TODO 추후 수정
        val date = SimpleDateFormat("yy. M. d").format(Date(System.currentTimeMillis()))
        charmStamps[position] = CharmStamp(selectedEmotionType, date, recordMessage)
        notifyItemChanged(position)
    }
}