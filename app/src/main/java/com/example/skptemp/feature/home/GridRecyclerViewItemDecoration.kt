package com.example.skptemp.feature.home

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.skptemp.common.util.ViewUtil.convertDPtoPX

class GridRecyclerViewItemDecoration(
    private val spanCount: Int = 1,
    topSpace: Int = 0,
    rowSpace: Int = 0,
    colSpace: Int = 0,
    endSpace: Int = 0,
    context: Context
) : RecyclerView.ItemDecoration() {

    private val mTopSpacePx =
        topSpace.toFloat().convertDPtoPX(context).toInt()

    private val mRowSpacePx =
        rowSpace.toFloat().convertDPtoPX(context).toInt()

    private val mColSpacePx =
        colSpace.toFloat().convertDPtoPX(context).toInt()

    private val mEndSpacePx =
        endSpace.toFloat().convertDPtoPX(context).toInt()

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)

        val row = position / spanCount
        val col = position % spanCount
        val maxRow = (state.itemCount - 1) / spanCount

        if (row == 0) outRect.top += mTopSpacePx
        else outRect.top = mRowSpacePx

        if (spanCount > 1 || col > 0) outRect.left = mColSpacePx

        if (row == maxRow) outRect.bottom += mEndSpacePx
    }

}