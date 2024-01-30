package com.example.skptemp.feature.home.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.skptemp.common.constants.CharmType
import com.example.skptemp.common.ui.setOnSingleClickListener
import com.example.skptemp.common.util.ColorUtil
import com.example.skptemp.databinding.CharmImageListItemBinding
import com.example.skptemp.feature.home.CharmDetailActivity

// TODO: List 타입을 서버에서 내려오는 DTO로 변경
class CharmImageListAdapter(
    private val context: Context,
    private val list: List<CharmType>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class ViewHolder(
        private val binding: CharmImageListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(backgroundColor: Int, strokeColor: Int) = with(binding.charmImage) {
            setCardBackgroundColor(backgroundColor)
            setStrokeColor(strokeColor)

            setOnSingleClickListener {
                Intent(context, CharmDetailActivity::class.java).run {
                    //putExtra("", "")
                    context.startActivity(this)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = CharmImageListItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val backgroundColor = ColorUtil.getColor(context, list[position].backgroundColor)
        val strokeColor = ColorUtil.getColor(context, list[position].subTextColor)

        (holder as ViewHolder).bind(backgroundColor, strokeColor)
    }

    override fun getItemViewType(position: Int) = position

}