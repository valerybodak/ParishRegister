package com.parish.register.ui.home

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.parish.register.databinding.ItemRegisterBinding
import com.parish.register.model.ListItem

class RegisterAdapter(
    private val listener: RegisterAdapterListener?
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val data: MutableList<ListItem> = mutableListOf()

    interface RegisterAdapterListener {
        fun onItemClick(item: ListItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            ItemRegisterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ItemHolder).bind(data[position])
    }

    fun update(newItems: List<ListItem>) {
        data.clear()
        data.addAll(newItems)
        notifyDataSetChanged()
    }

    inner class ItemHolder(private val binding: ItemRegisterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ListItem) {

            binding.tvName.text = "new item"

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
}