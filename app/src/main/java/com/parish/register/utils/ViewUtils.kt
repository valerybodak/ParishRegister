package com.parish.register.utils

import android.content.Context
import android.graphics.Rect
import android.view.View
import android.view.Window
import android.view.inputmethod.InputMethodManager
import androidx.annotation.DimenRes

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

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun dimenToPixels(context: Context, @DimenRes dimenId: Int): Int =
    context.resources.getDimension(dimenId).toInt()
