package com.parish.register.ui.custom

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

open class AspectRatioImageView : AppCompatImageView {

    constructor(context: Context) : super(context) {
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = MeasureSpec.getSize(widthMeasureSpec)
        var height = 0
        if (drawable != null) {
            height = width * drawable.intrinsicHeight / drawable.intrinsicWidth
        }
        setMeasuredDimension(width, height)
    }


}
