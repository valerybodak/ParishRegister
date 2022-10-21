package com.parish.register.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.parish.register.databinding.ItemRegisterBinding
import com.parish.register.model.Born
import com.parish.register.model.ListItem
import com.parish.register.model.Marriage

class RegisterAdapter(
    private val listener: RegisterAdapterListener?
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val data: MutableList<ListItem> = mutableListOf()

    interface RegisterAdapterListener {
        fun onItemClick(item: ListItem)
    }

    override fun getItemViewType(position: Int): Int {
        val item = data[position]
        if (item is Born) {
            return VIEW_TYPE_BORN
        } else {
            return VIEW_TYPE_MARRIAGE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_BORN -> {
                return BornItemHolder(
                    ItemRegisterBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> {
                return MarriageItemHolder(
                    ItemRegisterBinding.inflate(
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
        }
    }

    fun update(newItems: List<ListItem>) {
        data.clear()
        data.addAll(newItems)
        notifyDataSetChanged()
    }

    inner class BornItemHolder(private val binding: ItemRegisterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Born) {

            binding.tvName.text = item.fullName

            /*setBackgroundColor(item)

            binding.title.text = item.titleRu
            binding.author.text = item.authorRu

            var imageUrl = DEFAULT_BOOK_IMAGE_URL
            if (!item.imageUrl.isNullOrEmpty()) {
                imageUrl = item.imageUrl ?: ""
            }
            Glide.with(itemView.context)
                .load(imageUrl)
                .transform(CenterCrop())
                .into(binding.cover)*/

            itemView.setOnClickListener {
                listener?.onItemClick(item)
            }
        }

        /*private fun setBackgroundColor(item: Book) {
            val colorId = if(item.backgroundColorId == 0) R.color.book_bg_color_1 else item.backgroundColorId
            binding.coverBackground.backgroundTintList = ColorStateList.valueOf(
                ContextCompat.getColor(
                    itemView.context,
                    colorId
                )
            )
        }*/
    }

    inner class MarriageItemHolder(private val binding: ItemRegisterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Marriage) {

            binding.tvName.text = item.groom

            /*setBackgroundColor(item)

            binding.title.text = item.titleRu
            binding.author.text = item.authorRu

            var imageUrl = DEFAULT_BOOK_IMAGE_URL
            if (!item.imageUrl.isNullOrEmpty()) {
                imageUrl = item.imageUrl ?: ""
            }
            Glide.with(itemView.context)
                .load(imageUrl)
                .transform(CenterCrop())
                .into(binding.cover)*/

            itemView.setOnClickListener {
                listener?.onItemClick(item)
            }
        }

        /*private fun setBackgroundColor(item: Book) {
            val colorId = if(item.backgroundColorId == 0) R.color.book_bg_color_1 else item.backgroundColorId
            binding.coverBackground.backgroundTintList = ColorStateList.valueOf(
                ContextCompat.getColor(
                    itemView.context,
                    colorId
                )
            )
        }*/
    }

    companion object {
        private const val VIEW_TYPE_BORN = 0
        private const val VIEW_TYPE_MARRIAGE = 1
    }
}