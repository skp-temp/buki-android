package com.example.skptemp.feature.home.adapter

import android.content.Context
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.skptemp.common.util.ColorUtil
import com.example.skptemp.common.util.ViewUtil.convertDPtoPX
import com.example.skptemp.databinding.CharmImageListItemBinding
import com.example.skptemp.feature.home.CharmDetailActivity

// TODO: List 타입을 서버에서 내려오는 DTO로 변경
class CharmImageListAdapter(
    private val context: Context,
    private val list: List<Int>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val backgroundRadiusPx = BACKGROUND_RADIUS_DP.convertDPtoPX(context)

    class ViewHolder(
        private val binding: CharmImageListItemBinding,
        private val context: Context
    ) : RecyclerView.ViewHolder(binding.root) {

        // TODO: 서버 이미지 로드 (glide)
        fun bind(radius: Float, color: Int) {
            binding.charmImage.background = GradientDrawable().apply {
                cornerRadius = radius
                setColor(color)
            }
            binding.charmImage.setOnClickListener {
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
        return ViewHolder(binding, context)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val color = ColorUtil.getColor(context, list[position])
        (holder as ViewHolder).bind(backgroundRadiusPx, color)
    }

    override fun getItemViewType(position: Int) = position

    companion object {
        private const val BACKGROUND_RADIUS_DP = 20f
    }
}