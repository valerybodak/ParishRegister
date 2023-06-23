package com.parish.register.ui.inspector

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.parish.register.common.CommonConsts.BACKEND_DATE_FORMAT
import com.parish.register.databinding.ItemDuplicateBinding
import java.text.SimpleDateFormat
import java.util.*

class DuplicatesAdapter(
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val dateFormat = SimpleDateFormat(BACKEND_DATE_FORMAT, Locale.ENGLISH)
    private val items: MutableList<DuplicateItem> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return DiedItemHolder(
            ItemDuplicateBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as DiedItemHolder
        viewHolder.bind(items[position])
    }

    fun update(newItems: List<DuplicateItem>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    inner class DiedItemHolder(private val binding: ItemDuplicateBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DuplicateItem) {
            binding.tvFund.text = item.item1.getSortName()
            /*binding.tvName.setHighlightedText(item.fullName, searchString)
            bindDate(binding.tvDate, R.string.died, item.deathDate)
            if (item.parents.isEmpty()) {
                binding.tvParents.goneView()
            } else {
                binding.tvParents.showView()
                binding.tvParents.setHighlightedText(
                    itemView.context.getString(
                        R.string.parents,
                        item.parents
                    ), searchString
                )
            }
            itemView.setOnClickListener {
                listener?.onItemClick(item)
            }*/
        }
    }

    companion object {
        private const val VIEW_TYPE_BORN = 0
        private const val VIEW_TYPE_MARRIAGE = 1
        private const val VIEW_TYPE_DIED = 2

        private const val UNKNOWN_VALUE = "???"
        private const val DATE_FORMAT_TO_DISPLAY = "dd.MM.yyyy"
    }
}