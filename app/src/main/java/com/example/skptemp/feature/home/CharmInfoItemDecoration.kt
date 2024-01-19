package com.example.skptemp.feature.home

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.skptemp.R
import com.example.skptemp.common.util.ViewUtil.convertDPtoPX

class CharmInfoItemDecoration(context: Context) : RecyclerView.ItemDecoration() {

    private val mFirstItemTopMarginPx =
        FIRST_ITEM_TOP_MARGIN_DP.toFloat().convertDPtoPX(context).toInt()

    private val mItemMarginPx =
        ITEM_MARGIN_DP.toFloat().convertDPtoPX(context).toInt()

    private val mScrollEndMarginPx =
        context.resources.getDimensionPixelOffset(R.dimen.scroll_end_margin)

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)

        if (position == state.itemCount - 1) {
            outRect.bottom += mScrollEndMarginPx
            return
        }

        if (position == 0) {
            outRect.top += mFirstItemTopMarginPx
        }
        outRect.bottom += mItemMarginPx
    }

    companion object {
        private const val FIRST_ITEM_TOP_MARGIN_DP = 16
        private const val ITEM_MARGIN_DP = 12
    }

}