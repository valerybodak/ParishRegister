package com.parish.register.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import com.parish.register.R
import com.parish.register.databinding.ItemBornBinding
import com.parish.register.databinding.ItemDiedBinding
import com.parish.register.databinding.ItemMarriageBinding
import com.parish.register.model.Born
import com.parish.register.model.Died
import com.parish.register.model.ListItem
import com.parish.register.model.Marriage
import com.parish.register.utils.showView
import java.text.SimpleDateFormat
import java.util.*

class RegisterAdapter(
    private val listener: RegisterAdapterListener?
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
    private val items: MutableList<ListItem> = mutableListOf()
    private var filteredItems: MutableList<ListItem> = mutableListOf()

    interface RegisterAdapterListener {
        fun onItemClick(item: ListItem)
    }

    override fun getItemViewType(position: Int): Int {
        val item = filteredItems[position]
        return if (item is Born) {
            VIEW_TYPE_BORN
        } else if (item is Marriage) {
            VIEW_TYPE_MARRIAGE
        } else {
            VIEW_TYPE_DIED
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_BORN -> {
                BornItemHolder(
                    ItemBornBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            VIEW_TYPE_MARRIAGE -> {
                MarriageItemHolder(
                    ItemMarriageBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> {
                DiedItemHolder(
                    ItemDiedBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return filteredItems.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = filteredItems[position]
        when (holder.itemViewType) {
            VIEW_TYPE_BORN -> {
                val viewHolder = holder as BornItemHolder
                viewHolder.bind(item as Born)
            }
            VIEW_TYPE_MARRIAGE -> {
                val viewHolder = holder as MarriageItemHolder
                viewHolder.bind(item as Marriage)
            }
            VIEW_TYPE_DIED -> {
                val viewHolder = holder as DiedItemHolder
                viewHolder.bind(item as Died)
            }
        }
    }

    fun update(newItems: List<ListItem>) {
        items.clear()
        items.addAll(newItems)
        filteredItems = items
        notifyDataSetChanged()
    }

    private fun bindDate(textView: TextView, @StringRes label: Int, dateString: String) {
        val context = textView.context
        if (dateString.isNotEmpty()) {
            textView.showView()
            try {
                dateFormat.parse(dateString)?.let {
                    textView.text = context.getString(
                        label,
                        SimpleDateFormat("dd.MM.yyyy").format(it)
                    )
                }
            } catch (e: Exception) {
                textView.text = context.getString(label, dateString)
            }
        } else {
            textView.text = context.getString(label, UNKNOWN_VALUE)
        }
    }

    override fun getFilter(): Filter {
        return exampleFilter
    }

    private val exampleFilter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults? {
            val filteredList: MutableList<ListItem> = ArrayList()
            if (constraint == null || constraint.length == 0) {
                filteredList.addAll(items)
            } else {
                val filterPattern =
                    constraint.toString().lowercase(Locale.getDefault()).trim { it <= ' ' }
                for (item in items) {
                    if (item.getSortName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults) {
            filteredItems.clear()
            filteredItems.addAll(results.values as List<ListItem>)
            notifyDataSetChanged()
        }
    }

    inner class BornItemHolder(private val binding: ItemBornBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Born) {

            binding.tvName.text = item.fullName
            bindDate(binding.tvDate, R.string.born, item.birthDate)
            itemView.setOnClickListener {
                listener?.onItemClick(item)
            }
        }
    }

    inner class MarriageItemHolder(private val binding: ItemMarriageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Marriage) {

            bindDate(binding.tvDate, R.string.marriage, item.date)
            binding.tvGroomName.text = if(item.groom.isEmpty()) UNKNOWN_VALUE else item.groom
            binding.tvBrideName.text = if(item.bride.isEmpty()) UNKNOWN_VALUE else item.bride
            itemView.setOnClickListener {
                listener?.onItemClick(item)
            }
        }
    }

    inner class DiedItemHolder(private val binding: ItemDiedBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Died) {

            binding.tvName.text = item.fullName
            bindDate(binding.tvDate, R.string.died, item.deathDate)
            itemView.setOnClickListener {
                listener?.onItemClick(item)
            }
        }
    }

    companion object {
        private const val VIEW_TYPE_BORN = 0
        private const val VIEW_TYPE_MARRIAGE = 1
        private const val VIEW_TYPE_DIED = 2

        private const val UNKNOWN_VALUE = "???"
    }
}