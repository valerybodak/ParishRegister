package com.parish.register.ui.custom.dashboard

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat

class DashboardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var items: List<DashboardItem>? = null

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (canvas != null && items != null) {
            val center = Point(width / 2, height / 2)
            val outerRadius = height.toFloat() / 2
            var arcOffset = 0F
            var arcSweep = 0F
            for (index in items!!.indices) {
                val item = items!![index]
                arcOffset = arcOffset + arcSweep
                arcSweep = (COMPLETE_ANGLE * item.value) / 100
                /*if (items!!.lastIndex == index) {
                    arcSweep = COMPLETE_ANGLE -
                }*/
                val innerRadius = 130F

                val outer_rect = RectF(
                    center.x - outerRadius,
                    center.y - outerRadius,
                    center.x + outerRadius,
                    center.y + outerRadius
                )
                val inner_rect = RectF(
                    center.x - innerRadius,
                    center.y - innerRadius,
                    center.x + innerRadius,
                    center.y + innerRadius
                )

                val path = Path()
                path.arcTo(outer_rect, arcOffset, arcSweep)
                path.arcTo(inner_rect, arcOffset + arcSweep, -arcSweep)
                path.close()

                val fill = Paint()
                fill.setColor(ContextCompat.getColor(context, item.colorId))
                canvas?.drawPath(path, fill)

                /*val border = Paint()
            border.setStyle(Paint.Style.STROKE)
            border.setStrokeWidth(2F)
            canvas?.drawPath(path, border)*/
            }
        }
    }

    fun bind(items: List<DashboardItem>?) {
        this.items = items
        invalidate()
    }

    companion object {
        private const val COMPLETE_ANGLE = 360
    }
}