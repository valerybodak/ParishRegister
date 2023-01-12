package com.parish.register.ui.custom.dashboard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.parish.register.databinding.LayoutDashboardItemViewBinding

class DashboardItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var binding: LayoutDashboardItemViewBinding? = null

    init {
        binding = LayoutDashboardItemViewBinding.inflate(LayoutInflater.from(context), this, true)
    }

    fun bind(@ColorRes colorId: Int, title: String) {
        binding?.ivIcon?.backgroundTintList = ContextCompat.getColorStateList(context, colorId)
        binding?.tvTitle?.text = title
    }
}