package com.parish.register.ui.custom

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.parish.register.R
import com.parish.register.utils.dimenToPixels

class CommonItemDecoration(
    context: Context,
    private val considerFloatingActionButton: Boolean = false
) : RecyclerView.ItemDecoration() {

    private var horizontalMargin = dimenToPixels(
        context, R.dimen.normal_padding
    )

    private var verticalMargin = dimenToPixels(
        context, R.dimen.small_padding
    )

    private var floatingActionButtonPadding = dimenToPixels(
        context, R.dimen.fab_space_padding
    )

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView,
        state: RecyclerView.State
    ) {
        if (isFirstItem(view, parent)) {
            outRect.top = verticalMargin * 2
            outRect.bottom = verticalMargin
        } else if (isLastItem(view, parent)) {
            if (considerFloatingActionButton) {
                outRect.top = verticalMargin
                outRect.bottom = floatingActionButtonPadding
            } else {
                outRect.top = verticalMargin
                outRect.bottom = verticalMargin * 2
            }
        } else {
            outRect.top = verticalMargin
            outRect.bottom = verticalMargin
        }

        outRect.left = horizontalMargin
        outRect.right = horizontalMargin
    }

    private fun isFirstItem(view: View, parent: RecyclerView): Boolean {
        return parent.adapter != null && parent.getChildAdapterPosition(view) == 0
    }

    private fun isLastItem(view: View, parent: RecyclerView): Boolean {
        return parent.adapter != null && parent.getChildAdapterPosition(view) == parent.adapter?.itemCount!! - 1
    }
}