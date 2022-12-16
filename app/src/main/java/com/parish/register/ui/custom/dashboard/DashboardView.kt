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
            val sumValues = items!!.sumOf { it.value.toDouble() }
            for (index in items!!.indices) {
                val item = items!![index]
                arcOffset += arcSweep
                arcSweep = (COMPLETE_ANGLE * item.value) / sumValues.toFloat()
                val innerRadius = 130F

                val outerRect = createOuterRect(center, outerRadius)
                val innerRect = createInnerRect(center, innerRadius)

                val path = Path()
                path.arcTo(outerRect, arcOffset, arcSweep)
                path.arcTo(innerRect, arcOffset + arcSweep, -arcSweep)
                path.close()

                val fill = Paint()
                fill.setColor(ContextCompat.getColor(context, item.colorId))
                canvas?.drawPath(path, fill)
            }
        }
    }

    private fun createInnerRect(center: Point, innerRadius: Float): RectF {
        return RectF(
            center.x - innerRadius,
            center.y - innerRadius,
            center.x + innerRadius,
            center.y + innerRadius
        )
    }

    private fun createOuterRect(center: Point, outerRadius: Float): RectF {
        return RectF(
            center.x - outerRadius,
            center.y - outerRadius,
            center.x + outerRadius,
            center.y + outerRadius
        )
    }

    fun bind(items: List<DashboardItem>?) {
        this.items = items
        invalidate()
    }

    companion object {
        private const val COMPLETE_ANGLE = 360F
    }
}