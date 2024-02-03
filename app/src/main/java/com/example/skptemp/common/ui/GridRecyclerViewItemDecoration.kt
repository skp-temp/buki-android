package com.example.skptemp.common.ui

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GridRecyclerViewItemDecoration(
    private val spanCount: Int = 1,
    topSpaceId: Int? = null,
    rowSpaceId: Int? = null,
    colSpaceId: Int? = null,
    endSpaceId: Int? = null,
    context: Context
) : RecyclerView.ItemDecoration() {

    private val mTopSpacePx =
        topSpaceId?.run { context.resources.getDimensionPixelOffset(this) } ?: 0

    private val mRowSpacePx =
        rowSpaceId?.run { context.resources.getDimensionPixelOffset(this) } ?: 0

    private val mColSpacePx =
        colSpaceId?.run { context.resources.getDimensionPixelOffset(this) } ?: 0

    private val mEndSpacePx =
        endSpaceId?.run { context.resources.getDimensionPixelOffset(this) } ?: 0

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

        if (row == 0) outRect.top = mTopSpacePx
        else outRect.top = mRowSpacePx

        if (row == maxRow) outRect.bottom = mEndSpacePx

        if (spanCount == 1) return
        if (col > 0) outRect.left = mColSpacePx / 2
        if (col < spanCount - 1) outRect.right = mColSpacePx / 2
    }
}