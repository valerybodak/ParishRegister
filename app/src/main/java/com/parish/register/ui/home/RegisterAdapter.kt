package com.parish.register.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
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
import com.parish.register.utils.hideView
import com.parish.register.utils.showView
import java.text.SimpleDateFormat
import java.util.*

class RegisterAdapter(
    private val listener: RegisterAdapterListener?
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
    private val data: MutableList<ListItem> = mutableListOf()

    interface RegisterAdapterListener {
        fun onItemClick(item: ListItem)
    }

    override fun getItemViewType(position: Int): Int {
        val item = data[position]
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
        return data.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = data[position]
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
        data.clear()
        data.addAll(newItems)
        notifyDataSetChanged()
    }

    private fun bindDate(textView: TextView, @StringRes label: Int, dateString: String) {
        if (dateString.isNotEmpty()) {
            textView.showView()
            try {
                dateFormat.parse(dateString)?.let {
                    textView.text = textView.context.getString(
                        label,
                        SimpleDateFormat("dd.MM.yyyy").format(it)
                    )
                }
            } catch (e: Exception) {
                textView.text = textView.context.getString(label, dateString)
            }
        } else {
            textView.hideView()
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

            binding.tvName.text = item.groom
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
    }
}