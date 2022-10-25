package com.parish.register.ui.custom

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.parish.register.R
import com.parish.register.utils.dimenToPixels

class CommonItemDecoration(context: Context) : RecyclerView.ItemDecoration() {

    private var horizontalMargin = dimenToPixels(
        context, R.dimen.normal_padding
    )

    private var verticalMargin = dimenToPixels(
        context, R.dimen.normal_padding
    )

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView,
        state: RecyclerView.State
    ) {
        if (isFirstItem(view, parent)) {
            outRect.top = horizontalMargin
        } else {
            outRect.top = verticalMargin
        }
        if (isLastItem(view, parent)) {
            outRect.bottom = horizontalMargin
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