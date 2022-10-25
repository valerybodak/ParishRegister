package com.parish.register.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.parish.register.databinding.ItemBornBinding
import com.parish.register.databinding.ItemMarriageBinding
import com.parish.register.model.Born
import com.parish.register.model.Died
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
                    ItemBornBinding.inflate(
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

    inner class BornItemHolder(private val binding: ItemBornBinding) :
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

    inner class MarriageItemHolder(private val binding: ItemMarriageBinding) :
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

    inner class DiedItemHolder(private val binding: ItemBornBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Died) {

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

    companion object {
        private const val VIEW_TYPE_BORN = 0
        private const val VIEW_TYPE_MARRIAGE = 1
        private const val VIEW_TYPE_DIED = 2
    }
}