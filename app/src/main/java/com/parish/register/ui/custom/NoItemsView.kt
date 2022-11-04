package com.parish.register.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.view.isVisible
import com.parish.register.R
import com.parish.register.databinding.LayoutNoItemsViewBinding

class NoItemsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var binding: LayoutNoItemsViewBinding? = null

    init {
        binding = LayoutNoItemsViewBinding.inflate(LayoutInflater.from(context), this, true)

        val attrInfo = context.obtainStyledAttributes(attrs, R.styleable.NoItemsView, defStyleAttr, 0)

        val title = attrInfo.getString(R.styleable.NoItemsView_title)
        val description = attrInfo.getString(R.styleable.NoItemsView_description)

        attrInfo.getDrawable(R.styleable.NoItemsView_icon)?.apply {
            binding?.ivEmptyIcon?.setImageDrawable(this)
        }

        binding?.txtTitle?.text = title
        binding?.txtDescription?.text = description

        binding?.txtDescription?.isVisible = description?.isNotEmpty() ?: false

        attrInfo.recycle()
    }
}