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
            items?.forEach { item ->
                val inner_radius = 130F
                val outer_radius = height.toFloat() / 2
                val arc_sweep = 90F
                val arc_ofset = 30F

                val outer_rect = RectF(
                    center.x - outer_radius,
                    center.y - outer_radius,
                    center.x + outer_radius,
                    center.y + outer_radius
                )
                val inner_rect = RectF(
                    center.x - inner_radius,
                    center.y - inner_radius,
                    center.x + inner_radius,
                    center.y + inner_radius
                )

                val path = Path()
                path.arcTo(outer_rect, arc_ofset, arc_sweep)
                path.arcTo(inner_rect, arc_ofset + arc_sweep, -arc_sweep)
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
}