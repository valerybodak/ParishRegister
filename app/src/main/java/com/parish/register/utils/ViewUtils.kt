package com.parish.register.utils

import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.text.SpannableString
import android.text.style.BackgroundColorSpan
import android.view.View
import android.view.Window
import android.widget.TextView
import androidx.annotation.DimenRes
import androidx.core.content.ContextCompat
import com.parish.register.R


fun Window.getStatusBarHeight(): Int {
    val rect = Rect()
    decorView.getWindowVisibleDisplayFrame(rect)
    return rect.top
}

fun View.goneView() {
    this.visibility = View.GONE
}

fun View.invisibleView() {
    this.visibility = View.INVISIBLE
}

fun View.showView() {
    this.visibility = View.VISIBLE
}

fun dimenToPixels(context: Context, @DimenRes dimenId: Int): Int =
    context.resources.getDimension(dimenId).toInt()

fun TextView.setHighlightedText(source: String, textToHighlight: String?) {
    text = if (!textToHighlight.isNullOrEmpty() && source.containsIgnoreCase(textToHighlight)) {
        val span = SpannableString(source)
        val indexStart = source.toLowerCaseLocalized().indexOf(textToHighlight.toLowerCaseLocalized())
        if (indexStart != -1) {
            span.setSpan(
                BackgroundColorSpan(ContextCompat.getColor(context, R.color.yellow)),
                indexStart,
                indexStart + textToHighlight.length,
                0
            )
            span
        } else {
            source
        }
    } else {
        source
    }
}
