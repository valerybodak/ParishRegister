package com.parish.register.ui.inspector

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.parish.register.R
import com.parish.register.databinding.ItemDuplicateBinding
import com.parish.register.model.Born
import com.parish.register.model.Died
import com.parish.register.model.Marriage

class DuplicatesAdapter(
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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

        fun bind(duplicate: DuplicateItem) {
            if (duplicate.item1 is Born) {
                bindBornDuplicates(duplicate.item1, duplicate.item2 as Born, duplicate.similarity)
            } else if (duplicate.item1 is Marriage) {
                bindMarriageDuplicates(duplicate.item1, duplicate.item2 as Marriage, duplicate.similarity)
            } else if (duplicate.item1 is Died) {
                bindDiedDuplicates(duplicate.item1, duplicate.item2 as Died, duplicate.similarity)
            }
        }

        private fun bindBornDuplicates(item1: Born, item2: Born, similarity: Double) {
            binding.ivIcon.setImageResource(R.drawable.ic_born)
            binding.tvSimilarity.text = formatSimilarity(similarity)
            binding.tvFund.text = itemView.context.getString(
                R.string.fund_format,
                item1.fundNumber,
                item1.inventoryNumber,
                item1.caseNumber,
                item1.page
            )
            binding.tvDate.text = item1.birthDate
            binding.tvContent.text = item1.fullName + "\n\n" + item2.fullName
        }

        private fun bindMarriageDuplicates(item1: Marriage, item2: Marriage, similarity: Double) {
            binding.ivIcon.setImageResource(R.drawable.ic_marriage)
            binding.tvSimilarity.text = formatSimilarity(similarity)
            binding.tvFund.text = itemView.context.getString(
                R.string.fund_format,
                item1.fundNumber,
                item1.inventoryNumber,
                item1.caseNumber,
                item1.page
            )
            binding.tvDate.text = item1.date
            binding.tvContent.text = item1.groom + "\n\n" + item2.groom
        }

        private fun bindDiedDuplicates(item1: Died, item2: Died, similarity: Double) {
            binding.ivIcon.setImageResource(R.drawable.ic_died)
            binding.tvSimilarity.text = formatSimilarity(similarity)
            binding.tvFund.text = itemView.context.getString(
                R.string.fund_format,
                item1.fundNumber,
                item1.inventoryNumber,
                item1.caseNumber,
                item1.page
            )
            binding.tvDate.text = item1.deathDate
            binding.tvContent.text = item1.fullName + "\n\n" + item2.fullName
        }

        private fun formatSimilarity(similarity: Double): String {
            return String.format("%.2f", similarity)
        }
    }
}