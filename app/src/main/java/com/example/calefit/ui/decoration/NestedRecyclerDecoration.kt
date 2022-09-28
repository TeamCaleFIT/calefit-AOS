package com.example.calefit.ui.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class NestedRecyclerDecoration(
    private val bottomPadding: Int,
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.top = bottomPadding
    }

    companion object {
        const val DEFAULT_INNER_RECYCLER_VIEW_ITEM_PADDING = 50
    }
}